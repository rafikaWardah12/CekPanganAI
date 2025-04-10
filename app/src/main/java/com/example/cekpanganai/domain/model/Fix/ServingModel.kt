package com.example.cekpanganai.domain.model.Fix

data class Servings(
    val serving: List<Serving?>
)

data class Serving(
    val calcium: String?,
    val calories: String?,
    val carbohydrate: String?,
    val cholesterol: String?,
    val fat: String?,
    val fiber: String?,
    val iron: String?,
    val measurementDescription: String?,
    val metricServingAmount: String?,
    val metricServingUnit: String?,
    val monounsaturatedFat: String?,
    val numberOfUnits: String?,
    val polyunsaturatedFat: String?,
    val potassium: String?,
    val protein: String?,
    val saturatedFat: String?,
    val servingDescription: String?,
    val servingId: String?,
    val servingUrl: String?,
    val sodium: String?,
    val sugar: String?,
    val vitaminA: String?,
    val vitaminC: String?
)