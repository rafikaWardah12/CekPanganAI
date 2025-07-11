package com.example.cekpanganai

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cekpanganai.common.navigation_utils.AppNavHost
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.common.navigation_utils.NavigationIntent
import com.example.cekpanganai.common.navigation_utils.composable
import com.example.cekpanganai.presentation.test.TestingScreen
import com.example.cekpanganai.presentation.bmiScore.BMIScoreScreen
import com.example.cekpanganai.presentation.component.CustomBottomNavBar
import com.example.cekpanganai.presentation.dashboard.DashBoardScreen
import com.example.cekpanganai.presentation.detect.DetectScreen
import com.example.cekpanganai.presentation.detect.DetectViewModel
import com.example.cekpanganai.presentation.detect.EditImageScreen
import com.example.cekpanganai.presentation.detect.ProcessDetectScreen
import com.example.cekpanganai.presentation.historyDetail.HistoryDetailScreen
import com.example.cekpanganai.presentation.history.HistoryScreen
import com.example.cekpanganai.presentation.onBoarding.OnBoardingScreen
import com.example.cekpanganai.presentation.profile.FormProfileScreen
import com.example.cekpanganai.presentation.profile.ProfileScreen
import com.example.cekpanganai.presentation.result.ResultScreen
import com.example.cekpanganai.presentation.test.TestLineChartScreen
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.utils.Padding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    latestIntent: State<Intent?>,
    mainViewModel: MainViewModel = hiltViewModel(),
    detectViewModel: DetectViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val intent = latestIntent.value

    val handledIntent = remember(intent) { mutableStateOf(false) }

    LaunchedEffect(intent) {
        val navigateTo = intent?.getStringExtra("navigateTo")
        Log.d("IntentDebug", "Intent called with navigateTo=$navigateTo")

        if (!handledIntent.value) {
            mainViewModel.handleNavigationFromIntent(navigateTo)
            handledIntent.value = true
        }
    }

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )
    CekPanganAITheme {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                if (
                    currentRoute == Destination.DASHBOARD_ROUTE ||
                    currentRoute == Destination.PROFILE_ROUTE ||
                    currentRoute == Destination.HISTORY_ROUTE
                ) {
                    CustomBottomNavBar(
                        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
                        navController = navController
                    )
                }
            },
        ) {
            Column(
                Modifier.padding(
                    start = Padding.large,
                    top = Padding.medium,
                    end = Padding.large,
                    bottom = Padding.small
                )
            ) {
                AppNavHost(
                    navHostController = navController,
                    startDestination = mainViewModel.startDestination.value,
                    modifier = modifier.padding(it)
                ) {
                    composable(destination = Destination.OnBoardingScreen) {
                        OnBoardingScreen()
                    }
                    composable(destination = Destination.BMIScoreScreen) {
                        BMIScoreScreen()
                    }
                    composable(destination = Destination.FormProfileScreen) {
                        FormProfileScreen()
                    }
                    composable(destination = Destination.DashboardScreen) {
                        DashBoardScreen()
                    }
                    composable(destination = Destination.ProcessDetectScreen) {
                        ProcessDetectScreen(latestIntent = latestIntent.value)
                    }
                    composable(destination = Destination.ResultScreen) {
                        ResultScreen(latestIntent = latestIntent.value)
                    }
                    composable(destination = Destination.ProfileScreen) {
                        ProfileScreen()
                    }
                    composable(destination = Destination.HistoryScreen) {
                        HistoryScreen()
                    }
                    composable(destination = Destination.DetectScreen) {
                        DetectScreen()
                    }
                    composable(destination = Destination.EditImageScreen) {
                        EditImageScreen(
                            latestIntent = latestIntent.value,
                        )
                    }
                    composable(destination = Destination.HistoryDetailScreen) {
                        HistoryDetailScreen()
                    }
                    composable(destination = Destination.TestingScreen) {
                        TestingScreen()
                    }
                    composable(destination = Destination.TestLineChartScreen) {
                        TestLineChartScreen()
                    }
                }
            }
        }

    }
}

@Composable
fun NavigationEffects(
    modifier: Modifier = Modifier,
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    Log.d("Navigation", "Navigating to ${intent.route ?: "previous Screen"}")
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        Log.d(
                            "Navigation",
                            "Navigating to destination ${intent.route ?: "previous Screen"}"
                        )
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) {
                                inclusive = intent.inclusive
                            }
                        }
                    }
                }
            }
        }
    }
}