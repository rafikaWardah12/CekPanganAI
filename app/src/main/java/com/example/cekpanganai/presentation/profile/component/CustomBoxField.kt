package com.example.cekpanganai.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextTertiary
import com.example.cekpanganai.ui.utils.Padding

@Composable
internal fun CustomBoxField(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    value: String,
    isOutlined: Boolean = false
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Padding.medium))
            .then(
                if (isOutlined) Modifier.border(
                    width = 0.5.dp,
                    color = TextTertiary,
                    shape = RoundedCornerShape(Padding.medium)
                ) else Modifier
            )
            .background(color = if (isOutlined) Color.Transparent else color)
            .padding(Padding.large),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = if (isOutlined) GreenSecondary else TextPrimary
        )
    }
}