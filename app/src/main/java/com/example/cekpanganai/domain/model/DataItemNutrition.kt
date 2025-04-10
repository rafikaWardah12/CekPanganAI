package com.example.cekpanganai.domain.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.android.parcel.Parcelize

//@Parcelize
//data class DataItemNutrition(
////    val label: Int,
////    val color: Int,
//    val type: NutritionType,
//    val score: String,
//    val unit: String,
//    val service: Int = 0
//) : Parcelable {
//    val label: String
//        get() = type.label.toString()
//
//    val color: Int
//        get() = type.color
//}


data class DataItemNutrition(
    val label: Int,
    val color: Color,
    val score: String,
    val unit: String,
    val service: Int = 0
)

