package com.example.cekpanganai.presentation.dashboard

import co.yml.charts.common.model.Point
import com.example.cekpanganai.data.database.entity.HistoryEntity

data class DashboardState(
    val totalNutrition: HistoryEntity? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val dailyEnergyPoints: List<Point> = emptyList(),
    val yAxisStep: Int = 20,
    val yAxisSteps: Int = 5,
)
