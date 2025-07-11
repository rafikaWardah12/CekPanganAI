package com.example.cekpanganai.presentation.profile

import androidx.annotation.StringRes
import com.example.cekpanganai.R
import com.example.cekpanganai.data.database.entity.UserEntity
import com.example.cekpanganai.ui.utils.Message

data class ProfileState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: Message<UserEntity> = Message.Loading,
    val userById: UserEntity? = null
)

data class BMIState(
    val bmi: Double = 19.9,
    val category: bmiCategory? = bmiCategory.NORMAL
)

enum class bmiCategory(@StringRes val labelResId: Int) {
    UNDERWEIGHT(R.string.underweight),
    NORMAL(R.string.normal),
    OVERWEIGHT(R.string.overweight),
    OBESE(R.string.obese);

    companion object {
        fun fromBMI(bmi: Double): bmiCategory = when {
            bmi < 18.5 -> UNDERWEIGHT
            bmi < 25 -> NORMAL
            bmi < 30 -> OVERWEIGHT
            else -> OBESE
        }
    }
}