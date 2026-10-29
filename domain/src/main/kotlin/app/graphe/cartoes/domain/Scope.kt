package app.graphe.cartoes.domain

/** Stored in `scopeTestament` when the deck is church history, not a Bible book. */
const val HISTORY_SCOPE_KEY = "HISTORY"

/** Cards about church history use this book number. It is not a Graphe Bible book. */
const val HISTORY_BOOK_NUMBER = 0

data class Scope(
    val testament: Testament? = null,
    val bookNumber: Int? = null,
    val chapter: Int? = null,
    val history: Boolean = false,
) {
    fun isAll(): Boolean = testament == null && bookNumber == null && chapter == null && !history

    companion object {
        val all: Scope = Scope()
        val history: Scope = Scope(history = true)
    }
}

data class StoredScope(
    val testament: String?,
    val bookNumber: Int?,
    val chapter: Int?,
)

fun Scope.toStored(): StoredScope = when {
    history -> StoredScope(HISTORY_SCOPE_KEY, null, null)
    bookNumber != null -> StoredScope(null, bookNumber, chapter)
    else -> StoredScope(testament?.name, null, null)
}

fun scopeFromStored(testament: String?, bookNumber: Int?, chapter: Int?): Scope {
    if (bookNumber != null) {
        return Scope(bookNumber = bookNumber, chapter = chapter)
    }
    if (testament == HISTORY_SCOPE_KEY) {
        return Scope.history
    }
    val parsed = when (testament) {
        Testament.OLD.name -> Testament.OLD
        Testament.NEW.name -> Testament.NEW
        else -> null
    }
    return Scope(testament = parsed)
}

fun scopeLabel(scope: Scope): String {
    val book = scope.bookNumber?.let(Canon::byNumber)
    if (book != null && scope.chapter != null) {
        return "${book.longName} ${scope.chapter}"
    }
    if (book != null) {
        return book.longName
    }
    if (scope.history) {
        return "História"
    }
    return when (scope.testament) {
        Testament.OLD -> "Antigo Testamento"
        Testament.NEW -> "Novo Testamento"
        null -> "Toda a Bíblia"
    }
}

fun Card.matches(scope: Scope): Boolean {
    if (bookNumber == HISTORY_BOOK_NUMBER) {
        return scope.history
    }
    if (scope.history) {
        return false
    }
    val book = Canon.byNumber(bookNumber) ?: return false
    if (scope.bookNumber != null && bookNumber != scope.bookNumber) {
        return false
    }
    if (scope.bookNumber != null && scope.chapter != null && chapter != scope.chapter) {
        return false
    }
    if (scope.bookNumber == null && scope.testament != null && book.testament != scope.testament) {
        return false
    }
    return true
}
