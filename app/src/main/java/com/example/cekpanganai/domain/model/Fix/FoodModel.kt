package com.example.cekpanganai.domain.model.Fix

data class FoodModel(
    val food: Food?
)

data class Food(
//    val food_attributes: FoodAttributes,
    val foodId: String?,
    val foodImages: FoodImages?,
    val foodName: String?,
//    val food_sub_categories: FoodSubCategorieModel,
//    val food_type: String,
//    val food_url: String,
    val servings: Servings?
)
