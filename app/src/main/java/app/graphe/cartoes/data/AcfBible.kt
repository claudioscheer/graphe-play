package app.graphe.cartoes.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.graphe.cartoes.domain.Canon
import app.graphe.cartoes.domain.MemoryVerse
import app.graphe.cartoes.domain.acfBookId
import java.io.File

/** Read-only Almeida Corrigida Fiel text shipped with the app. */
class AcfBible(private val context: Context) {
    private val database: SQLiteDatabase by lazy { open() }

    fun verseCount(bookNumber: Int, chapter: Int): Int {
        val bookId = acfBookId(bookNumber) ?: return 0
        database.rawQuery(
            "SELECT COUNT(*) FROM verse WHERE book_id = ? AND chapter = ?",
            arrayOf(bookId.toString(), chapter.toString()),
        ).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) else 0
        }
    }

    fun verses(bookNumber: Int, chapter: Int, startVerse: Int, endVerse: Int): List<MemoryVerse> {
        val bookId = acfBookId(bookNumber) ?: return emptyList()
        val from = minOf(startVerse, endVerse)
        val to = maxOf(startVerse, endVerse)
        database.query(
            "verse",
            arrayOf("verse", "text"),
            "book_id = ? AND chapter = ? AND verse >= ? AND verse <= ?",
            arrayOf(bookId.toString(), chapter.toString(), from.toString(), to.toString()),
            null,
            null,
            "verse ASC",
        ).use { cursor ->
            val verseIndex = cursor.getColumnIndexOrThrow("verse")
            val textIndex = cursor.getColumnIndexOrThrow("text")
            val verses = ArrayList<MemoryVerse>(cursor.count)
            while (cursor.moveToNext()) {
                verses += MemoryVerse(
                    bookNumber = bookNumber,
                    chapter = chapter,
                    verse = cursor.getInt(verseIndex),
                    text = cursor.getString(textIndex),
                )
            }
            return verses
        }
    }

    fun randomVerse(
        bookNumber: Int?,
        oldTestament: Boolean?,
        chapter: Int?,
        startVerse: Int?,
        endVerse: Int?,
        avoidChapter: Int?,
        avoidVerse: Int?,
    ): MemoryVerse? {
        return pick(bookNumber, oldTestament, chapter, startVerse, endVerse, avoidChapter, avoidVerse)
            ?: pick(bookNumber, oldTestament, chapter, startVerse, endVerse, null, null)
    }

    private fun pick(
        bookNumber: Int?,
        oldTestament: Boolean?,
        chapter: Int?,
        startVerse: Int?,
        endVerse: Int?,
        avoidChapter: Int?,
        avoidVerse: Int?,
    ): MemoryVerse? {
        val where = StringBuilder("1=1")
        val args = ArrayList<String>()
        if (bookNumber != null) {
            val bookId = acfBookId(bookNumber) ?: return null
            where.append(" AND book_id = ?")
            args += bookId.toString()
        } else if (oldTestament != null) {
            where.append(if (oldTestament) " AND book_id <= 39" else " AND book_id >= 40")
        }
        if (chapter != null) {
            where.append(" AND chapter = ?")
            args += chapter.toString()
        }
        if (chapter != null && startVerse != null && endVerse != null) {
            where.append(" AND verse >= ? AND verse <= ?")
            args += minOf(startVerse, endVerse).toString()
            args += maxOf(startVerse, endVerse).toString()
        }
        if (avoidChapter != null && avoidVerse != null) {
            where.append(" AND NOT (chapter = ? AND verse = ?)")
            args += avoidChapter.toString()
            args += avoidVerse.toString()
        }
        database.rawQuery(
            "SELECT book_id, chapter, verse, text FROM verse WHERE $where ORDER BY RANDOM() LIMIT 1",
            args.toTypedArray(),
        ).use { cursor ->
            if (!cursor.moveToFirst()) {
                return null
            }
            val acfBook = cursor.getInt(0)
            val grapheBook = Canon.books.getOrNull(acfBook - 1)?.bookNumber ?: return null
            return MemoryVerse(
                bookNumber = grapheBook,
                chapter = cursor.getInt(1),
                verse = cursor.getInt(2),
                text = cursor.getString(3),
            )
        }
    }

    private fun open(): SQLiteDatabase {
        val out = File(context.filesDir, "ACF.sqlite")
        if (!out.exists() || out.length() == 0L) {
            context.assets.open("bible/ACF.sqlite").use { input ->
                out.outputStream().use { output -> input.copyTo(output) }
            }
        }
        return SQLiteDatabase.openDatabase(out.path, null, SQLiteDatabase.OPEN_READONLY)
    }
}
