package app.graphe.cartoes.domain

import kotlin.random.Random

enum class Pool {
    LEARNING,
    UNSEEN,
    KNOWN,
}

object Deck {
    val share: Map<Pool, Int> = mapOf(
        Pool.LEARNING to 50,
        Pool.UNSEEN to 30,
        Pool.KNOWN to 20,
    )

    fun poolOf(progress: CardProgress?): Pool {
        if (progress == null || (progress.correctCount == 0 && progress.wrongCount == 0)) {
            return Pool.UNSEEN
        }
        if (progress.lastResult == Grade.WRONG || progress.correctStreak < 2) {
            return Pool.LEARNING
        }
        return Pool.KNOWN
    }

    /**
     * Draws one card at random from [cards]. The caller passes only the active
     * scope, so a book, a chapter, or the whole Bible each stay inside themselves.
     * Skips [excludeId] when another card is available. The draw is not a queue.
     */
    fun next(
        cards: List<Card>,
        excludeId: String?,
        random: Random,
    ): Card? {
        if (cards.isEmpty()) {
            return null
        }
        val candidates = if (cards.size > 1 && excludeId != null) {
            val without = cards.filter { it.id != excludeId }
            if (without.isEmpty()) cards else without
        } else {
            cards
        }
        return candidates[random.nextInt(candidates.size)]
    }
}
