package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CanonTest {
    @Test
    fun pinsTheProtestantCanonToGrapheNumbers() {
        assertEquals(66, Canon.books.size)
        assertEquals(1189, Canon.chapterCount)
        assertEquals(39, Canon.books.count { it.testament == Testament.OLD })
        assertEquals(27, Canon.books.count { it.testament == Testament.NEW })
        assertEquals(66, Canon.books.map { it.bookNumber }.distinct().size)

        val genesis = Canon.byUsfm("GEN")!!
        assertEquals(10, genesis.bookNumber)
        assertEquals(50, genesis.chapters)
        assertEquals("Gn", genesis.shortName)
        assertEquals("Gênesis", genesis.longName)

        assertEquals(190, Canon.byUsfm("EST")!!.bookNumber)
        val job = Canon.byUsfm("JOB")!!
        assertEquals(220, job.bookNumber)
        assertEquals(42, job.chapters)

        val revelation = Canon.byUsfm("REV")!!
        assertEquals(730, revelation.bookNumber)
        assertEquals(22, revelation.chapters)
        assertEquals("Ap", revelation.shortName)
        assertEquals(Testament.NEW, revelation.testament)

        assertNull(Canon.byNumber(170))
        assertNull(Canon.byUsfm("TOB"))
    }
}
