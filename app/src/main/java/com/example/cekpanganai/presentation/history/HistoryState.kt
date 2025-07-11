package com.example.cekpanganai.presentation.history

import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.data.database.entity.HistoryWithFoods
import com.example.cekpanganai.data.network.response.FoodItemResponse
import com.example.cekpanganai.presentation.result.Option
import com.example.cekpanganai.ui.utils.Message

data class HistoryState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val histories: List<HistoryEntity> = emptyList(),
    val totalNutritionHistory: HistoryEntity? = null
)