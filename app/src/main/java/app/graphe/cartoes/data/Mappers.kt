package app.graphe.cartoes.data

import app.graphe.cartoes.domain.Card
import app.graphe.cartoes.domain.CardKind
import app.graphe.cartoes.domain.CardProgress
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.StreakState
import app.graphe.cartoes.domain.gradeStored
import app.graphe.cartoes.domain.scopeFromStored
import app.graphe.cartoes.domain.stored
import app.graphe.cartoes.domain.toStored
import java.time.LocalDate

fun Card.toEntity(): CardEntity = CardEntity(
    id = id,
    bookNumber = bookNumber,
    chapter = chapter,
    verseStart = verseStart,
    verseEnd = verseEnd,
    kind = when (kind) {
        CardKind.SUBJECT -> "subject"
        CardKind.CURIOSITY -> "curiosity"
        CardKind.NAME -> "name"
        CardKind.PLACE -> "place"
    },
    prompt = prompt,
    answer = answer,
    reference = reference,
    wrongs = wrongs.joinToString("\u001f"),
)

fun CardEntity.toDomain(): Card = Card(
    id = id,
    bookNumber = bookNumber,
    chapter = chapter,
    verseStart = verseStart,
    verseEnd = verseEnd,
    kind = when (kind) {
        "curiosity" -> CardKind.CURIOSITY
        "name" -> CardKind.NAME
        "place" -> CardKind.PLACE
        else -> CardKind.SUBJECT
    },
    prompt = prompt,
    answer = answer,
    reference = reference,
    wrongs = if (wrongs.isEmpty()) emptyList() else wrongs.split("\u001f"),
)

fun CardProgress.toEntity(): ProgressEntity = ProgressEntity(
    cardId = cardId,
    correctCount = correctCount,
    wrongCount = wrongCount,
    correctStreak = correctStreak,
    lastResult = lastResult?.stored(),
    lastReviewedAtEpochMillis = lastReviewedAtEpochMillis,
)

fun ProgressEntity.toDomain(): CardProgress = CardProgress(
    cardId = cardId,
    correctCount = correctCount,
    wrongCount = wrongCount,
    correctStreak = correctStreak,
    lastResult = gradeStored(lastResult),
    lastReviewedAtEpochMillis = lastReviewedAtEpochMillis,
)

fun DeviceStateEntity.toStreak(): StreakState = StreakState(
    current = currentStreak,
    longest = longestStreak,
    lastStudyDate = lastStudyDate?.let(LocalDate::parse),
)

fun DeviceStateEntity.toScope(): Scope = scopeFromStored(scopeTestament, scopeBookNumber, scopeChapter)

fun DeviceStateEntity.withScope(scope: Scope): DeviceStateEntity {
    val stored = scope.toStored()
    return copy(
        scopeTestament = stored.testament,
        scopeBookNumber = stored.bookNumber,
        scopeChapter = stored.chapter,
    )
}

fun DeviceStateEntity.withStreak(streak: StreakState): DeviceStateEntity = copy(
    currentStreak = streak.current,
    longestStreak = streak.longest,
    lastStudyDate = streak.lastStudyDate?.toString(),
)

fun Grade.toStored(): String = stored()
