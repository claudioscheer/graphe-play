package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class DeckTest {
    @Test
    fun classifiesPools() {
        assertEquals(Pool.UNSEEN, Deck.poolOf(null))
        assertEquals(Pool.UNSEEN, Deck.poolOf(progress("a", correct = 0, wrong = 0, streak = 0, last = null)))
        assertEquals(Pool.LEARNING, Deck.poolOf(progress("a", correct = 1, wrong = 0, streak = 1, last = Grade.CORRECT)))
        assertEquals(Pool.KNOWN, Deck.poolOf(progress("a", correct = 2, wrong = 0, streak = 2, last = Grade.CORRECT)))
        assertEquals(Pool.LEARNING, Deck.poolOf(progress("a", correct = 4, wrong = 1, streak = 0, last = Grade.WRONG)))
    }

    @Test
    fun picksTheRandomIndexInsideTheScope() {
        val cards = listOf(card("a"), card("b"), card("c"), card("d"))
        val drawn = Deck.next(cards, excludeId = null, random = ScriptedRandom(listOf(2)))
        assertEquals("c", drawn!!.id)
    }

    @Test
    fun aRunIsNotTheFileOrder() {
        val cards = (1..20).map { card("c$it") }
        val random = Random(42)
        val drawn = (1..12).map {
            Deck.next(cards, excludeId = null, random = random)!!.id
        }
        assertTrue(drawn != cards.take(12).map { it.id })
        assertTrue(drawn.all { it in cards.map { card -> card.id } })
    }

    @Test
    fun skipsTheCardOnScreenWhenThePoolHasAnother() {
        val cards = listOf(card("L1"), card("L2"))
        val progress = mapOf("L1" to knownWrong("L1"), "L2" to knownWrong("L2"))
        val drawn = Deck.next(cards, excludeId = "L1", random = ScriptedRandom(listOf(0)))
        assertEquals("L2", drawn!!.id)
    }

    @Test
    fun singleCardCanRepeat() {
        val cards = listOf(card("only"))
        val drawn = Deck.next(cards, excludeId = "only", random = ScriptedRandom(listOf(0)))
        assertEquals("only", drawn!!.id)
    }

    @Test
    fun emptyScopeDrawsNothing() {
        assertNull(Deck.next(emptyList(), null, ScriptedRandom(emptyList())))
    }

    @Test
    fun aWrongAnswerAfterMasteryReturnsToLearning() {
        val wasKnown = mastered("a")
        val after = applyGrade(wasKnown, "a", Grade.WRONG, 10L)
        assertEquals(Pool.LEARNING, Deck.poolOf(after))
        assertEquals(0, after.correctStreak)
        assertEquals(1, after.wrongCount)
        assertEquals(2, after.correctCount)
    }

    @Test
    fun twoCorrectAnswersInARowLeaveLearning() {
        val once = applyGrade(null, "a", Grade.CORRECT, 1L)
        assertEquals(Pool.LEARNING, Deck.poolOf(once))
        val twice = applyGrade(once, "a", Grade.CORRECT, 2L)
        assertEquals(Pool.KNOWN, Deck.poolOf(twice))
        assertTrue(twice.correctStreak >= 2)
    }

}

internal class ScriptedRandom(values: List<Int>) : Random() {
    private val queue = ArrayDeque(values)
    override fun nextBits(bitCount: Int): Int = 0
    override fun nextInt(until: Int): Int {
        val value = queue.removeFirst()
        check(value in 0 until until) { "$value not in 0..$until" }
        return value
    }
}

internal fun card(
    id: String,
    bookNumber: Int = 10,
    chapter: Int = 1,
    kind: CardKind = CardKind.SUBJECT,
): Card = Card(
    id = id,
    bookNumber = bookNumber,
    chapter = chapter,
    verseStart = 1,
    verseEnd = null,
    kind = kind,
    prompt = "Pergunta $id",
    answer = "Resposta $id",
    reference = "Gn 1:1",
)

internal fun progress(
    id: String,
    correct: Int,
    wrong: Int,
    streak: Int,
    last: Grade?,
): CardProgress = CardProgress(id, correct, wrong, streak, last, 1L)

internal fun knownWrong(id: String) = progress(id, correct = 0, wrong = 1, streak = 0, last = Grade.WRONG)

internal fun mastered(id: String) = progress(id, correct = 2, wrong = 0, streak = 2, last = Grade.CORRECT)
