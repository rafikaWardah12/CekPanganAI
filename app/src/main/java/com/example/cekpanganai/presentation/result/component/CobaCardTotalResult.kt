package com.example.cekpanganai.presentation.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.presentation.component.ItemNutrition
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.utils.Spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTotalResult1(modifier: Modifier = Modifier, serving: String = "0") {
    var showFullInfo by remember { mutableStateOf(false) }
    val itemsShow = if (showFullInfo) FakeDataNutrition.result else FakeDataNutrition.result.take(3)

    val scaffoldState = rememberBottomSheetScaffoldState()
    var contentHeight by remember { mutableStateOf(0) }
    var sheetPeekHeight by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BottomSheetScaffold(
            sheetShadowElevation = 5.dp,
            sheetContainerColor = MaterialTheme.colorScheme.surface,
            scaffoldState = scaffoldState,
            sheetPeekHeight = sheetPeekHeight,
            sheetContent = {
                Column(modifier = Modifier.padding(Padding.medium)) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_total),
                            contentDescription = stringResource(
                                id = R.string.total_nutrition
                            ),
                            tint = GreenSecondary,
                            modifier = modifier.size(28.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.total) + ": " + serving + "gr",
                            modifier = Modifier.padding(horizontal = Padding.large),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(Spacing.large))
                    Column(
                        modifier = Modifier
                            .onGloballyPositioned { layoutCoordinates ->
                                contentHeight = layoutCoordinates.size.height
                                sheetPeekHeight = contentHeight.dp + 32.dp
                            }
                    ) {
                        itemsShow.forEach {
                            ItemNutrition(
                                color = it.color,
                                label = it.label,
                                score = it.score,
                                unit = it.unit
                            )
                        }
                    }
                    IconButton(
                        onClick = { showFullInfo = !showFullInfo },
                        modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = if (showFullInfo) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                                id = R.drawable.ic_arrow_up
                            ),
                            contentDescription = if (showFullInfo) stringResource(id = R.string.hide) else stringResource(
                                id = R.string.show,
                            ),
                            modifier = modifier.fillMaxWidth()
                        )
                    }
                }
            }
        ) {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTotalResult2(modifier: Modifier = Modifier, serving: String = "0") {
    var showFullInfo by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val itemsShow = if (showFullInfo) FakeDataNutrition.result else FakeDataNutrition.result.take(3)

    var sheetPeekHeight by remember { mutableStateOf(0.dp) }
    var sheetFullHeight by remember { mutableStateOf(0.dp) }

    // Cek apakah sheet di-drag ke atas atau ditutup
    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        showFullInfo = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShadowElevation = 5.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = sheetPeekHeight,
        sheetDragHandle = { BottomSheetDefaults.DragHandle() }, // Pegangan drag
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(Padding.medium)
                    .onGloballyPositioned { layoutCoordinates ->
                        val calculatedHeight = layoutCoordinates.size.height.dp
                        sheetFullHeight = calculatedHeight
                        sheetPeekHeight =
                            (calculatedHeight / 2).coerceAtLeast(120.dp) // Setengah tinggi atau min 120dp
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_total),
                        contentDescription = stringResource(id = R.string.total_nutrition),
                        tint = GreenSecondary,
                        modifier = modifier.size(28.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.total) + ": $serving gr",
                        modifier = Modifier.padding(horizontal = Padding.large),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.large))

                itemsShow.forEach {
                    ItemNutrition(
                        color = it.color,
                        label = it.label,
                        score = it.score,
                        unit = it.unit
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.medium))

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                scaffoldState.bottomSheetState.partialExpand()
                            } else {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = if (showFullInfo) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                            id = R.drawable.ic_arrow_up
                        ),
                        contentDescription = if (showFullInfo) stringResource(id = R.string.hide) else stringResource(
                            id = R.string.show
                        ),
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) {}
}


@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
//        CardTotalResult()
//        BottomSheetDemo()
        CardTotalResult2()
    }
}