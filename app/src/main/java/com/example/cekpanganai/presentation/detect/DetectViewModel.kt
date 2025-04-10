package com.example.cekpanganai.presentation.detect

import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetectViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onNavigateBack() = appNavigator.tryNavigateBack()
    fun onNavigateToProcess() = appNavigator.tryNavigateTo(Destination.ProcessDetectScreen())
    fun onNavigateToResult() = appNavigator.tryNavigateTo(Destination.ResultScreen())
}