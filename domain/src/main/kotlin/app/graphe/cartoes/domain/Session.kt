package app.graphe.cartoes.domain

import java.time.LocalDate
import kotlin.random.Random

enum class Face {
    FRONT,
    BACK,
}

data class StudySession(
    val face: Face,
    val currentId: String?,
    val progress: Map<String, CardProgress>,
    val streak: StreakState,
)

fun applyGrade(previous: CardProgress?, cardId: String, grade: Grade, atMillis: Long): CardProgress {
    val base = previous ?: CardProgress(
        cardId = cardId,
        correctCount = 0,
        wrongCount = 0,
        correctStreak = 0,
        lastResult = null,
        lastReviewedAtEpochMillis = null,
    )
    return when (grade) {
        Grade.CORRECT -> base.copy(
            correctCount = base.correctCount + 1,
            correctStreak = base.correctStreak + 1,
            lastResult = Grade.CORRECT,
            lastReviewedAtEpochMillis = atMillis,
        )
        Grade.WRONG -> base.copy(
            wrongCount = base.wrongCount + 1,
            correctStreak = 0,
            lastResult = Grade.WRONG,
            lastReviewedAtEpochMillis = atMillis,
        )
    }
}

fun startSession(
    cards: List<Card>,
    progress: Map<String, CardProgress>,
    streak: StreakState,
    random: Random,
): StudySession {
    val next = Deck.next(cards, excludeId = null, random = random)
    return StudySession(
        face = Face.FRONT,
        currentId = next?.id,
        progress = progress,
        streak = streak,
    )
}

fun flip(session: StudySession): StudySession {
    if (session.currentId == null) {
        return session
    }
    val face = if (session.face == Face.FRONT) Face.BACK else Face.FRONT
    return session.copy(face = face)
}

/**
 * A right or wrong mark stores the grade and draws the next card face-up.
 * Revealing the answer is a separate tap on the card.
 */
fun mark(
    session: StudySession,
    cards: List<Card>,
    grade: Grade,
    today: LocalDate,
    nowMillis: Long,
    random: Random,
): StudySession {
    val current = session.currentId ?: return session
    val updated = applyGrade(session.progress[current], current, grade, nowMillis)
    val progress = session.progress + (current to updated)
    val streak = recordStudyDay(session.streak, today)
    val next = Deck.next(cards, excludeId = current, random = random)
    return StudySession(
        face = Face.FRONT,
        currentId = next?.id,
        progress = progress,
        streak = streak,
    )
}
