package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.ui.theme.CekPanganAITheme

@Composable
fun CardDelete(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = null,
            text = {
                Text(
                    text = stringResource(id = R.string.questions_delete),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            containerColor = Color.White,
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        text = stringResource(id = R.string.delete),
                        onClick = { onDelete() },
                        isWarning = true,
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    CustomButton(
                        text = stringResource(id = R.string.dont_save),
                        onClick = { onDismiss() },
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }
            },
            dismissButton = {}
        )
    }
}

@Preview
@Composable
private fun test() {
    CekPanganAITheme {
        CardDelete(onDelete = {}, showDialog = true, onDismiss = {})
    }
}
