package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class SessionTest {
    private val today = LocalDate.of(2026, 10, 29)
    private val cards = listOf(card("a"), card("b"))

    @Test
    fun aMarkOnTheFrontRecordsWithoutRevealing() {
        val session = StudySession(
            face = Face.FRONT,
            currentId = "a",
            progress = emptyMap(),
            streak = StreakState(0, 0, null),
        )
        val next = mark(session, cards, Grade.CORRECT, today, 5L, ScriptedRandom(listOf(0)))
        assertEquals(Face.FRONT, next.face)
        assertEquals("b", next.currentId)
        assertEquals(1, next.progress.getValue("a").correctCount)
        assertEquals(today, next.streak.lastStudyDate)
    }

    @Test
    fun aMarkOnTheBackRecordsAndAdvancesFaceUp() {
        val session = StudySession(
            face = Face.BACK,
            currentId = "a",
            progress = emptyMap(),
            streak = StreakState(0, 0, null),
        )
        val next = mark(session, cards, Grade.CORRECT, today, 5L, ScriptedRandom(listOf(0)))
        assertEquals(Face.FRONT, next.face)
        assertEquals("b", next.currentId)
        val graded = next.progress.getValue("a")
        assertEquals(1, graded.correctCount)
        assertEquals(Grade.CORRECT, graded.lastResult)
        assertEquals(1, next.streak.current)
        assertEquals(today, next.streak.lastStudyDate)
    }

    @Test
    fun flippingBackAndForthDoesNotGrade() {
        val start = startSession(cards, emptyMap(), StreakState(0, 0, null), ScriptedRandom(listOf(0, 0)))
        val back = flip(start)
        val front = flip(back)
        assertEquals(Face.FRONT, front.face)
        assertTrue(front.progress.isEmpty())
    }
}
