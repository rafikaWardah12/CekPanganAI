package com.example.cekpanganai.presentation.result

import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodsEntity

sealed interface ResultEvent {
    data class SaveResult(val food: FoodsEntity) : ResultEvent
    object AddFoodItemDialog : ResultEvent
    object HideDialog : ResultEvent

    data class DeleteFood(val food: FoodsEntity) : ResultEvent
    data class DeleteFoodItem(val foodItemEntity: FoodItemEntity) : ResultEvent
    object ShowFood : ResultEvent
}