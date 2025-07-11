package com.example.cekpanganai.presentation.bmiScore.component

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.profile.ProfileViewModel
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary

@Composable
fun BMICard(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val result by profileViewModel.userState.collectAsState()
    val height = result.userById?.height
    val weight = result.userById?.weight
    val bmi = result.userById?.bmi

    val bmiCategory by profileViewModel.bmiState.collectAsState()

    Card(
        modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Outline),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier.padding(Padding.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            Text(
                text = stringResource(id = R.string.bmi),
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Row(
                modifier = modifier.padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                Text(
                    text = DecimalFormat("#.##").format(bmi ?: 0.0),
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                bmiCategory.category?.let {
                    Text(
                        text = stringResource(id = it.labelResId),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreenSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.extraSmall)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.height),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "$height Cm",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Log.d("bmi", "Berikut merupakan height: $height dan weight: $weight")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.weight),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "$weight Kg",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}