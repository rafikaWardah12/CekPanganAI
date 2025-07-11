package com.example.cekpanganai.data.network.dto

import com.example.cekpanganai.R
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.presentation.dashboard.component.dataItem2Dashboard
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.MicronutrienPrimary
import com.example.cekpanganai.ui.theme.PinkPrimary
import com.example.cekpanganai.ui.theme.SodiumPrimary
import com.example.cekpanganai.ui.theme.YellowPrimary

fun HistoryEntity.toDataItemDashboardMacro(): List<dataItem2Dashboard> {
    return listOf(
        dataItem2Dashboard(
            color = BluePrimary,
            icon = R.drawable.ic_carbo,
            label = R.string.carbo,
            score = "$total_karbohindrat kkal"
        ),
        dataItem2Dashboard(
            color = YellowPrimary,
            icon = R.drawable.ic_protein,
            label = R.string.protein,
            score = "$total_protein g"
        ),
        dataItem2Dashboard(
            color = PinkPrimary,
            icon = R.drawable.ic_fatty,
            label = R.string.fatty,
            score = "$total_lemak g"
        ),
        dataItem2Dashboard(
            color = GreenSecondary,
            icon = R.drawable.ic_fiber,
            label = R.string.fiber,
            score = "$total_serat g"
        ),
    )
}

fun HistoryEntity.toDataItemDashboardMicro(): List<DataItemNutrition> {
    return listOf(
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

