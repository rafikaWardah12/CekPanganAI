package com.example.cekpanganai.presentation.component

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ContainerDisable
import com.example.cekpanganai.ui.theme.ContainerPrimary
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.OutlineFocused
import com.example.cekpanganai.ui.theme.TextDisable
import com.example.cekpanganai.ui.theme.TextError
import com.example.cekpanganai.ui.theme.TextPlaceHolder
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary

@Composable
fun CustomInputText(
    modifier: Modifier = Modifier,
    value: String = "",
    onValue: (String) -> Unit = {},
    placeholder: String = "",
    onClick: () -> Unit = {},
    isRequired: Boolean = false,
    isUnit: String = "",
    isObsecure: Boolean = false,
    label: String,
    isBox: Boolean = false,
    boxItem: List<BoxItem> = emptyList(),
    onBoxSelected: ((String) -> Unit)? = null,
    isNumber: Boolean = false,
) {
//    var password = remember { TextFieldState() }
    var showPassword by remember { mutableStateOf(false) }
    val corner = 10

    Column {
        Row {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.width(3.dp))
            if (isRequired) {
                Text(text = "*", color = TextError, style = MaterialTheme.typography.labelSmall)
            }
        }
        if (!isBox) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(start = Padding.small),
                value = value, onValueChange = onValue,
                colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OutlineFocused,
                    unfocusedBorderColor = Outline,
                    focusedLabelColor = OutlineFocused,
                    unfocusedContainerColor = ContainerPrimary,
                    focusedContainerColor = ContainerPrimary,
                    disabledContainerColor = ContainerDisable,
                    focusedTextColor = TextPrimary,
                    disabledTextColor = TextDisable,
                ),
                textStyle = MaterialTheme.typography.labelMedium,
                shape = RoundedCornerShape(corner),
                placeholder = {
                    Text(text = placeholder, color = TextPlaceHolder)
                },
                supportingText = {
                    Text(text = stringResource(id = R.string.input) + " " + label + " anda")
                },
                visualTransformation = if (isObsecure) {
                    when {
                        showPassword -> VisualTransformation.None
                        else -> RevealLastType()
                    }
                } else VisualTransformation.None,
                trailingIcon = {
                    if (isUnit.isNotEmpty()) {
                        Text(
                            text = isUnit,
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                        )
                    } else if (isObsecure) {
                        val icon =
                            if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (showPassword) "Show Password" else "Hide Password"

                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(imageVector = icon, contentDescription = description)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType =
                    if (isNumber) KeyboardType.Number else KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (isBox) {
            var isSelected by remember { mutableStateOf(-1) }
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.large),
                modifier = Modifier.padding(bottom = Spacing.large, start = Spacing.extraSmall)
            ) {
                boxItem.forEachIndexed { index, item ->
                    BoxItem(
                        corner = corner,
                        labelBox = item.labelBox,
                        isSelected = isSelected == index,
                        onClick = {
                            isSelected = index
                            onBoxSelected?.invoke(item.labelBox)
                        },
                        iconBox = item.iconBox ?: R.drawable.ic_add_reaction,
                    )
                }
            }
        }
    }
}


class RevealLastType : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val masked = text.text.mapIndexed { index, c ->
            if (index == text.text.length - 1) c else '•'
        }.joinToString("")
        return TransformedText(AnnotatedString(masked), OffsetMapping.Identity)
    }
}


@Preview(showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
        Column(Modifier.padding(40.dp)) {
            CustomInputText(
                value = "Fika",
                onValue = {},
                isRequired = true,
                isObsecure = false,
                label = "nama"
            )
            CustomInputText(
                value = "Fika",
                onValue = {},
                isRequired = true,
                isObsecure = true,
                label = "nama"
            )
            CustomInputText(
                value = "Fika",
                onValue = {},
                isRequired = true,
                isBox = true,
                boxItem = listOf(
                    BoxItem("Fika", R.drawable.boy),
                    BoxItem("FIda", R.drawable.girl)
                ),
                label = "jenis kelmain",
            )
        }
    }
}