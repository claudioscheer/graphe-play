package app.graphe.cartoes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        CardEntity::class,
        ProgressEntity::class,
        ReviewLogEntity::class,
        DeviceStateEntity::class,
        QuizResultEntity::class,
        QuizAnswerEntity::class,
        MemoryPassageEntity::class,
        MemoryReviewEntity::class,
    ],
    version = 4,
    exportSchema = false,
)
abstract class CartoesDatabase : RoomDatabase() {
    abstract fun dao(): CartoesDao

    abstract fun practiceDao(): PracticeDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS quiz_result (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        playedAtEpoch INTEGER NOT NULL,
                        scopeLabel TEXT NOT NULL,
                        asked INTEGER NOT NULL,
                        correctCount INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS quiz_answer (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        quizId INTEGER NOT NULL,
                        cardId TEXT NOT NULL,
                        prompt TEXT NOT NULL,
                        correctAnswer TEXT NOT NULL,
                        chosenAnswer TEXT NOT NULL,
                        wasCorrect INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS memory_passage (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        bookNumber INTEGER NOT NULL,
                        chapter INTEGER NOT NULL,
                        startVerse INTEGER NOT NULL,
                        endVerse INTEGER NOT NULL,
                        createdAtEpoch INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS memory_review (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        passageId INTEGER NOT NULL,
                        bookNumber INTEGER NOT NULL,
                        chapter INTEGER NOT NULL,
                        verse INTEGER NOT NULL,
                        result TEXT NOT NULL,
                        reviewedAtEpoch INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE device_state ADD COLUMN memoryTestament TEXT")
                db.execSQL("ALTER TABLE device_state ADD COLUMN memoryBookNumber INTEGER")
                db.execSQL("ALTER TABLE device_state ADD COLUMN memoryChapter INTEGER")
                db.execSQL("ALTER TABLE device_state ADD COLUMN memoryVerseStart INTEGER")
                db.execSQL("ALTER TABLE device_state ADD COLUMN memoryVerseEnd INTEGER")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE cards ADD COLUMN wrongs TEXT NOT NULL DEFAULT ''")
            }
        }

        fun create(context: Context): CartoesDatabase {
            return Room.databaseBuilder(context, CartoesDatabase::class.java, "cartoes.db")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .build()
        }
    }
}
