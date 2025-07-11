package com.example.cekpanganai.presentation.result

import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.network.response.FoodItemResponse
import com.example.cekpanganai.ui.utils.Message
import org.mockito.internal.matchers.Null

data class Option(val value: String, val label: String)

data class FoodOriginalServing(val id: String, val serving: Int);

data class ResultState(
    val isAddFoodItem: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val food: List<FoodTable> = emptyList(),
    val listFood: List<FoodsEntity> = emptyList(),
    val foodData: List<FoodTable> = emptyList(),
    val foodServings: List<FoodOriginalServing> = emptyList(),
    val foodItem: List<FoodItemEntity> = emptyList(),
    val message: Message<List<FoodTable>> = Message.Loading,
    val totalNutrition: FoodTable? = null,

    val apiFoods: List<FoodItemResponse> = emptyList(),
    val localSuggestions: List<Option> = emptyList(),
)
