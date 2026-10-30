package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuizTest {
    @Test
    fun eachQuestionHasOneCorrectOptionFromTheScope() {
        val names = (1..8).map { index ->
            card("n$index", kind = CardKind.NAME).copy(answer = "Nome $index", prompt = "Quem é $index?")
        }
        val places = (1..4).map { index ->
            card("p$index", kind = CardKind.PLACE).copy(answer = "Lugar $index", prompt = "Onde $index?")
        }
        val quiz = buildQuiz(names.take(5), names + places, count = 5, random = Random(7))
        assertEquals(5, quiz.size)
        assertTrue(quiz.all { it.cardId in names.take(5).map { card -> card.id } })
        quiz.forEach { question ->
            assertEquals(4, question.options.size)
            assertEquals(1, question.options.count { it.correct })
            val correct = question.options.first { it.correct }.text
            assertTrue(question.options.none { !it.correct && it.text == correct })
            val wrong = question.options.filter { !it.correct }.map { it.text }
            assertTrue(wrong.all { text -> names.any { it.answer == text } })
        }
    }

    @Test
    fun writtenWrongsAreTheQuizOptions() {
        val card = card("EXO.1.curiosity.1", bookNumber = 20).copy(
            prompt = "O que Faraó mandou fazer com todo menino recém-nascido?",
            answer = "Lançá-lo no rio.",
            wrongs = listOf("Escondê-lo em casa.", "Deixá-lo no deserto.", "Entregá-lo aos soldados."),
        )
        val other = card("HIS.0.curiosity.41", bookNumber = 0).copy(answer = "Pregador da Boêmia.")
        val question = buildQuiz(listOf(card), listOf(card, other), count = 1, random = Random(1)).single()
        val wrong = question.options.filter { !it.correct }.map { it.text }.toSet()
        assertEquals(card.wrongs.toSet(), wrong)
    }

    @Test
    fun aShortScopeStillMakesAQuiz() {
        val cards = listOf(
            card("a").copy(answer = "Um"),
            card("b").copy(answer = "Dois"),
        )
        val quiz = buildQuiz(cards, cards, count = 10, random = Random(1))
        assertEquals(2, quiz.size)
        assertTrue(quiz.all { it.options.size == 2 })
    }

    @Test
    fun aBookQuizIncludesThatBooksNotesAndTheWholeBibleIncludesHistory() {
        val chapter = card("GEN.1.subject.1", bookNumber = 10, chapter = 1)
        val note = card("GEN.0.curiosity.1", bookNumber = 10, chapter = 0)
        val history = card("HIS.0.curiosity.1", bookNumber = HISTORY_BOOK_NUMBER, chapter = 0)
        val john = card("JHN.3.subject.1", bookNumber = 500, chapter = 3)
        val all = listOf(chapter, note, history, john)
        assertEquals(setOf("GEN.1.subject.1", "GEN.0.curiosity.1"), quizPrompts(all, Scope(bookNumber = 10, chapter = 1)).map { it.id }.toSet())
        assertEquals(setOf("HIS.0.curiosity.1"), quizPrompts(all, Scope.history).map { it.id }.toSet())
        assertTrue(quizPrompts(all, Scope.all).any { it.bookNumber == HISTORY_BOOK_NUMBER })
    }

    @Test
    fun aVerseQuestionOffersTheWrittenTextAndThreeOthers() {
        val correct = MemoryVerse(10, 1, 1, "No princípio criou Deus os céus e a terra.")
        val others = listOf(
            MemoryVerse(10, 1, 2, "E a terra era sem forma e vazia."),
            MemoryVerse(10, 1, 3, "E disse Deus: Haja luz."),
            MemoryVerse(10, 2, 7, "Formou o Senhor Deus o homem do pó."),
            MemoryVerse(10, 1, 1, "No princípio criou Deus os céus e a terra."),
        )
        val question = verseQuizQuestion(correct, others, Random(3))
        assertEquals("O que está escrito em Gênesis 1:1?", question!!.prompt)
        assertEquals(4, question.options.size)
        assertEquals(1, question.options.count { it.correct })
        assertEquals(correct.text, question.options.first { it.correct }.text)
        assertEquals(QuizTopic.VERSE, question.topic)
    }

    @Test
    fun verseDistractorsComeFromTheSameBook() {
        val correct = MemoryVerse(20, 1, 2, "Texto de Êxodo 1:2.")
        val exodus = listOf(
            MemoryVerse(20, 1, 3, "Outro texto de Êxodo."),
            MemoryVerse(20, 2, 1, "Mais um texto de Êxodo."),
            MemoryVerse(20, 12, 4, "Um texto de outro capítulo de Êxodo."),
        )
        val genesis = MemoryVerse(10, 1, 1, "Texto de Gênesis.")
        val question = verseQuizQuestion(correct, exodus + genesis, Random(1))
        val wrong = question!!.options.filter { !it.correct }.map { it.text }
        assertEquals(exodus.map { it.text }.toSet(), wrong.toSet())
    }

    @Test
    fun genesisMapsToTheFirstAcfBook() {
        assertEquals(1, acfBookId(10))
        assertEquals(43, acfBookId(500))
        assertEquals(66, acfBookId(730))
        assertEquals("João 3:16", passageLabel(500, 3, 16, 16))
        assertEquals("João 3:16–18", passageLabel(500, 3, 16, 18))
    }

    @Test
    fun quizPlanGivesFiveFixedTopicsForFiveQuestions() {
        val plan = quizPlan(5, Random(1))
        assertEquals(0, plan.random)
        assertEquals(5, plan.fixed.size)
        assertTrue(plan.fixed.values.all { it == 1 })
        assertEquals(5, plan.total)
    }

    @Test
    fun quizPlanGivesTheRandomSliceOneInFive() {
        val ten = quizPlan(10, Random(1))
        assertEquals(2, ten.random)
        assertEquals(8, ten.fixed.values.sum())
        assertTrue(ten.fixed.values.all { it in 1..2 })
        assertEquals(10, ten.total)

        val twenty = quizPlan(20, Random(1))
        assertEquals(4, twenty.random)
        assertEquals(16, twenty.fixed.values.sum())
        assertTrue(twenty.fixed.values.all { it in 3..4 })
        assertEquals(20, twenty.total)
    }

    @Test
    fun theLeftoverFixedQuestionsRotate() {
        val withTwo = (1..50).flatMap { seed ->
            quizPlan(10, Random(seed)).fixed.filterValues { it == 2 }.keys
        }.toSet()
        assertEquals(QuizTopic.entries.toSet(), withTwo)
    }

    @Test
    fun aFiveQuestionQuizUsesOneOfEachFixedTopic() {
        val cards = mixedCards()
        val verses = (1..8).map { verseQuestion(it) }
        val quiz = buildMixedQuiz(cards, cards, verses, count = 5, random = Random(7))
        assertEquals(5, quiz.size)
        QuizTopic.entries.forEach { topic ->
            assertEquals(1, quiz.count { it.topic == topic })
        }
    }

    @Test
    fun aMixedQuizUsesEveryTopicAndTheRandomSlice() {
        val cards = mixedCards()
        val verses = (1..8).map { verseQuestion(it) }
        val quiz = buildMixedQuiz(cards, cards, verses, count = 10, random = Random(3))
        assertEquals(10, quiz.size)
        assertEquals(10, quiz.map { it.cardId }.distinct().size)
        QuizTopic.entries.forEach { topic ->
            assertTrue("missing $topic", quiz.any { it.topic == topic })
        }
    }

    @Test
    fun aBookQuizDropsHistoryAndStillFillsTheCount() {
        val cards = mixedCards().filter { it.bookNumber != HISTORY_BOOK_NUMBER }
        val verses = (1..6).map { verseQuestion(it) }
        val quiz = buildMixedQuiz(cards, cards, verses, count = 10, random = Random(4))
        assertEquals(10, quiz.size)
        assertTrue(quiz.none { it.topic == QuizTopic.HISTORY })
        assertTrue(quiz.any { it.topic == QuizTopic.VERSE })
    }

    @Test
    fun aHistoryQuizIsAllHistoryAndNoVerse() {
        val cards = (1..20).map { index ->
            card("H$index", bookNumber = HISTORY_BOOK_NUMBER, kind = CardKind.CURIOSITY)
        }
        val quiz = buildMixedQuiz(cards, cards, emptyList(), count = 10, random = Random(5))
        assertEquals(10, quiz.size)
        assertTrue(quiz.all { it.topic == QuizTopic.HISTORY })
    }

    @Test
    fun aShortScopeFillsWithVerseQuestions() {
        val cards = listOf(
            card("C1", kind = CardKind.CURIOSITY),
            card("S1", kind = CardKind.SUBJECT),
            card("N1", kind = CardKind.NAME),
        )
        val verses = (1..10).map { verseQuestion(it) }
        val quiz = buildMixedQuiz(cards, cards, verses, count = 10, random = Random(6))
        assertEquals(10, quiz.size)
        assertEquals(3, quiz.count { it.topic != QuizTopic.VERSE })
        assertEquals(7, quiz.count { it.topic == QuizTopic.VERSE })
    }
}

private fun mixedCards(): List<Card> {
    val cards = mutableListOf<Card>()
    repeat(8) { index ->
        cards += card("H$index", bookNumber = HISTORY_BOOK_NUMBER, kind = CardKind.CURIOSITY)
        cards += card("C$index", kind = CardKind.CURIOSITY)
        cards += card("S$index", kind = CardKind.SUBJECT)
        cards += card("N$index", kind = CardKind.NAME)
        cards += card("P$index", kind = CardKind.PLACE)
    }
    return cards
}

private fun verseQuestion(index: Int): QuizQuestion = QuizQuestion(
    cardId = "verse.$index",
    prompt = "O que está escrito em Livro 1:$index?",
    reference = "Livro 1:$index",
    options = listOf(
        QuizOption("Certa $index", true),
        QuizOption("Errada $index", false),
    ),
    topic = QuizTopic.VERSE,
)
