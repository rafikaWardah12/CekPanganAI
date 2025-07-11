package com.example.cekpanganai.data.network.dto

import com.example.cekpanganai.R
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.data.database.entity.HistoryFoodsEntity
import com.example.cekpanganai.data.database.entity.HistoryWithFoods
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.ui.theme.CarbohydratePrimary
import com.example.cekpanganai.ui.theme.EneryPrimary
import com.example.cekpanganai.ui.theme.FatPrimary
import com.example.cekpanganai.ui.theme.FiberPrimary
import com.example.cekpanganai.ui.theme.MicronutrienPrimary
import com.example.cekpanganai.ui.theme.ProteinPrimary

fun HistoryEntity.toDataItemHistory(): List<DataItemNutrition> {
    return listOf(
        DataItemNutrition(
            color = EneryPrimary,
            label = R.string.calorie,
            score = total_energy.toString() ?: "",
            unit = "kkal",
        ),
        DataItemNutrition(
            color = CarbohydratePrimary,
            label = R.string.carbo,
            score = total_karbohindrat.toString() ?: "",
            unit = "g",
        ),
        DataItemNutrition(
            color = ProteinPrimary,
            label = R.string.protein,
            score = total_protein.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = FatPrimary,
            label = R.string.fatty,
            score = total_lemak.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = FiberPrimary,
            label = R.string.fiber,
            score = total_serat.toString() ?: "",
            unit = "g",
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.saturatedFat,
            score = total_lemak_jenuh.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.polyunsaturatedFat,
            score = total_lemak_tak_jenuh_ganda.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.monounsaturatedFat,
            score = total_lemak_tak_jenuh_tunggal.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.cholesterol,
            score = total_kolestrol.toString() ?: "",
            unit = "mg"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.glukosa,
            score = total_gula.toString() ?: "",
            unit = "g"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.sodium,
            score = total_sodium.toString() ?: "",
            unit = "mg"
        ),
        DataItemNutrition(
            color = MicronutrienPrimary,
            label = R.string.potassium,
            score = total_kalium.toString() ?: "",
            unit = "mg"
        ),
    )
}