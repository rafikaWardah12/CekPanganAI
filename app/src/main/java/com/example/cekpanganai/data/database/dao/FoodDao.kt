package com.example.cekpanganai.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodsEntity)

    @Delete
    suspend fun deleteFoods(food: FoodsEntity)

    ///////Get All Food Item
    @Query("SELECT * FROM food_table ORDER BY food_name")
    fun getAllFoodItem(): Flow<List<FoodTable>>

    @Query("SELECT * FROM food_table WHERE id = :id LIMIT 1")
    suspend fun getFoodById(id: String): FoodTable?

    @Query("DELETE FROM food_table WHERE id = :id")
    suspend fun deleteFoodById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodTable(food: FoodTable)

    @Query("SELECT * FROM food_table WHERE id IN(:ids) ")
    fun getFoodData(ids: Array<String>): Flow<List<FoodTable>>

    @Update
    suspend fun update(food: FoodTable)
}