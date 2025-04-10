package com.example.cekpanganai.presentation.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomIndicator
import com.example.cekpanganai.presentation.component.Logo
import com.example.cekpanganai.presentation.onBoarding.component.CardItem
import com.example.cekpanganai.presentation.onBoarding.component.ImageItem
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, onBoardingViewModel: OnBoardingViewModel = hiltViewModel()) {
    val totalPage = 2
    val pagerState = rememberPagerState(0, 0F) { totalPage }
    Surface() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> ImageItem(resource = R.drawable.on_boarding_1)
                    1 -> ImageItem(resource = R.drawable.on_boarding_2)
                }
            }
            Spacer(modifier = modifier.height(20.dp))
            CustomIndicator(isSelected = pagerState.currentPage, totalIndicator = totalPage)
            Spacer(modifier = modifier.height(40.dp))
            Column(verticalArrangement = Arrangement.Bottom) {
                when (pagerState.currentPage) {
                    0 -> CardItem(
                        headline = stringResource(id = R.string.h2_onboarding_1),
                        description = stringResource(id = R.string.h3_onboarding_1)
                    )

                    1 -> CardItem(
                        headline = stringResource(id = R.string.h2_onboarding_2),
                        description = stringResource(id = R.string.h3_onboarding_2)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (pagerState.currentPage == pagerState.pageCount - 1) {
                CustomButton(
                    text = stringResource(id = R.string.start),
                    leadingIcon = {},
                    onClick = { onBoardingViewModel.onNavigateToFormProfile() },
                    isWide = true,
                    isRounded = true
                )
            }
        }
    }
}

@Preview
@Composable
private fun TestOnBoardingScreen() {
    CekPanganAITheme {
        OnBoardingScreen()
    }
}