package app.graphe.cartoes.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardKind {
    @SerialName("subject")
    SUBJECT,

    @SerialName("curiosity")
    CURIOSITY,

    @SerialName("name")
    NAME,

    @SerialName("place")
    PLACE,
}

@Serializable
data class Card(
    val id: String,
    val bookNumber: Int,
    val chapter: Int,
    val verseStart: Int,
    val verseEnd: Int? = null,
    val kind: CardKind,
    val prompt: String,
    val answer: String,
    val reference: String,
    val wrongs: List<String> = emptyList(),
)

@Serializable
data class CardFile(
    val cards: List<Card>,
)

enum class Grade {
    CORRECT,
    WRONG,
}

data class CardProgress(
    val cardId: String,
    val correctCount: Int,
    val wrongCount: Int,
    val correctStreak: Int,
    val lastResult: Grade?,
    val lastReviewedAtEpochMillis: Long?,
)

fun Card.pointsToVerse(): Boolean = chapter >= 1 && verseStart >= 1

fun Grade.stored(): String = when (this) {
    Grade.CORRECT -> "correct"
    Grade.WRONG -> "wrong"
}

fun gradeStored(value: String?): Grade? = when (value) {
    "correct" -> Grade.CORRECT
    "wrong" -> Grade.WRONG
    null -> null
    else -> null
}
