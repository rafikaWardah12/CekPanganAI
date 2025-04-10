package com.example.cekpanganai

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel

    fun onNavigateToDashboard() = appNavigator.tryNavigateTo(Destination.DashboardScreen())
    fun onNavigateToDetect() = appNavigator.tryNavigateTo(Destination.DetectScreen())
    fun onNavigateToHistory() = appNavigator.tryNavigateTo(Destination.HistoryScreen())

}