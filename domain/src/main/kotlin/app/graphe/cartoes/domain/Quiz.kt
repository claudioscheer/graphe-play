package app.graphe.cartoes.domain

import kotlin.random.Random

data class QuizOption(
    val text: String,
    val correct: Boolean,
)

/** The kind of question a quiz slice asks. */
enum class QuizTopic {
    VERSE,
    HISTORY,
    CURIOSITY,
    SUBJECT,
    NAME_PLACE,
}

data class QuizQuestion(
    val cardId: String,
    val prompt: String,
    val reference: String,
    val options: List<QuizOption>,
    /** Null for questions rebuilt from a saved quiz, which does not store the type. */
    val topic: QuizTopic? = null,
)

/**
 * How many questions each slice gets. [fixed] covers the five content types;
 * [random] is the joker that draws any card from the scope.
 */
data class QuizPlan(
    val fixed: Map<QuizTopic, Int>,
    val random: Int,
) {
    val total: Int get() = fixed.values.sum() + random
}

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

/** Which fixed slice a card belongs to. History is its own deck. */
fun Card.quizTopic(): QuizTopic = when {
    bookNumber == HISTORY_BOOK_NUMBER -> QuizTopic.HISTORY
    kind == CardKind.CURIOSITY -> QuizTopic.CURIOSITY
    kind == CardKind.SUBJECT -> QuizTopic.SUBJECT
    else -> QuizTopic.NAME_PLACE
}

/**
 * Splits [count] questions across the six slices. The random slice takes one
 * in five; the five fixed topics share the rest. Leftover fixed questions
 * rotate, so no topic is always short. A quiz of five or fewer uses only the
 * fixed topics, one for each topic that fits, because there is no room for the
 * joker.
 */
fun quizPlan(count: Int, random: Random): QuizPlan {
    if (count <= 0) {
        return QuizPlan(emptyMap(), 0)
    }
    if (count <= QuizTopic.entries.size) {
        return QuizPlan(QuizTopic.entries.take(count).associateWith { 1 }, 0)
    }
    val randomSlots = maxOf(1, count / 5)
    val fixedCount = count - randomSlots
    val base = fixedCount / QuizTopic.entries.size
    val extra = fixedCount % QuizTopic.entries.size
    val fixed = QuizTopic.entries.associateWith { base }.toMutableMap()
    QuizTopic.entries.shuffled(random).take(extra).forEach { topic ->
        fixed[topic] = (fixed[topic] ?: 0) + 1
    }
    return QuizPlan(fixed, randomSlots)
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
        topic = QuizTopic.VERSE,
    )
}

/**
 * Builds a quiz with the mix from [quizPlan]. [prompts] must already be limited
 * to the active scope. [verseQuestions] are the pre-built "O que está escrito"
 * questions; the builder takes only the verse slice from them. If a slice runs
 * out, the remaining cards fill in, then verse questions.
 */
fun buildMixedQuiz(
    prompts: List<Card>,
    distractorPool: List<Card>,
    verseQuestions: List<QuizQuestion>,
    count: Int,
    random: Random,
): List<QuizQuestion> {
    if (count <= 0) {
        return emptyList()
    }
    return buildMixedQuiz(prompts, distractorPool, verseQuestions, quizPlan(count, random), random)
}

/** Same as [buildMixedQuiz], with a plan already drawn so the caller can size the verse pool. */
fun buildMixedQuiz(
    prompts: List<Card>,
    distractorPool: List<Card>,
    verseQuestions: List<QuizQuestion>,
    plan: QuizPlan,
    random: Random,
): List<QuizQuestion> {
    if (plan.total <= 0) {
        return emptyList()
    }
    val count = plan.total
    val pool = prompts.distinctBy { it.id }
    val byTopic = pool.groupBy { it.quizTopic() }
    val used = mutableSetOf<String>()
    val cards = mutableListOf<Card>()
    val verses = verseQuestions.distinctBy { it.cardId }.shuffled(random)
    var verseCount = 0

    plan.fixed.forEach { (topic, target) ->
        if (topic == QuizTopic.VERSE) {
            verseCount = minOf(target, verses.size)
        } else {
            val picks = byTopic[topic].orEmpty()
                .filter { it.id !in used }
                .shuffled(random)
                .take(target)
            cards += picks
            used += picks.map { it.id }
        }
    }

    if (plan.random > 0) {
        val picks = pool.filter { it.id !in used }
            .shuffled(random)
            .take(plan.random)
        cards += picks
        used += picks.map { it.id }
    }

    val missing = count - verseCount - cards.size
    if (missing > 0) {
        val fill = pool.filter { it.id !in used }
            .shuffled(random)
            .take(missing)
        cards += fill
        used += fill.map { it.id }
    }

    val verseMissing = count - verseCount - cards.size
    if (verseMissing > 0) {
        verseCount = minOf(verses.size, verseCount + verseMissing)
    }

    val cardQuestions = cards.map { card -> cardQuizQuestion(card, distractorPool, random) }
    return (cardQuestions + verses.take(verseCount)).shuffled(random)
}

/**
 * Builds a multiple-choice quiz from [prompts], which must already be limited
 * to the active scope. Wrong options are the three written on the card.
 * If a card has none yet, other answers of the same kind fill in.
 */
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
        cardQuizQuestion(card, distractorPool, random)
    }
}

private fun cardQuizQuestion(
    card: Card,
    distractorPool: List<Card>,
    random: Random,
): QuizQuestion {
    val wrongAnswers = wrongAnswers(card, distractorPool, random)
    val options = (wrongAnswers.map { QuizOption(it, false) } + QuizOption(card.answer, true))
        .shuffled(random)
    return QuizQuestion(
        cardId = card.id,
        prompt = card.prompt,
        reference = card.reference,
        options = options,
        topic = card.quizTopic(),
    )
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
