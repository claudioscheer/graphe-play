package app.graphe.cartoes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.graphe.cartoes.data.PracticeRepository
import app.graphe.cartoes.data.StudyRepository
import app.graphe.cartoes.domain.Card
import app.graphe.cartoes.domain.Face
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.StudySession
import app.graphe.cartoes.domain.StreakState
import app.graphe.cartoes.domain.displayedStreak
import app.graphe.cartoes.domain.flip
import app.graphe.cartoes.domain.mark
import app.graphe.cartoes.domain.matches
import app.graphe.cartoes.domain.scopeLabel
import app.graphe.cartoes.domain.startSession
import app.graphe.cartoes.domain.studiedToday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import kotlin.random.Random

data class StudyUiState(
    val ready: Boolean = false,
    val scopeLabel: String = "Toda a Bíblia",
    val scope: Scope = Scope.all,
    val streak: Int = 0,
    val longestStreak: Int = 0,
    val studiedToday: Boolean = false,
    val showStreak: Boolean = false,
    val card: Card? = null,
    val face: Face = Face.FRONT,
    val empty: Boolean = false,
    val showScopeSheet: Boolean = false,
    val error: String? = null,
    val feedbackNonce: Int = 0,
    val feedback: Grade? = null,
    val verseReference: String? = null,
    val verseBody: String? = null,
)

class StudyViewModel(
    private val repository: StudyRepository,
    private val practice: PracticeRepository,
    private val random: Random = Random.Default,
    private val clock: Clock = Clock.systemDefaultZone(),
) : ViewModel() {
    private val _ui = MutableStateFlow(StudyUiState())
    val ui: StateFlow<StudyUiState> = _ui

    private var session = StudySession(
        face = Face.FRONT,
        currentId = null,
        progress = emptyMap(),
        streak = StreakState(0, 0, null),
    )
    private var deck: List<Card> = emptyList()
    private var scope: Scope = Scope.all
    private var busy = false
    private var feedbackNonce = 0
    private var feedback: Grade? = null
    private var showStreak = false
    private var verseReference: String? = null
    private var verseBody: String? = null

    init {
        reload(keepSheet = false)
    }

    fun onFlip() {
        if (_ui.value.card == null || busy) {
            return
        }
        session = flip(session)
        publish(showScopeSheet = _ui.value.showScopeSheet)
    }

    fun onGrade(grade: Grade) {
        val state = _ui.value
        if (state.card == null || busy) {
            return
        }
        busy = true
        val marked = mark(session, deck, grade, today(), Instant.now(clock).toEpochMilli(), random)
        viewModelScope.launch {
            try {
                repository.recordGrade(state.card.id, grade, Instant.now(clock), today())
                session = marked
                publish(showScopeSheet = false, graded = grade)
            } catch (error: Exception) {
                _ui.update { it.copy(error = "Não foi possível guardar a resposta.") }
            } finally {
                busy = false
            }
        }
    }

    fun openVerse() {
        val card = _ui.value.card ?: return
        viewModelScope.launch {
            val end = card.verseEnd ?: card.verseStart
            val lines = practice.verses(card.bookNumber, card.chapter, card.verseStart, end)
            verseReference = card.reference
            verseBody = if (lines.isEmpty()) {
                "Este versículo não está no texto."
            } else if (lines.size == 1) {
                lines.first().text
            } else {
                lines.joinToString("\n") { "${it.verse}  ${it.text}" }
            }
            publish(showScopeSheet = _ui.value.showScopeSheet)
        }
    }

    fun dismissVerse() {
        verseReference = null
        verseBody = null
        publish(showScopeSheet = _ui.value.showScopeSheet)
    }

    fun openScope() {
        _ui.update { it.copy(showScopeSheet = true) }
    }

    fun openStreak() {
        showStreak = true
        _ui.update { it.copy(showStreak = true, showScopeSheet = false) }
    }

    fun dismissStreak() {
        showStreak = false
        _ui.update { it.copy(showStreak = false) }
    }

    fun dismissScope() {
        _ui.update { it.copy(showScopeSheet = false) }
    }

    fun chooseScope(scope: Scope) {
        if (busy) {
            return
        }
        busy = true
        viewModelScope.launch {
            try {
                repository.setScope(scope)
                reload(keepSheet = false)
            } catch (error: Exception) {
                _ui.update { it.copy(error = "Não foi possível mudar o assunto.") }
                busy = false
            }
        }
    }

    fun clearScope() {
        chooseScope(Scope.all)
    }

    private fun reload(keepSheet: Boolean) {
        viewModelScope.launch {
            try {
                val loaded = repository.load()
                scope = loaded.scope
                deck = loaded.cards.filter { it.matches(scope) }
                session = startSession(deck, loaded.progress, loaded.streak, random)
                busy = false
                publish(showScopeSheet = keepSheet)
            } catch (error: Exception) {
                busy = false
                _ui.value = StudyUiState(
                    ready = true,
                    error = "Não foi possível abrir as cartas.",
                    showScopeSheet = keepSheet,
                )
            }
        }
    }

    private fun publish(showScopeSheet: Boolean, graded: Grade? = null) {
        if (graded != null) {
            feedbackNonce += 1
            feedback = graded
        }
        val card = session.currentId?.let { id -> deck.find { it.id == id } }
        _ui.value = StudyUiState(
            ready = true,
            scopeLabel = scopeLabel(scope),
            scope = scope,
            streak = displayedStreak(session.streak, today()),
            longestStreak = session.streak.longest,
            studiedToday = studiedToday(session.streak.lastStudyDate, today()),
            showStreak = showStreak,
            card = card,
            face = session.face,
            empty = card == null,
            showScopeSheet = showScopeSheet,
            feedbackNonce = feedbackNonce,
            feedback = feedback,
            verseReference = verseReference,
            verseBody = verseBody,
        )
    }

    private fun today(): LocalDate = LocalDate.now(clock)
}

class StudyViewModelFactory(
    private val repository: StudyRepository,
    private val practice: PracticeRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyViewModel(repository, practice) as T
        }
        error("Modelo desconhecido: ${modelClass.name}")
    }
}
