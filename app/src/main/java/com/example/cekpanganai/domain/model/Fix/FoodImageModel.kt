package com.example.cekpanganai.domain.model.Fix

data class FoodImages(
    val foodImage: List<FoodImage?>?
)

data class FoodImage(
    val imageType: String?,
    val imageUrl: String?
)