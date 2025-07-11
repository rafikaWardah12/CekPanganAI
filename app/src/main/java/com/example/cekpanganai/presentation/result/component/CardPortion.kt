package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.utils.Spacing


@Composable
fun CardEditPortion(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSave: (Int, Boolean) -> Unit,
    showDialog: Boolean,
    initialValue: String = "",
    originalServingPortionAmount: Int = 100
) {
    if (showDialog) {
        var input by rememberSaveable { mutableStateOf(initialValue) }
        var isPortion by rememberSaveable { mutableStateOf(false) }

        CustomDialog(
            onDismiss = {
                onDismiss()
            },
            onConfirm = {
                input.toIntOrNull()?.let { inputValue ->
                    onSave(inputValue, isPortion)
                    onDismiss()
                }
            },
            header = {
                HeaderCard(
                    title = stringResource(id = R.string.title_portion),
                    icon = R.drawable.ic_edit
                )
            },
            body = {
                BodyEditPortion(
                    input = input,
                    onInputChange = { input = it },
                    originalServingPortionAmount = originalServingPortionAmount,
                    onTabSelected = { selectedTab ->
                        isPortion = selectedTab == "Porsi"
                    }
                )
            })
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BodyEditPortion(
    modifier: Modifier = Modifier,
    label: String = "",
    input: String = "",
    onInputChange: (String) -> Unit,
    originalServingPortionAmount: Int,
    onTabSelected: (String) -> Unit,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Gram", "Porsi")


    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    selectedContentColor = TextPrimary,
                    unselectedContentColor = Outline,
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (index == selectedTabIndex) TextPrimary else TextSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    onClick = {
                        selectedTabIndex = index
                        onTabSelected(title)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (tabTitles[selectedTabIndex]) {
            "Gram" -> GramInputField(value = input, onValue = onInputChange)
            "Porsi" -> PortionInputField(
                value = input,
                onValue = onInputChange,
                originalServingPortionAmount = originalServingPortionAmount
            )

        }
    }
}


@Composable
fun GramInputField(modifier: Modifier = Modifier, value: String, onValue: (String) -> Unit) {
    CustomInputText(
        label = "",
        placeholder = "100 gr",
        modifier = modifier,
        value = value,
        onValue = onValue,
        isNumber = true
    )
}

@Composable
fun PortionInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValue: (String) -> Unit,
    originalServingPortionAmount: Int
) {
    Column {
        Text(
            text = "1 Porsi ($originalServingPortionAmount gr)",
            modifier = Modifier.padding(start = Spacing.medium),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic
            ),
        )
        CustomInputText(
            label = "",
            placeholder = "1",
            modifier = modifier,
            value = value,
            onValue = { portionInput ->
                onValue(portionInput)

                val portion = portionInput.toIntOrNull()
                if (portion != null) {
                    val grams = originalServingPortionAmount * portion
                }
            },
            isNumber = true,

            )
    }
}

@Preview
@Composable
private fun tes() {
    CekPanganAITheme {
//        CardEditPortion(onDismiss = {}, onSave = {10, true}, showDialog = true)
    }
}