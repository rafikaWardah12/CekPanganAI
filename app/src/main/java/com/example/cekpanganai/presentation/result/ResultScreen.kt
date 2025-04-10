package com.example.cekpanganai.presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomCardResult
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.result.component.CardEditName
import com.example.cekpanganai.presentation.result.component.CardEditPortion
import com.example.cekpanganai.presentation.result.component.CardTotalResult
import com.example.cekpanganai.presentation.result.component.HeaderResult
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.BlueSecondary
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.SecondaryBackground
import com.example.cekpanganai.ui.theme.TextPlaceHolder
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.utils.Spacing

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    val items = FakeDataNutrition.result
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNestedScrollInteropConnection()
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditPortionId by remember { mutableStateOf<Int?>(null) }

    if (showAddDialog) {
        CardEditName(onDismiss = { showAddDialog = false })
    }
    if (showEditPortionId != null) {
        CardEditPortion(onDismiss = { showEditPortionId = null })
    }

    Surface() {
        Column {
            CustomTopBar(
                title = stringResource(id = R.string.result),
                navigateBack = { resultViewModel.onNavigateBack() },
                isFilled = true
            )
            Box(Modifier.nestedScroll(nestedScrollConnection)) {
                Column(Modifier.verticalScroll(scrollState)) {
                    Box(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.surface)
                            .graphicsLayer {
                                alpha =
                                    1f - (scrollState.value.toFloat() / scrollState.maxValue / 2)
                                translationY = 0.5f * scrollState.value
                            }
                    ) {
//                    AsyncImage(model = , contentDescription = )
                        Image(
                            painterResource(id = R.drawable.contoh),
                            contentDescription = "Gambar",
                            modifier
                                .fillMaxSize()
                                .background(color = PrimaryBackground),
                            alignment = Alignment.TopEnd,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Card(
                        modifier = modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.extraLarge),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    )
                    {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Padding.medium, vertical = Padding.large),
                            verticalArrangement = Arrangement.spacedBy(Spacing.small)
                        ) {
                            Column() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(Spacing.small),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Rawon",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_edit),
                                            contentDescription = stringResource(
                                                id = R.string.edit
                                            ),
                                            tint = BluePrimary
                                        )
                                    }
                                    Row {
                                        Text(
                                            text = "7 Februari 2025,   23:29",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                Text(text = "Rawon, kerupuk")
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = TextPlaceHolder)
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = modifier.fillMaxWidth()
                            ) {
                                CustomButton(
                                    text = stringResource(id = R.string.add),
                                    onClick = { showAddDialog = true },
                                    buttonColor = BlueSecondary,
                                    contentColor = BluePrimary,
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_add),
                                            contentDescription = stringResource(id = R.string.add),
                                            modifier = Modifier.size(18.dp),
                                        )
                                    },
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                            items.forEach {
                                CustomCardResult(
                                    header = {
                                        HeaderResult(
                                            label = stringResource(id = it.label),
                                            serving = it.service.toString(),
                                            onClick = { showEditPortionId = it.label }
                                        )
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(300.dp))
                }
                CardTotalResult(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .nestedScroll(nestedScrollConnection),
                    onSave = { resultViewModel.onNavigateToHistory() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun test() {
    CekPanganAITheme {
        ResultScreen()
    }
}