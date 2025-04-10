package com.example.cekpanganai.data.network.response

import android.os.Parcelable
import com.example.cekpanganai.domain.model.Fix.Food
import com.example.cekpanganai.domain.model.Fix.FoodImage
import com.example.cekpanganai.domain.model.Fix.FoodImages
import com.example.cekpanganai.domain.model.Fix.FoodModel
import com.example.cekpanganai.domain.model.Fix.Serving
import com.example.cekpanganai.domain.model.Fix.Servings
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodsResponseDTO(

    @field:SerializedName("food")
    val food: FoodDTO? = null
) : Parcelable

@Parcelize
data class FoodDTO(
    @field:SerializedName("food_sub_categories")
    val foodSubCategories: FoodSubCategoriesDTO? = null,

    @field:SerializedName("food_name")
    val foodName: String? = null,

    @field:SerializedName("servings")
    val servings: ServingsDTO? = null,

    @field:SerializedName("food_images")
    val foodImages: FoodImagesDTO? = null,

    @field:SerializedName("food_type")
    val foodType: String? = null,

    @field:SerializedName("food_id")
    val foodId: String? = null,

    @field:SerializedName("food_url")
    val foodUrl: String? = null,

    @field:SerializedName("food_attributes")
    val foodAttributes: FoodAttributesDTO? = null
) : Parcelable


