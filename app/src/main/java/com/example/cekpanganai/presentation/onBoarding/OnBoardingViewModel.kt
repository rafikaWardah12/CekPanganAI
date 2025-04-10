package com.example.cekpanganai.presentation.onBoarding

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateToFormProfile() {
        appNavigator.tryNavigateTo(Destination.FORM_PROFILE_ROUTE)
    }
}