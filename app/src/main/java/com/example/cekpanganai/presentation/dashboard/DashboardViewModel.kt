package com.example.cekpanganai.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateToProfile() {
        appNavigator.tryNavigateTo(Destination.ProfileScreen())
    }
}