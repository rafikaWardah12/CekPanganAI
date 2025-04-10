package com.example.cekpanganai.presentation.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextPrimary


data class dataItem2Dashboard(
    val color: Color,
    val icon: Int,
    val label: Int,
    val score: String,
)

@Composable
fun Item2Dashboard(
    modifier: Modifier = Modifier,
    color: Color,
    icon: Int,
    label: Int,
    score: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = score,
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        Item2Dashboard(
            color = TextPrimary,
            icon = R.drawable.ic_carbo,
            label = R.string.name,
            score = "123"
        )
    }
}