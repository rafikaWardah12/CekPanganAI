package com.example.cekpanganai.data.database

import com.example.cekpanganai.data.database.entity.FoodImageEntity
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.ServingEntity

class FoodDataLocal {
    // ServingEntity - Data Servings (nutrisi per serving)
    val foodServing1 = ServingEntity(
        id = "1",
        metricServingAmount = 90,
        fat = 10.0,
        monounsaturatedFat = 4.0,
        polyunsaturatedFat = 3.0,
        fiber = 2.0,
        sugar = 1.5,
        sodium = 500.0,
        carbohydrate = 250.0,
        protein = 8.0,
        potassium = 200.0,
        saturatedFat = 2.0,
        cholesterol = 50.0,
        energy = 90.0
    )

    val foodServing2 = ServingEntity(
        id = "2",
        metricServingAmount = 100,
        fat = 15.0,
        monounsaturatedFat = 5.0,
        polyunsaturatedFat = 4.0,
        fiber = 3.0,
        sugar = 2.5,
        sodium = 600.0,
        carbohydrate = 300.0,
        protein = 10.0,
        potassium = 250.0,
        saturatedFat = 3.0,
        cholesterol = 60.0,
        energy = 120.0
    )
    val foodServing3 = ServingEntity(
        id = "3",
        metricServingAmount = 80,
        fat = 8.0,
        monounsaturatedFat = 3.0,
        polyunsaturatedFat = 2.5,
        fiber = 1.5,
        sugar = 1.0,
        sodium = 450.0,
        carbohydrate = 220.0,
        protein = 7.0,
        potassium = 180.0,
        saturatedFat = 1.5,
        cholesterol = 40.0,
        energy = 85.0
    )

    // FoodItemEntity - Data Item Makanan (misalnya Pizza)
    val foodItem1 = FoodItemEntity(
        id = "1",
        foodName = "Pizza",
        servings = listOf(foodServing1),
        foodImages = listOf(
            FoodImageEntity(
                id = 1,
                imageUrl = "https://example.com/pizza.jpg",
                imageType = "image/jpeg"
            )
        ),
        createdAt = "2025-04-28",
        updatedAt = "2025-04-28"
    )

    // FoodItemEntity - Data Item Makanan (misalnya Burger)
    val foodItem2 = FoodItemEntity(
        id = "2",
        foodName = "Burger",
        servings = listOf(foodServing2),
        foodImages = listOf(
            FoodImageEntity(
                id = 2,
                imageUrl = "https://example.com/burger.jpg",
                imageType = "image/jpeg"
            )
        ),
        createdAt = "2025-04-28",
        updatedAt = "2025-04-28"
    )

    // FoodItemEntity - Data Item Makanan (misalnya Salad)
    val foodItem3 = FoodItemEntity(
        id = "3",
        foodName = "Salad",
        servings = listOf(foodServing3),
        foodImages = listOf(
            FoodImageEntity(
                id = 3,
                imageUrl = "https://example.com/salad.jpg",
                imageType = "image/jpeg"
            )
        ),
        createdAt = "2025-04-28",
        updatedAt = "2025-04-28"
    )

    // FoodsEntity - Data Makanan (misalnya Fast Food)
    val foodEntity1 = FoodsEntity(
        id = "1",
        title = "Fast Food",
        description = "Delicious fast food options",
        food = listOf(foodItem1, foodItem2)
    )

    // FoodsEntity - Data Makanan (misalnya Healthy Food)
    val foodEntity2 = FoodsEntity(
        id = "2",
        title = "Healthy Food",
        description = "Fresh and healthy meals",
        food = listOf(foodItem3)
    )
}