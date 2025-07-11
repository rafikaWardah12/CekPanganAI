package com.example.cekpanganai.presentation.dashboard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.presentation.component.ItemNutrition
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.utils.DateRange

@Composable
fun CardMicronutrien(
    modifier: Modifier = Modifier,
    microItem: List<DataItemNutrition> = emptyList(),
    selectedRange: String
) {
    Card(
        modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Outline),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier
                .padding(Padding.medium)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.micronutrien) + " " + selectedRange,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            microItem.forEach { item ->
                ItemNutrition(
                    color = item.color,
                    label = item.label,
                    score = item.score,
                    unit = item.unit
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
//        CardMicronutrien(
//            microItem = listOf(
//                DataItemNutrition(
//                    label = R.string.save,
//                    color = BluePrimary,
//                    score = "40",
//                    unit = "mg"
//                )
//            ),
//        )
    }
}