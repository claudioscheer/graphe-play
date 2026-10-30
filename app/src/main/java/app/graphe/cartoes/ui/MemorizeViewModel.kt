package app.graphe.cartoes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.graphe.cartoes.data.PracticeRepository
import app.graphe.cartoes.domain.Card
import app.graphe.cartoes.domain.CardKind
import app.graphe.cartoes.domain.Face
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.passageLabel
import app.graphe.cartoes.domain.scopeLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MemorizeUiState(
    val ready: Boolean = false,
    val scope: Scope = Scope.all,
    val scopeLabel: String = "Toda a Bíblia",
    val verseStart: Int? = null,
    val verseEnd: Int? = null,
    val verseCount: Int? = null,
    val showScope: Boolean = false,
    val card: Card? = null,
    val face: Face = Face.FRONT,
    val passageId: Long = 0,
    val message: String? = null,
    val verseReference: String? = null,
    val verseBody: String? = null,
)

class MemorizeViewModel(
    private val practice: PracticeRepository,
) : ViewModel() {
    private val _ui = MutableStateFlow(MemorizeUiState())
    val ui: StateFlow<MemorizeUiState> = _ui

    init {
        viewModelScope.launch {
            val saved = practice.memorySelection()
            val scope = if (saved.scope.history) Scope.all else saved.scope
            val verseStart = if (saved.scope.history) null else saved.verseStart
            val verseEnd = if (saved.scope.history) null else saved.verseEnd
            if (saved.scope.history) {
                practice.setMemorySelection(Scope.all, null, null)
            }
            val count = verseCount(scope)
            _ui.value = MemorizeUiState(
                ready = true,
                scope = scope,
                scopeLabel = label(scope, verseStart, verseEnd, count),
                verseStart = verseStart,
                verseEnd = verseEnd,
                verseCount = count,
            )
            draw(avoid = false)
        }
    }

    fun openScope() {
        _ui.update { it.copy(showScope = true) }
    }

    fun dismissScope() {
        _ui.update { it.copy(showScope = false) }
    }

    fun chooseScope(scope: Scope) {
        viewModelScope.launch {
            val bookNumber = scope.bookNumber
            val chapter = scope.chapter
            if (bookNumber != null && chapter != null) {
                val count = practice.verseCount(bookNumber, chapter)
                _ui.update {
                    it.copy(
                        scope = scope,
                        verseCount = count,
                        verseStart = 1,
                        verseEnd = count.coerceAtLeast(1),
                        scopeLabel = label(scope, 1, count, count),
                        showScope = true,
                        message = if (count == 0) "Este capítulo não está no texto." else null,
                    )
                }
                return@launch
            }
            practice.setMemorySelection(scope, null, null)
            _ui.update {
                it.copy(
                    scope = scope,
                    verseStart = null,
                    verseEnd = null,
                    verseCount = null,
                    scopeLabel = scopeLabel(scope),
                    showScope = false,
                    face = Face.FRONT,
                    message = null,
                )
            }
            draw(avoid = false)
        }
    }

    fun moveStart(delta: Int) {
        val state = _ui.value
        val count = state.verseCount ?: return
        val start = ((state.verseStart ?: 1) + delta).coerceIn(1, count)
        val end = (state.verseEnd ?: count).coerceAtLeast(start)
        _ui.update {
            it.copy(
                verseStart = start,
                verseEnd = end,
                scopeLabel = label(state.scope, start, end, count),
            )
        }
    }

    fun moveEnd(delta: Int) {
        val state = _ui.value
        val count = state.verseCount ?: return
        val start = state.verseStart ?: 1
        val end = ((state.verseEnd ?: count) + delta).coerceIn(start, count)
        _ui.update {
            it.copy(
                verseEnd = end,
                scopeLabel = label(state.scope, start, end, count),
            )
        }
    }

    fun dismissVerseSelection() {
        val scope = _ui.value.scope
        if (scope.chapter == null) {
            return
        }
        val withoutChapter = scope.copy(chapter = null)
        _ui.update {
            it.copy(
                scope = withoutChapter,
                verseCount = null,
                verseStart = null,
                verseEnd = null,
                scopeLabel = scopeLabel(withoutChapter),
            )
        }
    }

    fun useVerseSet() {
        val state = _ui.value
        viewModelScope.launch {
            practice.setMemorySelection(state.scope, state.verseStart, state.verseEnd)
            _ui.update { it.copy(showScope = false, face = Face.FRONT) }
            draw(avoid = false)
        }
    }

    fun openVerse() {
        val card = _ui.value.card ?: return
        viewModelScope.launch {
            val end = card.verseEnd ?: card.verseStart
            val lines = practice.verses(card.bookNumber, card.chapter, card.verseStart, end)
            val body = if (lines.isEmpty()) {
                "Este versículo não está no texto."
            } else if (lines.size == 1) {
                lines.first().text
            } else {
                lines.joinToString("\n") { "${it.verse}  ${it.text}" }
            }
            _ui.update { it.copy(verseReference = card.reference, verseBody = body) }
        }
    }

    fun dismissVerse() {
        _ui.update { it.copy(verseReference = null, verseBody = null) }
    }

    fun onFlip() {
        val state = _ui.value
        if (state.card == null) {
            return
        }
        _ui.update {
            it.copy(face = if (state.face == Face.FRONT) Face.BACK else Face.FRONT)
        }
    }

    fun onGrade(grade: Grade) {
        val state = _ui.value
        val card = state.card ?: return
        viewModelScope.launch {
            practice.saveReview(
                passageId = state.passageId,
                verse = app.graphe.cartoes.domain.MemoryVerse(
                    bookNumber = card.bookNumber,
                    chapter = card.chapter,
                    verse = card.verseStart,
                    text = card.answer,
                ),
                grade = grade,
                now = System.currentTimeMillis(),
            )
            _ui.update { it.copy(face = Face.FRONT, verseReference = null, verseBody = null) }
            draw(avoid = true)
        }
    }

    private suspend fun draw(avoid: Boolean) {
        val state = _ui.value
        val verse = practice.randomVerse(
            scope = state.scope,
            verseStart = state.verseStart,
            verseEnd = state.verseEnd,
            avoidChapter = if (avoid) state.card?.chapter else null,
            avoidVerse = if (avoid) state.card?.verseStart else null,
        )
        if (verse == null) {
            _ui.update { it.copy(card = null, message = "Não há versículos neste contexto.") }
            return
        }
        val reference = passageLabel(verse.bookNumber, verse.chapter, verse.verse, verse.verse)
        _ui.update {
            it.copy(
                card = Card(
                    id = "mem.${verse.bookNumber}.${verse.chapter}.${verse.verse}",
                    bookNumber = verse.bookNumber,
                    chapter = verse.chapter,
                    verseStart = verse.verse,
                    kind = CardKind.SUBJECT,
                    prompt = reference,
                    answer = verse.text,
                    reference = reference,
                ),
                face = Face.FRONT,
                message = null,
            )
        }
    }

    private suspend fun verseCount(scope: Scope): Int? {
        val book = scope.bookNumber ?: return null
        val chapter = scope.chapter ?: return null
        return practice.verseCount(book, chapter)
    }

    private fun label(scope: Scope, start: Int?, end: Int?, count: Int?): String {
        val book = scope.bookNumber
        val chapter = scope.chapter
        if (book != null && chapter != null && start != null && end != null && count != null) {
            if (start != 1 || end != count) {
                return passageLabel(book, chapter, start, end)
            }
        }
        return scopeLabel(scope)
    }
}

class MemorizeViewModelFactory(
    private val practice: PracticeRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemorizeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemorizeViewModel(practice) as T
        }
        error("Modelo desconhecido: ${modelClass.name}")
    }
}
