package com.example.cekpanganai.presentation.dashboard.component

import android.app.Activity
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.ChartConstants
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.dashboard.DashboardViewModel
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.CarbohydratePrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.FatPrimary
import com.example.cekpanganai.ui.theme.FiberPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.MonounsaturatedFatPrimary
import com.example.cekpanganai.ui.theme.PolyunsaturatedFatPrimary
import com.example.cekpanganai.ui.theme.ProteinPrimary
import com.example.cekpanganai.ui.theme.SaturatedFatPrimary
import com.example.cekpanganai.ui.theme.SodiumPrimary
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.utils.Spacing


class ChartDashboard(
    modifier: Modifier = Modifier,
//    dashboardViewModel: DashboardViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
//    val dashboardState by dashboardViewModel.dashboardState.collectAsState()
//    val totalNutrition = dashboardState.totalNutrition


}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        ChartDashboard()
    }
}