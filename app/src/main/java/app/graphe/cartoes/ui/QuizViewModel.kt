package app.graphe.cartoes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.graphe.cartoes.data.PracticeRepository
import app.graphe.cartoes.data.QuizAnswerEntity
import app.graphe.cartoes.data.StudyRepository
import app.graphe.cartoes.domain.QuizOption
import app.graphe.cartoes.domain.QuizQuestion
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.buildQuiz
import app.graphe.cartoes.domain.quizPrompts
import app.graphe.cartoes.domain.scopeLabel
import app.graphe.cartoes.domain.verseQuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import kotlin.random.Random

data class QuizHistoryRow(
    val id: Long,
    val label: String,
    val score: String,
)

data class QuizMark(
    val chosenText: String,
    val correct: Boolean,
)

data class QuizUiState(
    val ready: Boolean = false,
    val scope: Scope = Scope.all,
    val scopeLabel: String = "Toda a Bíblia",
    val count: Int = 10,
    val history: List<QuizHistoryRow> = emptyList(),
    val showScope: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val index: Int = 0,
    val chosen: Int? = null,
    val marks: List<QuizMark> = emptyList(),
    val finished: Boolean = false,
    val message: String? = null,
)

class QuizViewModel(
    private val study: StudyRepository,
    private val practice: PracticeRepository,
    private val random: Random = Random.Default,
) : ViewModel() {
    private val _ui = MutableStateFlow(QuizUiState())
    val ui: StateFlow<QuizUiState> = _ui

    init {
        viewModelScope.launch { refreshSetup() }
    }

    fun setCount(count: Int) {
        _ui.update { it.copy(count = count) }
    }

    fun openScope() {
        _ui.update { it.copy(showScope = true) }
    }

    fun dismissScope() {
        _ui.update { it.copy(showScope = false) }
    }

    fun chooseScope(scope: Scope) {
        viewModelScope.launch {
            study.setScope(scope)
            _ui.update {
                it.copy(scope = scope, scopeLabel = scopeLabel(scope), showScope = false)
            }
        }
    }

    fun start() {
        val state = _ui.value
        viewModelScope.launch {
            val loaded = study.load()
            val prompts = quizPrompts(loaded.cards, state.scope)
            val verseSlots = if (state.scope.history) 0 else (state.count / 2).coerceAtMost(state.count)
            val cardQuestions = buildQuiz(prompts, loaded.cards, (state.count - verseSlots).coerceAtLeast(0), random)
            val verseQuestions = verseQuestions(state.scope, state.count - cardQuestions.size)
            val questions = (cardQuestions + verseQuestions).shuffled(random).take(state.count)
            if (questions.isEmpty()) {
                _ui.update { it.copy(message = "Não há perguntas neste contexto.") }
                return@launch
            }
            _ui.update {
                it.copy(
                    questions = questions,
                    index = 0,
                    chosen = null,
                    marks = emptyList(),
                    finished = false,
                    message = null,
                )
            }
        }
    }

    fun choose(option: Int) {
        val state = _ui.value
        if (state.questions.isEmpty() || state.chosen != null || state.finished) {
            return
        }
        _ui.update { it.copy(chosen = option) }
    }

    fun continueQuiz() {
        val state = _ui.value
        val chosen = state.chosen ?: return
        val question = state.questions[state.index]
        val option = question.options.getOrNull(chosen) ?: return
        val marks = state.marks + QuizMark(option.text, option.correct)
        if (state.index + 1 >= state.questions.size) {
            _ui.update { it.copy(marks = marks, chosen = null, finished = true) }
            save(state.questions, marks, state.scopeLabel)
        } else {
            _ui.update { it.copy(marks = marks, chosen = null, index = state.index + 1) }
        }
    }

    fun openHistory(id: Long) {
        viewModelScope.launch {
            val answers = practice.quizAnswers(id)
            if (answers.isEmpty()) {
                return@launch
            }
            _ui.update {
                it.copy(
                    questions = answers.map { answer ->
                        QuizQuestion(
                            cardId = answer.cardId,
                            prompt = answer.prompt,
                            reference = "",
                            options = listOf(QuizOption(answer.correctAnswer, true)),
                        )
                    },
                    marks = answers.map { answer -> QuizMark(answer.chosenAnswer, answer.wasCorrect) },
                    index = 0,
                    chosen = null,
                    finished = true,
                    message = null,
                )
            }
        }
    }

    fun deleteHistory(id: Long) {
        viewModelScope.launch {
            practice.deleteQuiz(id)
            _ui.update { state -> state.copy(history = state.history.filter { it.id != id }) }
        }
    }

    fun backToSetup() {
        viewModelScope.launch {
            _ui.update {
                it.copy(questions = emptyList(), finished = false, marks = emptyList(), chosen = null, index = 0)
            }
            refreshSetup()
        }
    }

    private suspend fun refreshSetup() {
        val loaded = study.load()
        val history = practice.recentQuizzes().map { row ->
            val date = Instant.ofEpochMilli(row.playedAtEpoch).atZone(ZoneId.systemDefault()).toLocalDate()
            QuizHistoryRow(
                id = row.id,
                label = "%02d/%02d · %s".format(date.dayOfMonth, date.monthValue, row.scopeLabel),
                score = "${row.correctCount}/${row.asked}",
            )
        }
        _ui.update {
            it.copy(
                ready = true,
                scope = loaded.scope,
                scopeLabel = scopeLabel(loaded.scope),
                history = history,
            )
        }
    }

    private suspend fun verseQuestions(scope: Scope, count: Int): List<QuizQuestion> {
        if (count <= 0 || scope.history) {
            return emptyList()
        }
        val inScope = practice.sampleVerses(scope, count + 2)
        return inScope.mapNotNull { correct ->
            val sameBook = practice.sampleVerses(Scope(bookNumber = correct.bookNumber), 8)
            verseQuizQuestion(correct, sameBook, random)
        }.take(count)
    }

    private fun save(questions: List<QuizQuestion>, marks: List<QuizMark>, scopeLabel: String) {
        viewModelScope.launch {
            val answers = questions.zip(marks).map { (question, mark) ->
                QuizAnswerEntity(
                    quizId = 0,
                    cardId = question.cardId,
                    prompt = question.prompt,
                    correctAnswer = question.options.first { it.correct }.text,
                    chosenAnswer = mark.chosenText,
                    wasCorrect = mark.correct,
                )
            }
            practice.saveQuiz(
                playedAtEpoch = System.currentTimeMillis(),
                scopeLabel = scopeLabel,
                answers = answers,
            )
        }
    }
}

class QuizViewModelFactory(
    private val study: StudyRepository,
    private val practice: PracticeRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(study, practice) as T
        }
        error("Modelo desconhecido: ${modelClass.name}")
    }
}
