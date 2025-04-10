package com.example.cekpanganai.domain.model

import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import com.example.cekpanganai.R
import com.example.cekpanganai.ui.theme.ColestrolPrimary

enum class NutritionType(val label: Int, val color: Int) {
    GLUCOSE(R.string.colestrol, color = ColestrolPrimary.toArgb()),
    HHHH(R.string.colestrol, color = ColestrolPrimary.toArgb());

    companion object {
        fun fromLabel(label: Int): NutritionType? {
            return values().find { it.label == label }
        }
    }
}