package app.graphe.cartoes.data

import android.content.res.AssetManager
import androidx.room.withTransaction
import app.graphe.cartoes.domain.CardFile
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.applyGrade
import app.graphe.cartoes.domain.recordStudyDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate

class RoomStudyRepository(
    private val database: CartoesDatabase,
    private val assets: AssetManager,
) : StudyRepository {
    private val dao = database.dao()
    private val json = Json { ignoreUnknownKeys = false }

    override suspend fun load(): LoadedStudy {
        ensureLoaded()
        val state = dao.deviceState() ?: DeviceStateEntity()
        val cards = dao.allCards().map { it.toDomain() }
        val progress = dao.allProgress().associate { it.cardId to it.toDomain() }
        return LoadedStudy(
            cards = cards,
            progress = progress,
            streak = state.toStreak(),
            scope = state.toScope(),
        )
    }

    override suspend fun setScope(scope: Scope) {
        val state = dao.deviceState() ?: DeviceStateEntity()
        dao.upsertState(state.withScope(scope))
    }

    override suspend fun recordGrade(cardId: String, grade: Grade, at: Instant, today: LocalDate) {
        database.withTransaction {
            val existing = dao.allProgress().firstOrNull { it.cardId == cardId }?.toDomain()
            val updated = applyGrade(existing, cardId, grade, at.toEpochMilli())
            dao.upsertProgress(updated.toEntity())
            dao.insertReview(
                ReviewLogEntity(
                    cardId = cardId,
                    result = grade.toStored(),
                    reviewedAtEpochMillis = at.toEpochMilli(),
                ),
            )
            val state = dao.deviceState() ?: DeviceStateEntity()
            dao.upsertState(state.withStreak(recordStudyDay(state.toStreak(), today)))
        }
    }

    private suspend fun ensureLoaded() {
        val version = withContext(Dispatchers.IO) { readVersion() }
        val state = dao.deviceState()
        if (state != null && state.loadedAssetVersion >= version) {
            return
        }
        val cards = withContext(Dispatchers.IO) { readCards() }
        database.withTransaction {
            dao.deleteAssetCards()
            dao.insertCards(cards.map { it.toEntity() })
            val current = dao.deviceState() ?: DeviceStateEntity()
            dao.upsertState(current.copy(loadedAssetVersion = version))
        }
    }

    private fun readVersion(): Int {
        return assets.open("cards/version.txt").bufferedReader().use { it.readText().trim().toInt() }
    }

    private fun readCards() = assets.list("cards/pt").orEmpty()
        .filter { it.endsWith(".json") }
        .sorted()
        .flatMap { name ->
            assets.open("cards/pt/$name").bufferedReader().use { reader ->
                json.decodeFromString(CardFile.serializer(), reader.readText()).cards
            }
        }
}
