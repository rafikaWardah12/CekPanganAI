package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.Shapes
import com.example.cekpanganai.ui.theme.TextPlaceHolder
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing

//@Composable
//fun CardEditName(modifier: Modifier = Modifier, onDismiss: () -> Unit, onSave: (String) -> Unit = {}) {
//    CustomDialog(onDismiss = { onDismiss() }, onConfirm = { onSave(String) }, header = {
//        HeaderCard(title = stringResource(id = R.string.edit_name), icon = R.drawable.ic_edit)
//    }, body = { BodyEditName() })
//}
@Composable
fun CardEditName(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit = { _, _ -> },
    titleFood: String = "",
    descriptionFood: String = "",
) {
    var foodTitle by remember { mutableStateOf<String>(titleFood) }
    var foodDescription by remember { mutableStateOf<String>(descriptionFood) }

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = Shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = TextPlaceHolder, shape = Shapes.extraSmall),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                Modifier
                    .padding(Padding.medium)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onDismiss() }, modifier.align(Alignment.End)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(
                            id = R.string.close
                        ),
                    )
                }
                HeaderCard(
                    title = stringResource(id = R.string.edit_name),
                    icon = R.drawable.ic_edit
                )
                Spacer(modifier = Modifier.height(Spacing.large))
                Column(Modifier.fillMaxWidth()) {
                    CustomInputText(
                        label = stringResource(id = R.string.title_name),
                        placeholder = "Masukkan Judul Makanan",
                        value = foodTitle,
                        onValue = { str -> foodTitle = str }

                    )
                    CustomInputText(
                        label = stringResource(id = R.string.description_name),
                        placeholder = "Masukkan Deskripsi Makanan",
                        value = foodDescription,
                        onValue = { str -> foodDescription = str }
                    )
                }
                CustomButton(
                    text = stringResource(id = R.string.save),
                    onClick = { onSave(foodTitle, foodDescription) },
                    isWide = true,
                    isRounded = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = stringResource(id = R.string.save)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.large))
            }
        }
    }
}


@Composable
internal fun BodyEditName(modifier: Modifier = Modifier, label: String = "") {
    Column(Modifier.fillMaxWidth()) {
        CustomInputText(
            label = stringResource(id = R.string.title_name),
            placeholder = "Masukkan Judul Makanan"
        )
        CustomInputText(
            label = stringResource(id = R.string.description_name),
            placeholder = "Masukkan Deskripsi Makanan"
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