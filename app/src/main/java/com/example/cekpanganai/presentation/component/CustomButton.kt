package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ErrorColor
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.TextButton

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: @Composable RowScope.() -> Unit = {},
    trailingIcon: @Composable RowScope.() -> Unit = {},
    isWide: Boolean = false,
    isRounded: Boolean = false,
    isOutline: Boolean = false,
    isWarning: Boolean = false,
    buttonColor: Color? = null,
    contentColor: Color? = null
) {
    Button(
        onClick = onClick,
        modifier = if (isWide) modifier.fillMaxWidth()
        else modifier,
        shape = if (isRounded) {
            RoundedCornerShape(20.dp)
        } else {
            RoundedCornerShape(12.dp)
        },
        border = if (isOutline) BorderStroke(1.dp, GreenPrimary) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
                ?: if (isOutline) Color.Transparent else if (isWarning) ErrorColor else GreenPrimary,
            contentColor = contentColor ?: if (isOutline) GreenPrimary else TextButton
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.extraSmall),
            modifier = Modifier.padding(Padding.extraSmall)
        ) {
            CompositionLocalProvider(value = LocalContentColor provides Color.Unspecified) {
                leadingIcon()
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            CompositionLocalProvider(value = LocalContentColor provides Color.Unspecified) {
                trailingIcon()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
        CustomButton(text = "Mulai", onClick = { /*TODO*/ }, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }, isWide = true, isOutline = true
        )
    }
}