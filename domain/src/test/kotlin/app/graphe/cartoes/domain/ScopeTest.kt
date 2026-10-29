package app.graphe.cartoes.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ScopeTest {
    private val genesis = card("GEN.1.subject.1", bookNumber = 10, chapter = 1)
    private val jonah = card("JON.2.subject.1", bookNumber = 390, chapter = 2)
    private val john = card("JHN.3.subject.1", bookNumber = 500, chapter = 3)

    @Test
    fun filtersByTestamentBookAndChapter() {
        val cards = listOf(genesis, jonah, john)
        assertEquals(listOf(genesis, jonah, john), cards.filter { it.matches(Scope.all) })
        assertEquals(listOf(genesis, jonah), cards.filter { it.matches(Scope(testament = Testament.OLD)) })
        assertEquals(listOf(john), cards.filter { it.matches(Scope(testament = Testament.NEW)) })
        assertEquals(listOf(genesis), cards.filter { it.matches(Scope(bookNumber = 10)) })
        assertEquals(emptyList<Card>(), cards.filter { it.matches(Scope(bookNumber = 10, chapter = 2)) })
        assertEquals(listOf(john), cards.filter { it.matches(Scope(bookNumber = 500, chapter = 3)) })
    }

    @Test
    fun labelsFollowTheNarrowestChoice() {
        assertEquals("Toda a Bíblia", scopeLabel(Scope.all))
        assertTrue(Scope.all.isAll())
        assertEquals("Antigo Testamento", scopeLabel(Scope(testament = Testament.OLD)))
        assertEquals("Novo Testamento", scopeLabel(Scope(testament = Testament.NEW)))
        assertEquals("Jonas", scopeLabel(Scope(bookNumber = 390)))
        assertEquals("João 3", scopeLabel(Scope(bookNumber = 500, chapter = 3)))
        assertEquals("História", scopeLabel(Scope.history))
        assertFalse(Scope(bookNumber = 500, chapter = 3).isAll())
        assertFalse(Scope.history.isAll())
    }

    @Test
    fun keepsBookFactsWithTheBookAndHistoryOnItsOwn() {
        val aboutGenesis = card("GEN.0.curiosity.1", bookNumber = 10, chapter = 0)
        val history = card("HIS.0.curiosity.1", bookNumber = HISTORY_BOOK_NUMBER, chapter = 0)
        val cards = listOf(genesis, aboutGenesis, history)
        assertEquals(listOf(genesis, aboutGenesis), cards.filter { it.matches(Scope.all) })
        assertEquals(listOf(genesis, aboutGenesis), cards.filter { it.matches(Scope(bookNumber = 10)) })
        assertEquals(listOf(genesis), cards.filter { it.matches(Scope(bookNumber = 10, chapter = 1)) })
        assertEquals(listOf(history), cards.filter { it.matches(Scope.history) })
        assertEquals(emptyList<Card>(), listOf(genesis).filter { it.matches(Scope.history) })
    }

    @Test
    fun storesHistoryApartFromABibleBook() {
        val stored = Scope.history.toStored()
        assertEquals(HISTORY_SCOPE_KEY, stored.testament)
        assertNull(stored.bookNumber)
        assertEquals(Scope.history, scopeFromStored(stored.testament, stored.bookNumber, stored.chapter))
        val book = Scope(bookNumber = 10, chapter = 2).toStored()
        assertEquals(Scope(bookNumber = 10, chapter = 2), scopeFromStored(book.testament, book.bookNumber, book.chapter))
    }
}
