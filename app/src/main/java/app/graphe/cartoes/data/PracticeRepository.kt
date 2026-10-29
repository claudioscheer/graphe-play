package app.graphe.cartoes.data

import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.MemoryPassage
import app.graphe.cartoes.domain.MemoryVerse
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.Testament
import app.graphe.cartoes.domain.scopeFromStored
import app.graphe.cartoes.domain.stored
import app.graphe.cartoes.domain.toStored
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class MemorySelection(
    val scope: Scope,
    val verseStart: Int?,
    val verseEnd: Int?,
)

class PracticeRepository(
    private val dao: PracticeDao,
    private val stateDao: CartoesDao,
    private val bible: AcfBible,
) {
    suspend fun recentQuizzes(): List<QuizResultEntity> = dao.recentQuizzes()

    suspend fun quizAnswers(quizId: Long): List<QuizAnswerEntity> = dao.quizAnswers(quizId)

    suspend fun deleteQuiz(quizId: Long) {
        dao.deleteQuizAnswers(quizId)
        dao.deleteQuizResult(quizId)
    }

    suspend fun sampleVerses(scope: Scope, count: Int): List<MemoryVerse> = withContext(Dispatchers.IO) {
        val picked = mutableListOf<MemoryVerse>()
        repeat(count * 6) {
            if (picked.size >= count) {
                return@withContext picked
            }
            val verse = bible.randomVerse(
                bookNumber = scope.bookNumber,
                oldTestament = when (scope.testament) {
                    Testament.OLD -> true
                    Testament.NEW -> false
                    null -> null
                },
                chapter = scope.chapter,
                startVerse = null,
                endVerse = null,
                avoidChapter = null,
                avoidVerse = null,
            )
            if (verse != null && picked.none { it.bookNumber == verse.bookNumber && it.chapter == verse.chapter && it.verse == verse.verse }) {
                picked += verse
            }
        }
        picked
    }

    suspend fun saveQuiz(
        playedAtEpoch: Long,
        scopeLabel: String,
        answers: List<QuizAnswerEntity>,
    ) {
        val correct = answers.count { it.wasCorrect }
        val id = dao.insertQuiz(
            QuizResultEntity(
                playedAtEpoch = playedAtEpoch,
                scopeLabel = scopeLabel,
                asked = answers.size,
                correctCount = correct,
            ),
        )
        if (answers.isNotEmpty()) {
            dao.insertAnswers(answers.map { it.copy(quizId = id) })
        }
    }

    suspend fun passages(): List<MemoryPassage> {
        return dao.passages().map {
            MemoryPassage(it.id, it.bookNumber, it.chapter, it.startVerse, it.endVerse)
        }
    }

    suspend fun verseCount(bookNumber: Int, chapter: Int): Int = withContext(Dispatchers.IO) {
        bible.verseCount(bookNumber, chapter)
    }

    suspend fun verses(bookNumber: Int, chapter: Int, startVerse: Int, endVerse: Int): List<MemoryVerse> =
        withContext(Dispatchers.IO) {
            bible.verses(bookNumber, chapter, startVerse, endVerse)
        }

    suspend fun savePassage(bookNumber: Int, chapter: Int, startVerse: Int, endVerse: Int, now: Long): Long {
        return dao.insertPassage(
            MemoryPassageEntity(
                bookNumber = bookNumber,
                chapter = chapter,
                startVerse = minOf(startVerse, endVerse),
                endVerse = maxOf(startVerse, endVerse),
                createdAtEpoch = now,
            ),
        )
    }

    suspend fun memorySelection(): MemorySelection {
        val state = stateDao.deviceState()
        return MemorySelection(
            scope = scopeFromStored(state?.memoryTestament, state?.memoryBookNumber, state?.memoryChapter),
            verseStart = state?.memoryVerseStart,
            verseEnd = state?.memoryVerseEnd,
        )
    }

    suspend fun setMemorySelection(scope: Scope, verseStart: Int?, verseEnd: Int?) {
        val state = stateDao.deviceState() ?: DeviceStateEntity()
        val stored = scope.toStored()
        stateDao.upsertState(
            state.copy(
                memoryTestament = stored.testament,
                memoryBookNumber = stored.bookNumber,
                memoryChapter = stored.chapter,
                memoryVerseStart = verseStart,
                memoryVerseEnd = verseEnd,
            ),
        )
    }

    suspend fun randomVerse(
        scope: Scope,
        verseStart: Int?,
        verseEnd: Int?,
        avoidChapter: Int?,
        avoidVerse: Int?,
    ): MemoryVerse? = withContext(Dispatchers.IO) {
        bible.randomVerse(
            bookNumber = scope.bookNumber,
            oldTestament = when (scope.testament) {
                Testament.OLD -> true
                Testament.NEW -> false
                null -> null
            },
            chapter = scope.chapter,
            startVerse = verseStart,
            endVerse = verseEnd,
            avoidChapter = avoidChapter,
            avoidVerse = avoidVerse,
        )
    }

    suspend fun saveReview(
        passageId: Long,
        verse: MemoryVerse,
        grade: Grade,
        now: Long,
    ) {
        dao.insertReview(
            MemoryReviewEntity(
                passageId = passageId,
                bookNumber = verse.bookNumber,
                chapter = verse.chapter,
                verse = verse.verse,
                result = grade.stored(),
                reviewedAtEpoch = now,
            ),
        )
    }
}
