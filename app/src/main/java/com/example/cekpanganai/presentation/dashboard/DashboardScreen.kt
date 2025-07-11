package com.example.cekpanganai.presentation.dashboard

import android.icu.text.DecimalFormat
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.ChartConstants
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.dto.toDataItemDashboardMacro
import com.example.cekpanganai.data.network.dto.toDataItemDashboardMicro
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.dashboard.component.CardMicronutrien
import com.example.cekpanganai.presentation.dashboard.component.ChartDashboard
import com.example.cekpanganai.presentation.dashboard.component.Item1Dashboard
import com.example.cekpanganai.presentation.dashboard.component.Item2Dashboard
import com.example.cekpanganai.presentation.dashboard.component.TopDashboardNavbar
import com.example.cekpanganai.presentation.dashboard.component.dataItem2Dashboard
import com.example.cekpanganai.presentation.profile.ProfileViewModel
import com.example.cekpanganai.presentation.result.ResultViewModel
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.CarbohydratePrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.EneryPrimary
import com.example.cekpanganai.ui.theme.FatPrimary
import com.example.cekpanganai.ui.theme.FiberPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.MonounsaturatedFatPrimary
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.PinkPrimary
import com.example.cekpanganai.ui.theme.PolyunsaturatedFatPrimary
import com.example.cekpanganai.ui.theme.ProteinPrimary
import com.example.cekpanganai.ui.theme.SaturatedFatPrimary
import com.example.cekpanganai.ui.theme.SodiumPrimary
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.theme.YellowPrimary
import com.example.cekpanganai.ui.utils.AppShadows
import com.example.cekpanganai.ui.utils.DateRange
import com.example.cekpanganai.ui.utils.filterDateConverter
import kotlinx.coroutines.delay
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    resultViewModel: ResultViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val coroutine = rememberCoroutineScope()
    val dashboardState by dashboardViewModel.dashboardState.collectAsState()
    val totalNutrition = dashboardState.totalNutrition

    val profile by profileViewModel.userState.collectAsState()
    val name = profile.userById?.name
    val bmiScore = profile.userById?.bmi

    val dataMacro = totalNutrition?.toDataItemDashboardMacro()
    val dataMicro = totalNutrition?.toDataItemDashboardMicro()

    var selectedRange by remember { mutableStateOf(DateRange.TODAY) }
    var selectedSliceDescription by remember { mutableStateOf<String?>(null) }

    val labelColorList = listOf(
        stringResource(id = R.string.carbo) to CarbohydratePrimary,
        stringResource(id = R.string.protein) to ProteinPrimary,
        stringResource(id = R.string.fatty) to FatPrimary,
    )

    @Composable
    fun DateRange.toLabelRes(): String = when (this) {
        DateRange.TODAY -> stringResource(id = R.string.current_day)
        DateRange.LAST_3_DAYS -> stringResource(id = R.string.last_3_days)
        DateRange.LAST_7_DAYS -> stringResource(id = R.string.last_week)
        DateRange.LAST_30_DAYS -> stringResource(id = R.string.last_month)
    }


    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = stringResource(id = R.string.carbo),
                value = totalNutrition?.total_karbohindrat?.toFloat() ?: 0.0f,
                color = CarbohydratePrimary,
                sliceDescription = { slicePercentage ->
                    "Deskripsi: Karbohidrat \nPersentase: $slicePercentage %"
                }
            ),
            PieChartData.Slice(
                label = stringResource(id = R.string.protein),
                value = totalNutrition?.total_protein?.toFloat() ?: 0.0f,
                color = ProteinPrimary,
                sliceDescription = { slicePercentage ->
                    "Deskripsi: Protein \nPersentase: $slicePercentage %"
                }
            ),
            PieChartData.Slice(
                label = stringResource(id = R.string.fatty),
                value = totalNutrition?.total_lemak?.toFloat() ?: 0.0f,
                color = FatPrimary,
                sliceDescription = { slicePercentage ->
                    "Deskripsi: Lemak \nPersentase: $slicePercentage %"
                }
            ),
        ),
        plotType = PlotType.Donut
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = false,
        activeSliceAlpha = 0.5f,
        animationDuration = 600,
        labelType = PieChartConfig.LabelType.PERCENTAGE,
        sliceLabelTextColor = TextPrimary,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
        sliceLabelTextSize = 10.sp,
        isSumVisible = true,
        sumUnit = "70",
        labelVisible = true,
        labelColorType = PieChartConfig.LabelColorType.SLICE_COLOR,
        labelColor = Color.Green,
        backgroundColor = Color.White,
        inActiveSliceAlpha = 23f,
        isEllipsizeEnabled = false,
        chartPadding = 20,
        sliceMinTextWidthToEllipsize = 10.dp,
        isClickOnSliceEnabled = true,
        accessibilityConfig = AccessibilityConfig(
            chartDescription = ChartConstants.CHART_DESCRIPTION,
            shouldHandleBackWhenTalkBackPopUpShown = false,
            descriptionTextSize = TextUnit(1f, type = TextUnitType.Sp),
            titleTextSize = TextUnit(100f, type = TextUnitType.Sp)
        ),
    )
    val pointsData = dashboardState.dailyEnergyPoints
    val steps = dashboardState.yAxisSteps
    val yAxisStep = dashboardState.yAxisStep

    val maxDay = pointsData.maxOfOrNull { it.x.toInt() } ?: 30

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .backgroundColor(Color.Transparent)
        .steps(maxDay)
        .labelData { i -> (i + 1).toString() }
        .axisLineColor(GreenPrimary)
        .axisLabelColor(GreenSecondary)
        .axisLabelFontSize(TextUnit(value = 14f, TextUnitType.Sp))
        .labelAndAxisLinePadding(10.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(10.dp)
        .labelData { i -> (i * yAxisStep).toString() }
        .axisLineColor(GreenPrimary)
        .axisLabelColor(GreenSecondary)
        .axisLabelFontSize(TextUnit(value = 14f, TextUnitType.Sp))
        .build()

    val density = LocalDensity.current
    val offsetPerDayPx = with(density) { 40.dp.toPx().toInt() }

    val scrollState = rememberScrollState()
    val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    val lineChartData = LineChartData(

        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = GreenPrimary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(color = GreenPrimary),
                    SelectionHighlightPoint(GreenPrimary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                GreenPrimary.copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(Outline),
        backgroundColor = Color.White
    )

    LaunchedEffect(selectedRange) {
        val (start, end) = filterDateConverter(selectedRange)
        Log.d("dashboard", "Start: $start, End: $end")
        dashboardViewModel.getFilterByDate(start, end)
        resultViewModel.seedInitialData()
        delay(400)
    }

    LaunchedEffect(Unit) {
        profileViewModel.getUser()
//        scrollState.scrollTo((today - 1) * chartOffsetPerDay)
        scrollState.scrollTo((today - 1) * offsetPerDayPx)
    }

    Surface() {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.extraSmall)
        ) {
            item {
                TopDashboardNavbar(
                    onProfile = { dashboardViewModel.onNavigateToProfile() },
                    selectedDateRange = selectedRange,
                    onSelectedDateRange = { selectedRange = it },
                    name = "$name",
                )
            }
//            item { Logo() }
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = GreenPrimary)) {
                            append("$name")
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
                            score = totalNutrition?.total_energy.toString(),
                            icon = R.drawable.ic_calorie
                        )
                        Item1Dashboard(
                            label = R.string.score_bmi,
                            score = DecimalFormat("#.##").format(bmiScore ?: 0.0),
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
                    dataMacro?.forEach { item ->
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
                    microItem = dataMicro ?: emptyList(),
                    modifier = modifier.padding(top = Padding.medium),
                    selectedRange = selectedRange.toLabelRes()
                )
            }
            item { Spacer(modifier = Modifier.padding(Spacing.small)) }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = GreenPrimary)) {
                                append("Grafik")
                            }
                            withStyle(style = SpanStyle(color = TextPrimary)) {
                                append(" ")
                                append(
                                    selectedRange.toLabelRes()
                                )
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .width(150.dp),
                        color = Outline,
                        thickness = 1.dp
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(Spacing.large)) }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        PieChart(
                            modifier = modifier
                                .width(200.dp)
                                .fillMaxWidth(),
                            pieChartData = pieChartData,
                            pieChartConfig = pieChartConfig,
                            onSliceClick = { slice ->
                                val percentage =
                                    (slice.value / pieChartData.slices.sumOf { it.value.toDouble() } * 100).toInt()
                                val description = slice.sliceDescription(percentage)

                                selectedSliceDescription =
                                    if (selectedSliceDescription == description) {
                                        null
                                    } else {
                                        description
                                    }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(Spacing.large))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Column {
                            labelColorList.forEach { (label, color) ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(Spacing.extraSmall),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = modifier
                                            .background(color)
                                            .height(12.dp)
                                            .width(12.dp)
                                    )
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(Padding.medium))
                        selectedSliceDescription?.let { description ->
                            Box(
                                modifier = modifier
                                    .border(
                                        width = 1.dp, brush = Brush.horizontalGradient(
                                            0.0f to EneryPrimary,
                                            0.3f to GreenSecondary,
                                            1.0f to GreenPrimary,
                                            startX = 0.0f,
                                            endX = 100.0f
                                        ), shape = RoundedCornerShape(12.dp)
                                    )
                            )
                            {
                                Text(
                                    text = description,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(Spacing.small)) }
            item {
                if (pointsData.isNotEmpty()) {
                    Column {
                        LineChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .width((pointsData.size * 40).dp), lineChartData = lineChartData
                        )
                    }
                    Box(
                        modifier = modifier
                            .border(
                                width = 1.dp, brush = Brush.horizontalGradient(
                                    0.0f to EneryPrimary,
                                    0.3f to GreenSecondary,
                                    1.0f to GreenPrimary,
                                    startX = 0.0f,
                                    endX = 100.0f
                                ), shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Column(
                            modifier.padding(
                                start = 12.dp,
                                top = 6.dp,
                                bottom = 6.dp,
                                end = 30.dp
                            )
                        ) {
                            Text(
                                text = "Keterangan",
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                            )
                            Text(
                                text = "x: Hari",
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                            )
                            Text(
                                text = "y: Jumlah Kalori",
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(Padding.small)) }
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