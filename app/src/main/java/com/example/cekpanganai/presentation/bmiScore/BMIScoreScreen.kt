package com.example.cekpanganai.presentation.bmiScore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.bmiScore.component.BMICard
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.profile.ProfileViewModel
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary

@Composable
fun BMIScoreScreen(
    modifier: Modifier = Modifier,
    bmiScoreViewModel: BMIScoreViewModel = hiltViewModel(),
) {
    Surface() {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { bmiScoreViewModel.onNavigateBack() },
                    tint = TextPrimary
                )
                Text(
                    text = stringResource(id = R.string.h2_bmi_score),
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(Padding.extraLarge))
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.large)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bmi_score),
                    contentDescription = stringResource(
                        id = R.string.h2_bmi_score
                    ),
                    modifier = modifier.fillMaxWidth(),
                    alignment = Alignment.Center
                )
                BMICard()
                Text(
                    text = stringResource(id = R.string.h4_bmi_score),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(50.dp))
                CustomButton(
                    text = stringResource(id = R.string.next),
                    onClick = { bmiScoreViewModel.onNavigateToDashboard() },
                    isWide = true,
                    isRounded = true
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
//        BMICard()
        BMIScoreScreen()
    }
}