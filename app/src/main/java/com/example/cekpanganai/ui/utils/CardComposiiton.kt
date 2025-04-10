package com.example.cekpanganai.ui.utils

import androidx.compose.material3.CardColors
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.theme.PrimaryBackground

data class Elevation(val card: Dp = 0.dp, val default: Dp = 0.dp)

val LocalElevations = compositionLocalOf { Elevation() }

data class Color(val color: Color = PrimaryBackground)

val LocalColor = compositionLocalOf { Color() }

