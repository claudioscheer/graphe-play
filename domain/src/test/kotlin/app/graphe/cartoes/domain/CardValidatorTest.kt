package app.graphe.cartoes.domain

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class CardValidatorTest {
    private val json = Json { ignoreUnknownKeys = false }

    @Test
    fun bundledBooksAreComplete() {
        val problems = validateCards(loadBundledCards(), requireBookAbout = true, requireWrongs = true)
        assertEquals(problems.joinToString("\n"), emptyList<CardProblem>(), problems)
    }

    @Test
    fun fullCanon() {
        val problems = validateCards(loadBundledCards(), requireEveryChapter = true, requireWrongs = true)
        assertTrue(problems.joinToString("\n"), problems.isEmpty())
    }

    @Test
    fun onlyAChapterAndVerseCanOpenTheText() {
        val verse = obadiah()
        val bookNote = obadiah(reference = "Obadias").copy(chapter = 0, verseStart = 0, id = "OBA.0.curiosity.1")
        assertTrue(verse.pointsToVerse())
        assertTrue(!bookNote.pointsToVerse())
    }

    @Test
    fun rejectsAPromptThatDoesNotFitTheCard() {
        val card = obadiah(prompt = "x".repeat(MAX_PROMPT_LENGTH + 1))
        val messages = validateCards(listOf(card)).map { it.message }
        assertEquals(listOf("pergunta com ${MAX_PROMPT_LENGTH + 1} caracteres (máximo $MAX_PROMPT_LENGTH)."), messages)
    }

    @Test
    fun acceptsABookCuriosityWithoutAVerse() {
        val cards = listOf(obadiah(), bookCuriosity())
        assertEquals(emptyList<CardProblem>(), validateCards(cards))
    }

    @Test
    fun acceptsABookCuriosityThatCitesAVerse() {
        val cards = listOf(
            obadiah(),
            bookCuriosity(
                prompt = "De onde fala Obadias?",
                answer = "Uma visão sobre Edom.",
                verseStart = 1,
                reference = "Ob 1:1",
            ),
        )
        assertEquals(emptyList<CardProblem>(), validateCards(cards))
    }

    @Test
    fun rejectsAReferenceFromAnotherChapter() {
        val card = obadiah(reference = "Ob 2:1")
        val messages = validateCards(listOf(card)).map { it.message }
        assertTrue(messages.any { it.contains("capítulo da referência") })
    }

    private fun obadiah(prompt: String = "De onde fala Obadias?", reference: String = "Ob 1:1"): Card = Card(
        id = "OBA.1.subject.1",
        bookNumber = 380,
        chapter = 1,
        verseStart = 1,
        kind = CardKind.SUBJECT,
        prompt = prompt,
        answer = "Uma visão sobre Edom.",
        reference = reference,
    )

    private fun bookCuriosity(
        prompt: String = "Quem escreveu Obadias?",
        answer: String = "Obadias.",
        verseStart: Int = 0,
        reference: String = "Obadias",
    ): Card = Card(
        id = "OBA.0.curiosity.1",
        bookNumber = 380,
        chapter = 0,
        verseStart = verseStart,
        kind = CardKind.CURIOSITY,
        prompt = prompt,
        answer = answer,
        reference = reference,
    )

    private fun loadBundledCards(): List<Card> {
        val dir = contentDir()
        val files = dir.listFiles { file -> file.extension == "json" }?.sortedBy { it.name }.orEmpty()
        assertTrue("Nenhum JSON em $dir", files.isNotEmpty())
        return files.flatMap { file ->
            json.decodeFromString(CardFile.serializer(), file.readText()).cards
        }
    }

    private fun contentDir(): File {
        var dir: File? = File(System.getProperty("user.dir"))
        while (dir != null) {
            val candidate = File(dir, "content/pt")
            if (candidate.isDirectory) {
                return candidate
            }
            dir = dir.parentFile
        }
        error("content/pt não encontrado a partir de ${System.getProperty("user.dir")}")
    }
}
