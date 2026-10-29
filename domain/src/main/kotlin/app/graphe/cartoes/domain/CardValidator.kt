package app.graphe.cartoes.domain

data class CardProblem(
    val cardId: String?,
    val message: String,
)

private val idPattern = Regex("""^([0-9A-Z]{3})\.(\d+)\.(subject|curiosity|name|place)\.(\d+)$""")
private val referencePattern = Regex("""^(\S+)\s+(\d+):(\d+)(?:-(\d+))?$""")

const val MAX_PROMPT_LENGTH = 90
const val MAX_ANSWER_LENGTH = 140
const val MAX_HISTORY_REFERENCE_LENGTH = 40

fun validateCards(
    cards: List<Card>,
    requireEveryChapter: Boolean = false,
    requireBookAbout: Boolean = false,
    requireWrongs: Boolean = false,
): List<CardProblem> {
    val problems = mutableListOf<CardProblem>()
    val seen = mutableSetOf<String>()
    for (card in cards) {
        problems += problemsFor(card, seen, requireWrongs)
    }
    val byBook = cards.groupBy { it.bookNumber }
    val checkAbout = requireBookAbout || requireEveryChapter
    for ((number, bookCards) in byBook) {
        val book = Canon.byNumber(number) ?: continue
        for (chapter in 1..book.chapters) {
            val hasSubject = bookCards.any { it.chapter == chapter && it.kind == CardKind.SUBJECT }
            if (!hasSubject) {
                problems += CardProblem(null, "${book.usfm} $chapter não tem carta de assunto.")
            }
        }
        if (checkAbout && bookCards.none { it.chapter == 0 && it.kind == CardKind.CURIOSITY }) {
            problems += CardProblem(null, "${book.usfm} não tem curiosidade do livro.")
        }
    }
    if (requireEveryChapter) {
        for (book in Canon.books) {
            if (book.bookNumber !in byBook) {
                problems += CardProblem(null, "${book.usfm} não tem cartas.")
            }
        }
    }
    return problems
}

private fun problemsFor(
    card: Card,
    seen: MutableSet<String>,
    requireWrongs: Boolean,
): List<CardProblem> {
    val problems = mutableListOf<CardProblem>()
    fun problem(message: String) {
        problems += CardProblem(card.id, message)
    }
    if (!seen.add(card.id)) {
        problem("id repetido.")
    }
    checkWrongs(card, ::problem, requireWrongs)
    if (card.bookNumber == HISTORY_BOOK_NUMBER) {
        problems += historyProblems(card)
        return problems
    }
    val book = Canon.byNumber(card.bookNumber)
    if (book == null) {
        problem("bookNumber ${card.bookNumber} não está no cânon.")
        return problems
    }
    checkText(card, ::problem)
    val id = idPattern.matchEntire(card.id)
    if (id == null) {
        problem("id fora do padrão USFM.capítulo.tipo.n.")
    } else {
        if (id.groupValues[1] != book.usfm) {
            problem("USFM do id (${id.groupValues[1]}) não é ${book.usfm}.")
        }
        val idChapter = id.groupValues[2].toInt()
        if (idChapter != card.chapter) {
            problem("capítulo do id ($idChapter) diferente do campo chapter.")
        }
        if (id.groupValues[3] != kindToken(card.kind)) {
            problem("tipo do id (${id.groupValues[3]}) diferente de ${kindToken(card.kind)}.")
        }
    }
    if (card.chapter == 0) {
        if (card.kind != CardKind.CURIOSITY) {
            problem("carta do livro inteiro precisa ser curiosity.")
        }
        if (card.verseStart == 0) {
            if (card.verseEnd != null) {
                problem("verseEnd sem versículo.")
            }
            if (card.reference != book.longName) {
                problem("referência do livro deve ser ${book.longName}.")
            }
        } else {
            checkVerseReference(card, book, ::problem, requireSameChapter = false)
        }
    } else {
        if (card.chapter !in 1..book.chapters) {
            problem("capítulo ${card.chapter} fora de ${book.usfm} (1..${book.chapters}).")
        }
        if (card.verseStart < 1) {
            problem("verseStart precisa ser pelo menos 1.")
        }
        checkVerseReference(card, book, ::problem, requireSameChapter = true)
    }
    return problems
}

private fun historyProblems(card: Card): List<CardProblem> {
    val problems = mutableListOf<CardProblem>()
    fun problem(message: String) {
        problems += CardProblem(card.id, message)
    }
    checkText(card, ::problem)
    if (card.chapter != 0 || card.verseStart != 0 || card.verseEnd != null) {
        problem("carta de história não leva capítulo nem versículo.")
    }
    if (card.kind != CardKind.CURIOSITY) {
        problem("carta de história precisa ser curiosity.")
    }
    val id = Regex("""^HIS\.0\.curiosity\.(\d+)$""").matchEntire(card.id)
    if (id == null) {
        problem("id de história fora do padrão HIS.0.curiosity.n.")
    }
    if (card.reference.isBlank()) {
        problem("referência vazia.")
    } else if (card.reference.length > MAX_HISTORY_REFERENCE_LENGTH) {
        problem("referência de história com ${card.reference.length} caracteres.")
    }
    return problems
}

private fun checkWrongs(card: Card, problem: (String) -> Unit, required: Boolean) {
    if (card.wrongs.isEmpty() && !required) {
        return
    }
    if (card.wrongs.size != 3) {
        problem("precisa de 3 respostas erradas.")
        return
    }
    val seen = mutableSetOf(card.answer)
    for (wrong in card.wrongs) {
        if (wrong.isBlank()) {
            problem("resposta errada vazia.")
        } else if (wrong.length > MAX_ANSWER_LENGTH) {
            problem("resposta errada com ${wrong.length} caracteres (máximo $MAX_ANSWER_LENGTH).")
        } else if (!wrong.endsWith(".")) {
            problem("resposta errada precisa terminar com ponto.")
        }
        if (!seen.add(wrong)) {
            problem("resposta errada repete a certa ou outra opção.")
        }
    }
}

private fun checkText(card: Card, problem: (String) -> Unit) {
    if (card.prompt.isBlank()) {
        problem("pergunta vazia.")
    } else if (card.prompt.length > MAX_PROMPT_LENGTH) {
        problem("pergunta com ${card.prompt.length} caracteres (máximo $MAX_PROMPT_LENGTH).")
    }
    if (card.answer.isBlank()) {
        problem("resposta vazia.")
    } else if (card.answer.length > MAX_ANSWER_LENGTH) {
        problem("resposta com ${card.answer.length} caracteres (máximo $MAX_ANSWER_LENGTH).")
    }
}

private fun checkVerseReference(
    card: Card,
    book: BibleBook,
    problem: (String) -> Unit,
    requireSameChapter: Boolean,
) {
    if (card.verseEnd != null && card.verseEnd < card.verseStart) {
        problem("verseEnd anterior a verseStart.")
    }
    if (card.reference.isBlank()) {
        problem("referência vazia.")
        return
    }
    val reference = referencePattern.matchEntire(card.reference)
    if (reference == null) {
        problem("referência fora do padrão \"${book.shortName} ${card.chapter}:${card.verseStart}\".")
        return
    }
    val abbrev = reference.groupValues[1]
    val chapter = reference.groupValues[2].toInt()
    val start = reference.groupValues[3].toInt()
    val end = reference.groupValues[4].takeIf { it.isNotEmpty() }?.toInt()
    if (abbrev != book.shortName) {
        problem("abreviação \"$abbrev\" não é ${book.shortName}.")
    }
    if (chapter !in 1..book.chapters) {
        problem("capítulo da referência ($chapter) fora de ${book.usfm}.")
    } else if (requireSameChapter && chapter != card.chapter) {
        problem("capítulo da referência ($chapter) diferente do campo chapter.")
    }
    if (start != card.verseStart) {
        problem("versículo da referência ($start) diferente de verseStart.")
    }
    if (end == null) {
        if (card.verseEnd != null && card.verseEnd != card.verseStart) {
            problem("referência sem intervalo, mas verseEnd é ${card.verseEnd}.")
        }
    } else if (card.verseEnd != end) {
        problem("intervalo da referência termina em $end, verseEnd é ${card.verseEnd}.")
    }
}

private fun kindToken(kind: CardKind): String = when (kind) {
    CardKind.SUBJECT -> "subject"
    CardKind.CURIOSITY -> "curiosity"
    CardKind.NAME -> "name"
    CardKind.PLACE -> "place"
}
