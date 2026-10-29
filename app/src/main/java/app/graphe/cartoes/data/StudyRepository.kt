package app.graphe.cartoes.data

import app.graphe.cartoes.domain.Card
import app.graphe.cartoes.domain.CardProgress
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.StreakState
import java.time.Instant
import java.time.LocalDate

data class LoadedStudy(
    val cards: List<Card>,
    val progress: Map<String, CardProgress>,
    val streak: StreakState,
    val scope: Scope,
)

interface StudyRepository {
    suspend fun load(): LoadedStudy

    suspend fun setScope(scope: Scope)

    suspend fun recordGrade(cardId: String, grade: Grade, at: Instant, today: LocalDate)
}
