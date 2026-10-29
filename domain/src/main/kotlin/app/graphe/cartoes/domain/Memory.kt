package app.graphe.cartoes.domain

data class MemoryVerse(
    val bookNumber: Int,
    val chapter: Int,
    val verse: Int,
    val text: String,
)

data class MemoryPassage(
    val id: Long,
    val bookNumber: Int,
    val chapter: Int,
    val startVerse: Int,
    val endVerse: Int,
)

fun acfBookId(bookNumber: Int): Int? {
    val index = Canon.books.indexOfFirst { it.bookNumber == bookNumber }
    return if (index < 0) null else index + 1
}

fun passageLabel(bookNumber: Int, chapter: Int, startVerse: Int, endVerse: Int): String {
    val name = Canon.byNumber(bookNumber)?.longName ?: return ""
    return if (startVerse == endVerse) {
        "$name $chapter:$startVerse"
    } else {
        "$name $chapter:$startVerse–$endVerse"
    }
}
