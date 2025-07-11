package com.example.cekpanganai.presentation.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.ui.theme.CekPanganAITheme

@Composable
fun CardEditBMI(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    height: Double,
    weight: Double,
    onSave: (Double, Double) -> Unit
) {
    var setHeight by remember { mutableDoubleStateOf(height) }
    var setWeight by remember { mutableDoubleStateOf(weight) }

    CustomDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onSave(setHeight, setWeight); onDismiss() },
        header = {
            HeaderCard(title = stringResource(id = R.string.edit_bmi), icon = R.drawable.ic_edit)
        },
        body = {
            BodyEditBMI(
                height = setHeight,
                weight = setWeight,
                onHeightChange = { setHeight = it },
                onWeightChange = { setWeight = it }
            )
        })
}

@Composable
internal fun BodyEditBMI(
    modifier: Modifier = Modifier,
    label: String = "",
    height: Double,
    weight: Double,
    onHeightChange: (Double) -> Unit,
    onWeightChange: (Double) -> Unit
) {

    Column(Modifier.fillMaxWidth()) {
        CustomInputText(
            label = stringResource(id = R.string.height) + " (cm)",
            placeholder = "$height cm",
            isNumber = true,
            value = height.toString(),
            onValue = { str -> str.toDoubleOrNull()?.let { onHeightChange(it) } }
        )
        CustomInputText(
            label = stringResource(id = R.string.weight) + " (kg)",
            placeholder = "$weight kg",
            value = weight.toString(),
            onValue = { str -> str.toDoubleOrNull()?.let { onWeightChange(it) } },
            isNumber = true
        )
    }
}

@Preview
@Composable
private fun tes() {
    CekPanganAITheme {
//        CardEditBMI(onDismiss = {}, weight = 45, height = 150, onSave = {})
    }
}