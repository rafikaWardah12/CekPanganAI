package com.example.cekpanganai.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.presentation.component.CustomCardResult
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.component.ItemNutrition
import com.example.cekpanganai.presentation.history.component.HeaderHistory
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.utils.Spacing

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val items = FakeDataNutrition.result
    Surface() {
        LazyColumn(Modifier) {
            item {
                CustomTopBar(
                    title = stringResource(id = R.string.history),
                    isFilled = true,
                    navigateBack = { historyViewModel.onNavigateBack() })
            }
            item {
                items.forEach {
                    CustomCardResult(
                        header = {
                            HeaderHistory(
                                label = stringResource(id = it.label),
                                serving = it.service.toString(),
                                description = stringResource(id = it.label)
                            )
                        }
                    )
                    Spacer(modifier = modifier.height(Spacing.large))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        HistoryScreen()
    }
}