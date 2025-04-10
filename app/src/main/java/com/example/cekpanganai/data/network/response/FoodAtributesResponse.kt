package com.example.cekpanganai.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodAttributesDTO(

    @field:SerializedName("preferences")
    val preferences: PreferencesDTO? = null,

    @field:SerializedName("allergens")
    val allergens: AllergensDTO? = null
) : Parcelable


@Parcelize
data class AllergensDTO(

    @field:SerializedName("allergen")
    val allergen: List<AllergenItemDTO?>? = null
) : Parcelable

@Parcelize
data class PreferencesDTO(

    @field:SerializedName("preference")
    val preference: List<PreferenceItemDTO?>? = null
) : Parcelable

@Parcelize
data class AllergenItemDTO(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("value")
    val value: String? = null
) : Parcelable

@Parcelize
data class PreferenceItemDTO(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("value")
    val value: String? = null
) : Parcelable
