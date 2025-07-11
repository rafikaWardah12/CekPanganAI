package com.example.cekpanganai.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodsEntity(
    @PrimaryKey
    val id: String,
    val title: String?,
    val description: String?,
    val food: List<FoodItemEntity>?,
)

@Entity(tableName = "food_item")
data class FoodItemEntity(
    @PrimaryKey
    val id: String = "",
    val foodName: String?,
    val servings: List<ServingEntity?> = emptyList(),
    val foodImages: List<FoodImageEntity?> = emptyList(),
    val createdAt: String?,
    val updatedAt: String?,
)

@Entity
data class ServingEntity(
    @PrimaryKey
    val id: String,
    val metricServingAmount: Int?,
    val energy: Double?,
    val fat: Double?,
    val saturatedFat: Double?,
    val polyunsaturatedFat: Double?,
    val monounsaturatedFat: Double?,
    val cholesterol: Double?,
    val protein: Double?,
    val carbohydrate: Double?,
    val fiber: Double?,
    val sugar: Double?,
    val sodium: Double?,
    val potassium: Double?
)

data class FoodImageEntity(
    val id: Int = 0,
    val imageUrl: String?,
    val imageType: String?,
)

