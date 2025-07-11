package com.example.cekpanganai.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.Date

@Parcelize
data class FoodsResponse(

    @field:SerializedName("responseObject")
    val id: String,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    val foods: List<FoodItemResponse?> = emptyList(),

    val title: String? = null,

    val description: String? = null,
) : Parcelable

@Parcelize
data class FoodItemResponse(

    @field:SerializedName("food_name")
    val foodName: String? = null,

    @field:SerializedName("servings")
    val servings: List<ServingsItemResponse?> = emptyList(),

    @field:SerializedName("updated_At")
    val updatedAt: Date? = null,

    @field:SerializedName("created_At")
    val createdAt: Date? = null,

    @field:SerializedName("food_images")
    val foodImages: List<FoodImagesResponse?> = emptyList(),

    @field:SerializedName("id")
    val id: String
) : Parcelable

@Parcelize
data class ServingsItemResponse(

    @field:SerializedName("fiber")
    val fiber: Double? = null,

    @field:SerializedName("potassium")
    val potassium: Double? = null,

    @field:SerializedName("saturated_fat")
    val saturatedFat: Double? = null,

    @field:SerializedName("carbohydrate")
    val carbohydrate: Double? = null,

    @field:SerializedName("metric_serving_amount")
    val metricServingAmount: Int? = null,

    @field:SerializedName("sodium")
    val sodium: Double? = null,

    @field:SerializedName("monounsaturated_fat")
    val monounsaturatedFat: Double? = null,

    @field:SerializedName("polyunsaturated_fat")
    val polyunsaturatedFat: Double? = null,

    @field:SerializedName("protein")
    val protein: Double? = null,

    @field:SerializedName("fat")
    val fat: Double? = null,

    @field:SerializedName("cholesterol")
    val cholesterol: Double? = null,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("sugar")
    val sugar: Double? = null,

    @field:SerializedName("energy")
    val energy: Double? = null
) : Parcelable

@Parcelize
data class FoodImagesResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("image_type")
    val imageType: String? = null
) : Parcelable