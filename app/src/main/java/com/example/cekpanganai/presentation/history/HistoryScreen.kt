package com.example.cekpanganai.presentation.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.dto.toDataItemHistory
import com.example.cekpanganai.data.network.dto.toDataItemResult
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.presentation.component.CustomCardResult
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.component.ItemNutrition
import com.example.cekpanganai.presentation.history.component.HeaderHistory
import com.example.cekpanganai.presentation.result.ResultViewModel
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.TextBlue
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.utils.decodeBitmapFromUriString
import com.example.cekpanganai.ui.utils.formatDate
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {

    val historyState by historyViewModel.historyState.collectAsState()

    val items = historyState.histories

    LaunchedEffect(Unit) {
        historyViewModel.getHistories()
    }
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
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(text = "", modifier = modifier.fillMaxWidth(0.5f))
                                Text(
                                    text = stringResource(id = R.string.show_all),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GreenSecondary,
                                    modifier = modifier
                                        .clickable { historyViewModel.onNavigateToDetailHistory(id = it.id) }
                                        .padding(vertical = Spacing.small)
                                )
                            }
                            HeaderHistory(
                                label = it.title_food,
                                serving = it.total_amount_serving.toString(),
                                description = it.description_food ?: "null",
                                timestamp = formatDate(it.updated_at)
                            )
                        },
                        itemsShow = it.toDataItemHistory()
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