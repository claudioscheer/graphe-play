package app.graphe.cartoes.domain

/**
 * Protestant 66-book canon in Graphe order.
 * Book numbers match graphe-bible `GRAPHE_BOOK_NUMBERS` (MyBible gaps left empty).
 * Portuguese names match graphe-bible `i18n.ts` (`pt`).
 */
enum class Testament {
    OLD,
    NEW,
}

data class BibleBook(
    val usfm: String,
    val bookNumber: Int,
    val chapters: Int,
    val shortName: String,
    val longName: String,
    val testament: Testament,
)

object Canon {
    val books: List<BibleBook> = listOf(
        book("GEN", 10, 50, "Gn", "Gênesis", Testament.OLD),
        book("EXO", 20, 40, "Êx", "Êxodo", Testament.OLD),
        book("LEV", 30, 27, "Lv", "Levítico", Testament.OLD),
        book("NUM", 40, 36, "Nm", "Números", Testament.OLD),
        book("DEU", 50, 34, "Dt", "Deuteronômio", Testament.OLD),
        book("JOS", 60, 24, "Js", "Josué", Testament.OLD),
        book("JDG", 70, 21, "Jz", "Juízes", Testament.OLD),
        book("RUT", 80, 4, "Rt", "Rute", Testament.OLD),
        book("1SA", 90, 31, "1Sm", "1 Samuel", Testament.OLD),
        book("2SA", 100, 24, "2Sm", "2 Samuel", Testament.OLD),
        book("1KI", 110, 22, "1Rs", "1 Reis", Testament.OLD),
        book("2KI", 120, 25, "2Rs", "2 Reis", Testament.OLD),
        book("1CH", 130, 29, "1Cr", "1 Crônicas", Testament.OLD),
        book("2CH", 140, 36, "2Cr", "2 Crônicas", Testament.OLD),
        book("EZR", 150, 10, "Ed", "Esdras", Testament.OLD),
        book("NEH", 160, 13, "Ne", "Neemias", Testament.OLD),
        book("EST", 190, 10, "Et", "Ester", Testament.OLD),
        book("JOB", 220, 42, "Jó", "Jó", Testament.OLD),
        book("PSA", 230, 150, "Sl", "Salmos", Testament.OLD),
        book("PRO", 240, 31, "Pv", "Provérbios", Testament.OLD),
        book("ECC", 250, 12, "Ec", "Eclesiastes", Testament.OLD),
        book("SNG", 260, 8, "Ct", "Cantares", Testament.OLD),
        book("ISA", 290, 66, "Is", "Isaías", Testament.OLD),
        book("JER", 300, 52, "Jr", "Jeremias", Testament.OLD),
        book("LAM", 310, 5, "Lm", "Lamentações", Testament.OLD),
        book("EZK", 330, 48, "Ez", "Ezequiel", Testament.OLD),
        book("DAN", 340, 12, "Dn", "Daniel", Testament.OLD),
        book("HOS", 350, 14, "Os", "Oséias", Testament.OLD),
        book("JOL", 360, 3, "Jl", "Joel", Testament.OLD),
        book("AMO", 370, 9, "Am", "Amós", Testament.OLD),
        book("OBA", 380, 1, "Ob", "Obadias", Testament.OLD),
        book("JON", 390, 4, "Jn", "Jonas", Testament.OLD),
        book("MIC", 400, 7, "Mq", "Miquéias", Testament.OLD),
        book("NAM", 410, 3, "Na", "Naum", Testament.OLD),
        book("HAB", 420, 3, "Hc", "Habacuque", Testament.OLD),
        book("ZEP", 430, 3, "Sf", "Sofonias", Testament.OLD),
        book("HAG", 440, 2, "Ag", "Ageu", Testament.OLD),
        book("ZEC", 450, 14, "Zc", "Zacarias", Testament.OLD),
        book("MAL", 460, 4, "Ml", "Malaquias", Testament.OLD),
        book("MAT", 470, 28, "Mt", "Mateus", Testament.NEW),
        book("MRK", 480, 16, "Mc", "Marcos", Testament.NEW),
        book("LUK", 490, 24, "Lc", "Lucas", Testament.NEW),
        book("JHN", 500, 21, "Jo", "João", Testament.NEW),
        book("ACT", 510, 28, "At", "Atos", Testament.NEW),
        book("ROM", 520, 16, "Rm", "Romanos", Testament.NEW),
        book("1CO", 530, 16, "1Co", "1 Coríntios", Testament.NEW),
        book("2CO", 540, 13, "2Co", "2 Coríntios", Testament.NEW),
        book("GAL", 550, 6, "Gl", "Gálatas", Testament.NEW),
        book("EPH", 560, 6, "Ef", "Efésios", Testament.NEW),
        book("PHP", 570, 4, "Fp", "Filipenses", Testament.NEW),
        book("COL", 580, 4, "Cl", "Colossenses", Testament.NEW),
        book("1TH", 590, 5, "1Ts", "1 Tessalonicenses", Testament.NEW),
        book("2TH", 600, 3, "2Ts", "2 Tessalonicenses", Testament.NEW),
        book("1TI", 610, 6, "1Tm", "1 Timóteo", Testament.NEW),
        book("2TI", 620, 4, "2Tm", "2 Timóteo", Testament.NEW),
        book("TIT", 630, 3, "Tt", "Tito", Testament.NEW),
        book("PHM", 640, 1, "Fm", "Filemom", Testament.NEW),
        book("HEB", 650, 13, "Hb", "Hebreus", Testament.NEW),
        book("JAS", 660, 5, "Tg", "Tiago", Testament.NEW),
        book("1PE", 670, 5, "1Pe", "1 Pedro", Testament.NEW),
        book("2PE", 680, 3, "2Pe", "2 Pedro", Testament.NEW),
        book("1JN", 690, 5, "1Jo", "1 João", Testament.NEW),
        book("2JN", 700, 1, "2Jo", "2 João", Testament.NEW),
        book("3JN", 710, 1, "3Jo", "3 João", Testament.NEW),
        book("JUD", 720, 1, "Jd", "Judas", Testament.NEW),
        book("REV", 730, 22, "Ap", "Apocalipse", Testament.NEW),
    )

    val chapterCount: Int = books.sumOf { it.chapters }

    private val numberIndex = books.associateBy { it.bookNumber }
    private val usfmIndex = books.associateBy { it.usfm }

    fun byNumber(bookNumber: Int): BibleBook? = numberIndex[bookNumber]

    fun byUsfm(usfm: String): BibleBook? = usfmIndex[usfm]
}

private fun book(
    usfm: String,
    bookNumber: Int,
    chapters: Int,
    shortName: String,
    longName: String,
    testament: Testament,
): BibleBook = BibleBook(usfm, bookNumber, chapters, shortName, longName, testament)
