package com.example.cekpanganai.data.network.dto

import com.example.cekpanganai.data.network.response.FoodDTO
import com.example.cekpanganai.data.network.response.FoodImageItemDTO
import com.example.cekpanganai.data.network.response.FoodsResponseDTO
import com.example.cekpanganai.data.network.response.ServingItemDTO
import com.example.cekpanganai.data.network.response.ServingsDTO
import com.example.cekpanganai.domain.model.Fix.Food
import com.example.cekpanganai.domain.model.Fix.FoodImage
import com.example.cekpanganai.domain.model.Fix.FoodImages
import com.example.cekpanganai.domain.model.Fix.FoodModel
import com.example.cekpanganai.domain.model.Fix.Serving
import com.example.cekpanganai.domain.model.Fix.Servings

fun FoodsResponseDTO.toDTO() = FoodModel(
    food = food?.toFood()
)

fun FoodDTO.toFood() = Food(
    foodId = foodId,
    foodImages = toFoodImage(),
    servings = servings?.toServings(),
    foodName = foodName
)

fun FoodDTO.toFoodImage() = FoodImages(
    foodImage = foodImages?.foodImage?.map { it?.toFoodItemImage() }
)

fun FoodImageItemDTO.toFoodItemImage() = FoodImage(
    imageType = imageType,
    imageUrl = imageUrl
)

fun ServingsDTO.toServings() = serving?.map { it?.toServingItem() }?.let { Servings(it) }

fun ServingItemDTO.toServingItem() = Serving(
    servingId = servingId,
    metricServingAmount = metricServingAmount,
    metricServingUnit = metricServingUnit,
    servingDescription = servingDescription,
    servingUrl = servingUrl,
    iron = iron,
    fat = fat,
    monounsaturatedFat = monounsaturatedFat,
    polyunsaturatedFat = polyunsaturatedFat,
    fiber = fiber,
    sugar = sugar,
    sodium = sodium,
    calories = calories,
    vitaminA = vitaminA,
    vitaminC = vitaminC,
    calcium = calcium,
    protein = protein,
    potassium = potassium,
    carbohydrate = carbohydrate,
    saturatedFat = saturatedFat,
    numberOfUnits = numberOfUnits,
    cholesterol = cholesterol,
    measurementDescription = measurementDescription
)