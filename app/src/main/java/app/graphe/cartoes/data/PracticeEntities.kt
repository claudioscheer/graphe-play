package app.graphe.cartoes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_result")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playedAtEpoch: Long,
    val scopeLabel: String,
    val asked: Int,
    val correctCount: Int,
)

@Entity(tableName = "quiz_answer")
data class QuizAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizId: Long,
    val cardId: String,
    val prompt: String,
    val correctAnswer: String,
    val chosenAnswer: String,
    val wasCorrect: Boolean,
)

@Entity(tableName = "memory_passage")
data class MemoryPassageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookNumber: Int,
    val chapter: Int,
    val startVerse: Int,
    val endVerse: Int,
    val createdAtEpoch: Long,
)

@Entity(tableName = "memory_review")
data class MemoryReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val passageId: Long,
    val bookNumber: Int,
    val chapter: Int,
    val verse: Int,
    val result: String,
    val reviewedAtEpoch: Long,
)
