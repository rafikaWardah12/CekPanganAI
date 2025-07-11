package com.example.cekpanganai

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.data.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val userDao: UserDao,
) : ViewModel() {
    val startDestination = mutableStateOf<Destination>(Destination.OnBoardingScreen)

    init {
        viewModelScope.launch {
            val user = userDao.getUser()
            startDestination.value = if (user?.name != null) {
                Destination.DashboardScreen
            } else {
                Destination.OnBoardingScreen
            }
        }
    }

    val navigationChannel = appNavigator.navigationChannel

    fun onNavigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun onNavigateToDashboard() {
        appNavigator.tryNavigateTo(Destination.DashboardScreen())
    }

    fun onNavigateToDetect() = appNavigator.tryNavigateTo(Destination.DetectScreen())
    fun onNavigateToHistory() = appNavigator.tryNavigateTo(Destination.HistoryScreen())
    fun onNavigateToResult() = appNavigator.tryNavigateTo(Destination.ResultScreen())

    fun onNavigateToEditImage() = appNavigator.tryNavigateTo(Destination.EditImageScreen())

    fun onNavigateToProcessDetect() = appNavigator.tryNavigateTo(Destination.ProcessDetectScreen())

    fun handleNavigationFromIntent(navigateTo: String?) {
        Log.d("HandleIntent", "handleNavigationFromIntent called with: $navigateTo")
        when (navigateTo) {
            "back" -> {
                Log.d("handleIntent", "Navigation to Back Screen Now")
                onNavigateBack()
            }

            "dashboard" -> {
                onNavigateToDashboard()
            }

            "result" -> onNavigateToResult()
            "editImage" -> onNavigateToEditImage()
            "processDetect" -> onNavigateToProcessDetect()
        }
    }
}