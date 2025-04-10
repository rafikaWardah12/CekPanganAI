package com.example.cekpanganai.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodImagesDTO(

    @field:SerializedName("food_image")
    val foodImage: List<FoodImageItemDTO?>? = null
) : Parcelable


@Parcelize
data class FoodImageItemDTO(

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("image_type")
    val imageType: String? = null
) : Parcelable