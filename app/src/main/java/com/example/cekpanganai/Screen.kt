package com.example.cekpanganai

sealed class Screen(val route: String) {
    data object OnBoarding : Screen("onBoarding")
    data object BMIScore : Screen("bmiScore")
    data object FormProfile : Screen("formProfile")
    data object Dashboard : Screen("dashboard")
}