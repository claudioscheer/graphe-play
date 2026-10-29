package app.graphe.cartoes.domain

import kotlin.random.Random

data class QuizOption(
    val text: String,
    val correct: Boolean,
)

data class QuizQuestion(
    val cardId: String,
    val prompt: String,
    val reference: String,
    val options: List<QuizOption>,
)

/**
 * Builds a multiple-choice quiz from [prompts], which must already be limited
 * to the active scope. Wrong options are the three written on the card.
 * If a card has none yet, other answers of the same kind fill in.
 */
/**
 * Cards the quiz may ask. A book or chapter also includes that book's own
 * history notes (chapter 0). The whole Bible also includes church history.
 * The História context stays on church history alone.
 */
fun quizPrompts(all: List<Card>, scope: Scope): List<Card> {
    val matched = all.filter { it.matches(scope) }
    if (scope.history) {
        return matched
    }
    val bookNotes = scope.bookNumber?.let { book ->
        all.filter { it.bookNumber == book && it.chapter == 0 }
    }.orEmpty()
    val churchHistory = if (scope.isAll()) {
        all.filter { it.bookNumber == HISTORY_BOOK_NUMBER }
    } else {
        emptyList()
    }
    return (matched + bookNotes + churchHistory).distinctBy { it.id }
}

fun verseQuizQuestion(
    correct: MemoryVerse,
    distractors: List<MemoryVerse>,
    random: Random,
): QuizQuestion? {
    val wrong = distractors
        .filter { it.bookNumber == correct.bookNumber && it.text != correct.text }
        .distinctBy { it.text }
        .shuffled(random)
        .take(3)
    if (wrong.size < 3) {
        return null
    }
    val reference = passageLabel(correct.bookNumber, correct.chapter, correct.verse, correct.verse)
    val options = (wrong.map { QuizOption(it.text, false) } + QuizOption(correct.text, true))
        .shuffled(random)
    return QuizQuestion(
        cardId = "verse.${correct.bookNumber}.${correct.chapter}.${correct.verse}",
        prompt = "O que está escrito em $reference?",
        reference = reference,
        options = options,
    )
}

fun buildQuiz(
    prompts: List<Card>,
    distractorPool: List<Card>,
    count: Int,
    random: Random,
): List<QuizQuestion> {
    if (prompts.isEmpty() || count <= 0) {
        return emptyList()
    }
    return prompts.shuffled(random).take(count).map { card ->
        val wrongAnswers = wrongAnswers(card, distractorPool, random)
        val options = (wrongAnswers.map { QuizOption(it, false) } + QuizOption(card.answer, true))
            .shuffled(random)
        QuizQuestion(
            cardId = card.id,
            prompt = card.prompt,
            reference = card.reference,
            options = options,
        )
    }
}

private fun wrongAnswers(card: Card, pool: List<Card>, random: Random): List<String> {
    val written = card.wrongs.filter { it.isNotBlank() && it != card.answer }.distinct()
    if (written.size >= 3) {
        return written.shuffled(random).take(3)
    }
    val sameKind = pool
        .asSequence()
        .filter { it.id != card.id && it.kind == card.kind && it.answer != card.answer }
        .map { it.answer }
        .distinct()
        .shuffled(random)
        .take(3)
        .toList()
    if (sameKind.size == 3) {
        return sameKind
    }
    val filler = pool
        .asSequence()
        .filter { it.id != card.id && it.answer != card.answer && it.answer !in sameKind }
        .map { it.answer }
        .distinct()
        .shuffled(random)
        .take(3 - sameKind.size)
        .toList()
    return sameKind + filler
}
