package app.graphe.cartoes.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.graphe.cartoes.domain.Canon
import app.graphe.cartoes.domain.MemoryVerse
import app.graphe.cartoes.domain.Testament
import java.io.File

/** Read-only Almeida Corrigida Fiel 2011 text shipped with the app. */
class AcfBible(private val context: Context) {
    private val database: SQLiteDatabase by lazy { open() }

    fun verseCount(bookNumber: Int, chapter: Int): Int {
        if (Canon.byNumber(bookNumber) == null) return 0
        database.rawQuery(
            "SELECT COUNT(*) FROM verses WHERE book_number = ? AND chapter = ?",
            arrayOf(bookNumber.toString(), chapter.toString()),
        ).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) else 0
        }
    }

    fun verses(bookNumber: Int, chapter: Int, startVerse: Int, endVerse: Int): List<MemoryVerse> {
        if (Canon.byNumber(bookNumber) == null) return emptyList()
        val from = minOf(startVerse, endVerse)
        val to = maxOf(startVerse, endVerse)
        database.query(
            "verses",
            arrayOf("verse", "text"),
            "book_number = ? AND chapter = ? AND verse >= ? AND verse <= ?",
            arrayOf(bookNumber.toString(), chapter.toString(), from.toString(), to.toString()),
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
                    text = plain(cursor.getString(textIndex)),
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
            if (Canon.byNumber(bookNumber) == null) return null
            where.append(" AND book_number = ?")
            args += bookNumber.toString()
        } else if (oldTestament != null) {
            where.append(" AND ${testamentClause(oldTestament)}")
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
            "SELECT book_number, chapter, verse, text FROM verses WHERE $where ORDER BY RANDOM() LIMIT 1",
            args.toTypedArray(),
        ).use { cursor ->
            if (!cursor.moveToFirst()) {
                return null
            }
            val grapheBook = cursor.getInt(0)
            if (Canon.byNumber(grapheBook) == null) return null
            return MemoryVerse(
                bookNumber = grapheBook,
                chapter = cursor.getInt(1),
                verse = cursor.getInt(2),
                text = plain(cursor.getString(3)),
            )
        }
    }

    private fun open(): SQLiteDatabase {
        val out = File(context.filesDir, "ACF.sqlite")
        val stamp = File(context.filesDir, "ACF.sqlite.stamp")
        if (!out.exists() || out.length() == 0L || !stamp.exists() || stamp.readText() != TEXT_VERSION) {
            context.assets.open("bible/ACF.sqlite").use { input ->
                out.outputStream().use { output -> input.copyTo(output) }
            }
            stamp.writeText(TEXT_VERSION)
        }
        return SQLiteDatabase.openDatabase(out.path, null, SQLiteDatabase.OPEN_READONLY)
    }

    private companion object {
        const val TEXT_VERSION = "3"

        fun testamentClause(oldTestament: Boolean): String {
            val numbers = Canon.books
                .filter { (it.testament == Testament.OLD) == oldTestament }
                .map { it.bookNumber }
            return "book_number IN (${numbers.joinToString(",")})"
        }

        fun plain(text: String): String = text.replace(Regex("<[^>]+>"), "").trim()
    }
}
