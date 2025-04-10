package com.example.cekpanganai.presentation.bmiScore

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BMIScoreViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateToDashboard() = appNavigator.tryNavigateTo(Destination.DashboardScreen())
}