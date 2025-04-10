package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenTertiary
import com.example.cekpanganai.ui.theme.Outline

data class BoxItem(
    val labelBox: String,
    val iconBox: Int
)

@Composable
fun BoxItem(
    modifier: Modifier = Modifier,
    corner: Int,
    labelBox: String,
    iconBox: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .background(color = if (isSelected) GreenTertiary else Color.Transparent)
                .border(
                    width = 1.dp,
                    color = if (isSelected) Color.Transparent else Outline,
                    shape = RoundedCornerShape(corner)
                )
                .clickable { onClick() }
                .padding(Padding.small)
        ) {
            Icon(
                painter = painterResource(iconBox),
                contentDescription = labelBox,
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = labelBox, style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
//        BoxItem(corner = 10, labelBox = "FIka", isSelected = null)
    }
}