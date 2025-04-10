package com.example.cekpanganai.presentation.profile.component

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
fun CardEditBMI(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    CustomDialog(onDismiss = { onDismiss() }, onConfirm = { /*TODO*/ }, header = {
        HeaderCard(title = stringResource(id = R.string.edit_bmi), icon = R.drawable.ic_edit)
    }, body = { BodyEditBMI() })
}

@Composable
internal fun BodyEditBMI(modifier: Modifier = Modifier, label: String = "") {
    Column(Modifier.fillMaxWidth()) {
        CustomInputText(label = stringResource(id = R.string.height), placeholder = "145 cm")
        CustomInputText(
            label = stringResource(id = R.string.weight),
            placeholder = "45 kg"
        )
    }
}

@Preview
@Composable
private fun tes() {
    CekPanganAITheme {
        CardEditBMI(onDismiss = {})
    }
}