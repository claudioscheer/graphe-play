package app.graphe.cartoes.domain

import java.time.Duration
import java.time.LocalDate
import java.time.ZonedDateTime

data class StreakState(
    val current: Int,
    val longest: Int,
    val lastStudyDate: LocalDate?,
)

fun displayedStreak(state: StreakState, today: LocalDate): Int {
    val last = state.lastStudyDate ?: return 0
    return if (last == today || last == today.minusDays(1)) state.current else 0
}

fun studiedToday(lastStudyDate: LocalDate?, today: LocalDate): Boolean = lastStudyDate == today

/**
 * One graded card on [today] keeps or grows the streak.
 * The same day does not add another day. A gap starts the count at 1.
 */
fun recordStudyDay(state: StreakState, today: LocalDate): StreakState {
    val next = when (state.lastStudyDate) {
        today -> state.current
        today.minusDays(1) -> state.current + 1
        else -> 1
    }
    return StreakState(
        current = next,
        longest = maxOf(state.longest, next),
        lastStudyDate = today,
    )
}

/** Delay until the next [minutesFromMidnight] in [now]'s zone. Zero if that moment is now. */
fun nextReminderDelay(now: ZonedDateTime, minutesFromMidnight: Int = 20 * 60): Duration {
    require(minutesFromMidnight in 0 until 24 * 60)
    val target = now.toLocalDate().atStartOfDay(now.zone).plusMinutes(minutesFromMidnight.toLong())
    val whenNext = if (!now.isAfter(target)) target else target.plusDays(1)
    return Duration.between(now, whenNext)
}
