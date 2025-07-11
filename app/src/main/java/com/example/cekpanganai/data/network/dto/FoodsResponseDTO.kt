package com.example.cekpanganai.data.network.dto


import com.example.cekpanganai.R
import com.example.cekpanganai.data.database.entity.FoodImageEntity
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.ServingEntity
import com.example.cekpanganai.data.network.response.FoodImagesResponse
import com.example.cekpanganai.data.network.response.FoodItemResponse
import com.example.cekpanganai.data.network.response.FoodsResponse
import com.example.cekpanganai.data.network.response.ServingsItemResponse
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.CarbohydratePrimary
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.EneryPrimary
import com.example.cekpanganai.ui.theme.FatPrimary
import com.example.cekpanganai.ui.theme.FiberPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.MicronutrienPrimary
import com.example.cekpanganai.ui.theme.ProteinPrimary
import com.example.cekpanganai.ui.theme.SodiumPrimary

fun FoodsResponse.toFoodDTO() = FoodsEntity(
    food = foods.mapNotNull { it?.toFoodItem() },
    id = id,
    title = title,
    description = description,
)

fun FoodItemResponse.toFoodItem() = FoodItemEntity(
    id = id,
    foodImages = foodImages.map { it?.toFoodItemImage() },
    servings = servings.map { it?.toServingItem() },
    foodName = foodName,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString(),
)

fun FoodImagesResponse.toFoodItemImage() = FoodImageEntity(
    id = id,
    imageType = imageType,
    imageUrl = imageUrl
)

fun ServingsItemResponse.toServingItem() = ServingEntity(
    id = id,
    metricServingAmount = metricServingAmount,
    fat = fat,
    monounsaturatedFat = monounsaturatedFat,
    polyunsaturatedFat = polyunsaturatedFat,
    fiber = fiber,
    sugar = sugar,
    sodium = sodium,
    protein = protein,
    potassium = potassium,
    carbohydrate = carbohydrate,
    saturatedFat = saturatedFat,
    cholesterol = cholesterol,
    energy = energy
)

fun FoodTable.toDataItemResult(): List<DataItemNutrition> {
    return listOf(
        DataItemNutrition(
            color = EneryPrimary,
            label = R.string.calorie,
            score = energy.toString() ?: "",
            unit = "kkal",
        ),
        DataItemNutrition(
            color = CarbohydratePrimary,
            label = R.string.carbo,
            score = karbohindrat.toString() ?: "",
            unit = "g",
        ),
        DataItemNutrition(
            color = ProteinPrimary,
            label = R.string.protein,
            score = protein.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = FatPrimary,
            label = R.string.fatty,
            score = lemak.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = FiberPrimary,
            label = R.string.fiber,
            score = serat.toString() ?: "",
            unit = "g",
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.saturatedFat,
            score = lemak_jenuh.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.polyunsaturatedFat,
            score = lemak_tak_jenuh_ganda.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.monounsaturatedFat,
            score = lemak_tak_jenuh_tunggal.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.cholesterol,
            score = kolestrol.toString() ?: "",
            unit = "mg"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.glukosa,
            score = gula.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.sodium,
            score = sodium.toString() ?: "",
            unit = "mg"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.potassium,
            score = kalium.toString() ?: "",
            unit = "mg"
        ),
    )
}

fun FoodItemResponse.toFoodTable(): FoodTable {
    // Pilih salah satu serving data dari 'servings' jika ada (misalnya mengambil yang pertama)
    val servingItem = servings.firstOrNull()

    return FoodTable(
        id = this.id,
        food_name = this.foodName,
        metric_serving_amount = servingItem?.metricServingAmount,
        energy = servingItem?.energy,
        lemak = servingItem?.fat,
        lemak_jenuh = servingItem?.saturatedFat,
        lemak_tak_jenuh_ganda = servingItem?.polyunsaturatedFat,
        lemak_tak_jenuh_tunggal = servingItem?.monounsaturatedFat,
        kolestrol = servingItem?.cholesterol,
        protein = servingItem?.protein,
        karbohindrat = servingItem?.carbohydrate,
        serat = servingItem?.fiber,
        gula = servingItem?.sugar,
        sodium = servingItem?.sodium,
        kalium = servingItem?.potassium,
        created_at = this.createdAt,
        updated_at = this.updatedAt
    )
}