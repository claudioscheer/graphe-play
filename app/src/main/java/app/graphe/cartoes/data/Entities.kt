package app.graphe.cartoes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: String,
    val bookNumber: Int,
    val chapter: Int,
    val verseStart: Int,
    val verseEnd: Int?,
    val kind: String,
    val prompt: String,
    val answer: String,
    val reference: String,
    val wrongs: String = "",
)

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val cardId: String,
    val correctCount: Int,
    val wrongCount: Int,
    val correctStreak: Int,
    val lastResult: String?,
    val lastReviewedAtEpochMillis: Long?,
)

@Entity(tableName = "review_log")
data class ReviewLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cardId: String,
    val result: String,
    val reviewedAtEpochMillis: Long,
)

@Entity(tableName = "device_state")
data class DeviceStateEntity(
    @PrimaryKey val id: Int = 1,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastStudyDate: String? = null,
    val scopeTestament: String? = null,
    val scopeBookNumber: Int? = null,
    val scopeChapter: Int? = null,
    val loadedAssetVersion: Int = 0,
    val memoryTestament: String? = null,
    val memoryBookNumber: Int? = null,
    val memoryChapter: Int? = null,
    val memoryVerseStart: Int? = null,
    val memoryVerseEnd: Int? = null,
)
