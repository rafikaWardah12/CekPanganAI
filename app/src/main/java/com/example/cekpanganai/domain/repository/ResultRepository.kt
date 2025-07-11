package com.example.cekpanganai.domain.repository

import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.network.response.FoodItemResponse
import com.example.cekpanganai.ui.utils.Message
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ResultRepository {
    //    fun getFoodById(id: String): Flow<Message<FoodItemEntity>>
    fun fetchSuggestionsFromApi(query: String): Flow<Message<List<FoodTable>>>

    fun getLocalFoodSuggestions(): Flow<List<String>>

    suspend fun saveFoodItemsToLocal(foodItems: List<FoodTable>)

    suspend fun deleteFoodbyId(id: String)
}