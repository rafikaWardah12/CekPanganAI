package com.example.cekpanganai.data.network.response.coba

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodSubCategoriesDTO(

    @field:SerializedName("food_sub_category")
    val foodSubCategory: List<String?>? = null
) : Parcelable