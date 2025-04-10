package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary


@Composable
fun CardEditPortion(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
        CustomDialog(onDismiss = { onDismiss() }, onConfirm = { /*TODO*/ }, header = {
            HeaderCard(title = stringResource(id = R.string.edit_name), icon = R.drawable.ic_edit)
        }, body = { BodyEditPortion() })
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BodyEditPortion(modifier: Modifier = Modifier, label: String = "") {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Gram", "Portion")

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
                    onClick = { selectedTabIndex = index }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (tabTitles[selectedTabIndex]) {
                "Gram" -> GramInputField()
                "Portion" -> PortionInputField()

            }
        }
    }
}


@Composable
fun GramInputField(modifier: Modifier = Modifier) {
    CustomInputText(label = "", placeholder = "100 gr", modifier = modifier)
}

@Composable
fun PortionInputField(modifier: Modifier = Modifier) {
    CustomInputText(label = "", placeholder = "1", modifier = modifier)
}

@Preview
@Composable
private fun tes() {
    CekPanganAITheme {
        CardEditPortion(onDismiss = {})
    }
}