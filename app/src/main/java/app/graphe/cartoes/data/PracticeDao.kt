package app.graphe.cartoes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PracticeDao {
    @Insert
    suspend fun insertQuiz(result: QuizResultEntity): Long

    @Insert
    suspend fun insertAnswers(answers: List<QuizAnswerEntity>)

    @Query("SELECT * FROM quiz_result ORDER BY playedAtEpoch DESC LIMIT 12")
    suspend fun recentQuizzes(): List<QuizResultEntity>

    @Query("SELECT * FROM quiz_answer WHERE quizId = :quizId ORDER BY id")
    suspend fun quizAnswers(quizId: Long): List<QuizAnswerEntity>

    @Query("DELETE FROM quiz_answer WHERE quizId = :quizId")
    suspend fun deleteQuizAnswers(quizId: Long)

    @Query("DELETE FROM quiz_result WHERE id = :quizId")
    suspend fun deleteQuizResult(quizId: Long)

    @Insert
    suspend fun insertPassage(passage: MemoryPassageEntity): Long

    @Query("SELECT * FROM memory_passage ORDER BY createdAtEpoch DESC")
    suspend fun passages(): List<MemoryPassageEntity>

    @Insert
    suspend fun insertReview(review: MemoryReviewEntity)
}
