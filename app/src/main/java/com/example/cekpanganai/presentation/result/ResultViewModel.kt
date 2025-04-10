package com.example.cekpanganai.presentation.result

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateBack() = appNavigator.tryNavigateTo(Destination.DashboardScreen())
    fun onNavigateToHistory() = appNavigator.tryNavigateTo(Destination.HistoryScreen())
}