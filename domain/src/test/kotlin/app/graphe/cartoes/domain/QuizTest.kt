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
    }

    @Test
    fun genesisMapsToTheFirstAcfBook() {
        assertEquals(1, acfBookId(10))
        assertEquals(43, acfBookId(500))
        assertEquals(66, acfBookId(730))
        assertEquals("João 3:16", passageLabel(500, 3, 16, 16))
        assertEquals("João 3:16–18", passageLabel(500, 3, 16, 18))
    }
}
