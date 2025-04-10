package com.example.cekpanganai.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.presentation.dashboard.component.CardMicronutrien
import com.example.cekpanganai.presentation.dashboard.component.Item1Dashboard
import com.example.cekpanganai.presentation.dashboard.component.Item2Dashboard
import com.example.cekpanganai.presentation.dashboard.component.TopDashboardNavbar
import com.example.cekpanganai.presentation.dashboard.component.dataItem2Dashboard
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.PinkPrimary
import com.example.cekpanganai.ui.theme.SodiumPrimary
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.YellowPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val items = listOf(
        dataItem2Dashboard(
            color = BluePrimary,
            icon = R.drawable.ic_carbo,
            label = R.string.carbo,
            score = "12 gr"
        ),
        dataItem2Dashboard(
            color = YellowPrimary,
            icon = R.drawable.ic_protein,
            label = R.string.protein,
            score = "12 gr"
        ),
        dataItem2Dashboard(
            color = PinkPrimary,
            icon = R.drawable.ic_fatty,
            label = R.string.fatty,
            score = "12 gr"
        ),
        dataItem2Dashboard(
            color = GreenSecondary,
            icon = R.drawable.ic_fiber,
            label = R.string.fiber,
            score = "12 gr"
        ),
    )
    val micronutrien = listOf(
        DataItemNutrition(
            color = ColestrolPrimary,
            label = R.string.colestrol,
            score = "12",
            unit = "mg"
        ),
        DataItemNutrition(
            color = GlukosaPrimary,
            label = R.string.glukosa,
            score = "12 gr",
            unit = "g"
        ),
        DataItemNutrition(
            color = SodiumPrimary,
            label = R.string.sodium,
            score = "12",
            unit = "mg"
        ),
        DataItemNutrition(
            color = CaliumPrimary,
            label = R.string.calium,
            score = "12",
            unit = "mg"
        )
    )

    Surface() {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.extraSmall)
        ) {
            item { TopDashboardNavbar(onProfile = { dashboardViewModel.onNavigateToProfile() }) }
//            item { Logo() }
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = GreenPrimary)) {
                            append("Fika")
                        }
                        withStyle(style = SpanStyle(color = TextPrimary)) {
                            append(stringResource(id = R.string.h4_dashboard))
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dashboard),
                            contentDescription = stringResource(id = R.string.dashboard),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.large)
                    ) {
                        Item1Dashboard(
                            label = R.string.score_calorie,
                            score = "24 kcal",
                            icon = R.drawable.ic_calorie
                        )
                        Item1Dashboard(
                            label = R.string.score_bmi,
                            score = "17.5",
                            icon = R.drawable.ic_bmi
                        )
                    }
                }
            }
            item {
                FlowRow(
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalArrangement = Arrangement.spacedBy(
                        Spacing.large
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEach { item ->
                        Item2Dashboard(
                            color = item.color,
                            icon = item.icon,
                            label = item.label,
                            score = item.score
                        )
                    }
                }
            }
            item {
                CardMicronutrien(
                    microItem = micronutrien,
                    modifier = modifier.padding(top = Padding.medium)
                )
            }
            item { Spacer(modifier = Modifier.padding(20.dp)) }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
//        TopNavbar()
        DashBoardScreen()
    }
}