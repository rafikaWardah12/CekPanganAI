package com.example.cekpanganai.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.data.database.entity.HistoryFoodsEntity
import com.example.cekpanganai.data.database.entity.HistoryWithFoods
import com.example.cekpanganai.ui.utils.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Transaction
    @Query("SELECT * FROM history")
    fun getHistories(): Flow<List<HistoryEntity>>

    @Transaction
    @Query("SELECT * FROM history")
    fun getHistoriesWithFoods(): Flow<List<HistoryWithFoods>>

    @Transaction
    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    suspend fun getHistory(id: String): HistoryEntity

    @Transaction
    @Query("SELECT * FROM history_foods WHERE history_id = :historyId")
    suspend fun getHistoryFoods(historyId: String): List<HistoryFoodsEntity>

    @Transaction
    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    suspend fun getHistoryWithFoods(id: String): HistoryWithFoods?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoods(foods: List<FoodTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryFoodJunctions(junctions: List<HistoryFoodsEntity>)

    @Transaction
    suspend fun insertHistoryWithFoods(
        history: HistoryEntity,
        foods: List<FoodTable>
    ) {
        insertHistory(history)

        val junctions = foods.map { food ->
            HistoryFoodsEntity(
                food_id = food.id,
                history_id = history.id ?: "0",
                serving_size = food.metric_serving_amount ?: 0
            )
        }
        insertHistoryFoodJunctions(junctions)
    }

    @Query("SELECT * FROM history WHERE created_at BETWEEN :start AND :end")
    fun filterByDate(start: Long, end: Long): Flow<List<HistoryEntity>>
}