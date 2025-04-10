package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.ui.theme.CekPanganAITheme

@Composable
fun CardEditName(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    CustomDialog(onDismiss = { onDismiss() }, onConfirm = { /*TODO*/ }, header = {
        HeaderCard(title = stringResource(id = R.string.edit_name), icon = R.drawable.ic_edit)
    }, body = { BodyEditName() })
}

@Composable
internal fun BodyEditName(modifier: Modifier = Modifier, label: String = "") {
    Column(Modifier.fillMaxWidth()) {
        CustomInputText(label = stringResource(id = R.string.title_name), placeholder = "Rawon")
        CustomInputText(
            label = stringResource(id = R.string.description_name),
            placeholder = "Rawon, Tempe"
        )
    }
}

@Preview
@Composable
private fun tes() {
    CekPanganAITheme {
        CardEditName(onDismiss = {})
    }
}