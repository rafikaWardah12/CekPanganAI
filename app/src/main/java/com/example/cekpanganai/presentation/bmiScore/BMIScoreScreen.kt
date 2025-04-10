package com.example.cekpanganai.presentation.bmiScore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.bmiScore.component.BMICard
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary

@Composable
fun BMIScoreScreen(modifier: Modifier = Modifier, bmiScoreViewModel: BMIScoreViewModel = hiltViewModel()) {
    Surface() {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.large)
        ) {
            Text(
                text = stringResource(id = R.string.h2_bmi_score),
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
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


@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
//        BMICard()
        BMIScoreScreen()
    }
}