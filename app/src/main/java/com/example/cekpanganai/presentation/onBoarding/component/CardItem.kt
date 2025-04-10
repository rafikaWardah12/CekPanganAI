package com.example.cekpanganai.presentation.onBoarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    headline: String = "",
    description: String = ""
) {
    Column(
        Modifier
            .background(PrimaryBackground),
        verticalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        if (headline.isNotEmpty()) {
            Text(
                text = headline,
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
        }
        if (description.isNotEmpty()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                color = TextSecondary
            )
        }
    }
}