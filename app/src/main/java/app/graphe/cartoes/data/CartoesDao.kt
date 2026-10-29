package app.graphe.cartoes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartoesDao {
    @Query("SELECT * FROM cards")
    suspend fun allCards(): List<CardEntity>

    @Query("SELECT * FROM progress")
    suspend fun allProgress(): List<ProgressEntity>

    @Query("SELECT * FROM device_state WHERE id = 1")
    suspend fun deviceState(): DeviceStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Query("DELETE FROM cards WHERE id NOT LIKE 'user.%'")
    suspend fun deleteAssetCards()

    @Insert
    suspend fun insertReview(entry: ReviewLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: ProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertState(state: DeviceStateEntity)
}
