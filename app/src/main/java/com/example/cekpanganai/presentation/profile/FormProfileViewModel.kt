package com.example.cekpanganai.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateToBMIScore() {
        appNavigator.tryNavigateTo(Destination.BMI_SCORE_ROUTE)
    }
}