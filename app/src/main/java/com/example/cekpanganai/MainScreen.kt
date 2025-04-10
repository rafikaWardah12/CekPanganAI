package com.example.cekpanganai

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cekpanganai.common.navigation_utils.AppNavHost
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.common.navigation_utils.NavigationIntent
import com.example.cekpanganai.common.navigation_utils.composable
import com.example.cekpanganai.presentation.bmiScore.BMIScoreScreen
import com.example.cekpanganai.presentation.component.CustomBottomNavBar
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.dashboard.DashBoardScreen
import com.example.cekpanganai.presentation.detect.DetectScreen
import com.example.cekpanganai.presentation.detect.ProcessDetectScreen
import com.example.cekpanganai.presentation.history.HistoryScreen
import com.example.cekpanganai.presentation.onBoarding.OnBoardingScreen
import com.example.cekpanganai.presentation.profile.FormProfileScreen
import com.example.cekpanganai.presentation.profile.ProfileScreen
import com.example.cekpanganai.presentation.result.ResultScreen
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.utils.Padding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
                    startDestination = Destination.OnBoardingScreen,
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
                        ProcessDetectScreen()
                    }
                    composable(destination = Destination.ResultScreen) {
                        ResultScreen()
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