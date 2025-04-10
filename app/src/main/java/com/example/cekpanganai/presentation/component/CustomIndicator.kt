package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.TextDisable

@Composable
fun CustomIndicator(modifier: Modifier = Modifier, isSelected: Int, totalIndicator: Int = 2) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
        repeat(totalIndicator) { index ->
            Box(
                modifier = modifier
                    .padding(horizontal = Padding.extraSmall)
                    .clip(RoundedCornerShape(30))
                    .width(if (index == isSelected) 20.dp else 16.dp)
                    .height(4.dp)
                    .background(color = if (index == isSelected) GreenPrimary else TextDisable)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
        CustomIndicator(isSelected = 1)
    }
}