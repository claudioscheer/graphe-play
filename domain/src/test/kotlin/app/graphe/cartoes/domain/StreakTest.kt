package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class StreakTest {
    private val zone = ZoneId.of("America/Sao_Paulo")
    private val today = LocalDate.of(2026, 10, 29)

    @Test
    fun sameDayDoesNotGrow() {
        val started = recordStudyDay(StreakState(0, 0, null), today)
        assertEquals(1, started.current)
        val again = recordStudyDay(started, today)
        assertEquals(1, again.current)
        assertEquals(1, again.longest)
        assertEquals(1, displayedStreak(again, today))
        assertTrue(studiedToday(again.lastStudyDate, today))
    }

    @Test
    fun nextDayGrows() {
        val started = recordStudyDay(StreakState(0, 0, null), today)
        val tomorrow = recordStudyDay(started, today.plusDays(1))
        assertEquals(2, tomorrow.current)
        assertEquals(2, tomorrow.longest)
        assertEquals(2, displayedStreak(tomorrow, today.plusDays(1)))
        assertEquals(1, displayedStreak(started, today.plusDays(1)))
    }

    @Test
    fun aSkippedDayShowsZeroUntilTheNextStudyStartsAtOne() {
        val started = recordStudyDay(StreakState(0, 4, null), today)
        val later = today.plusDays(2)
        assertEquals(0, displayedStreak(started, later))
        val restarted = recordStudyDay(started, later)
        assertEquals(1, restarted.current)
        assertEquals(4, restarted.longest)
    }

    @Test
    fun reminderDelayLandsAtEightInTheEvening() {
        val before = ZonedDateTime.of(2026, 10, 29, 19, 0, 0, 0, zone)
        assertEquals(60L, nextReminderDelay(before).toMinutes())
        val onTheHour = ZonedDateTime.of(2026, 10, 29, 20, 0, 0, 0, zone)
        assertEquals(0L, nextReminderDelay(onTheHour).toMinutes())
        val after = ZonedDateTime.of(2026, 10, 29, 21, 30, 0, 0, zone)
        assertEquals(22 * 60L + 30L, nextReminderDelay(after).toMinutes())
        assertFalse(studiedToday(null, today))
    }
}
