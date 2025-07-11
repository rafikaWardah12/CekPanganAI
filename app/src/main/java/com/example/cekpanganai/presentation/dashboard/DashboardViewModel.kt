package com.example.cekpanganai.presentation.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.data.database.dao.HistoryDao
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val historyDao: HistoryDao,
) : ViewModel() {
    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState = _dashboardState.asStateFlow()

    fun onNavigateToTesting() = appNavigator.tryNavigateTo(Destination.TestingScreen())

    fun onNavigateToProfile() {
        appNavigator.tryNavigateTo(Destination.ProfileScreen())
    }

    fun getFilterByDate(start: Long, end: Long) {
        viewModelScope.launch {
            val filter = historyDao.filterByDate(start, end).collectLatest {
                getTotalNutrition(it)
            }
            Log.d("Date", "$filter")
        }
    }

    fun getTotalNutrition(historyFood: List<HistoryEntity>) {
        viewModelScope.launch {
            val totalNutrition = historyFood.fold(
                initial = HistoryEntity(
                    id = "",
                    title_food = "",
                    description_food = null,
                    bounding_box = emptyList(),
                    image_path = null,
                    total_amount_serving = 0,
                    total_energy = 0.0,
                    total_lemak = 0.0,
                    total_lemak_jenuh = 0.0,
                    total_lemak_tak_jenuh_ganda = 0.0,
                    total_lemak_tak_jenuh_tunggal = 0.0,
                    total_kolestrol = 0.0,
                    total_protein = 0.0,
                    total_karbohindrat = 0.0,
                    total_serat = 0.0,
                    total_gula = 0.0,
                    total_sodium = 0.0,
                    total_kalium = 0.0,
                    created_at = Date(),
                    updated_at = Date()
                )
            ) { acc, foodTable ->
                acc.copy(
                    total_amount_serving = (acc.total_amount_serving
                        ?: 0) + (foodTable.total_amount_serving ?: 0),
                    total_energy = (acc.total_energy ?: 0.0) + (foodTable.total_energy ?: 0.0),
                    total_karbohindrat = (acc.total_karbohindrat
                        ?: 0.0) + (foodTable.total_karbohindrat ?: 0.0),
                    total_protein = (acc.total_protein ?: 0.0) + (foodTable.total_protein ?: 0.0),
                    total_lemak = (acc.total_lemak ?: 0.0) + (foodTable.total_lemak ?: 0.0),
                    total_lemak_jenuh = (acc.total_lemak_jenuh
                        ?: 0.0) + (foodTable.total_lemak_jenuh ?: 0.0),
                    total_lemak_tak_jenuh_ganda = (acc.total_lemak_tak_jenuh_ganda
                        ?: 0.0) + (foodTable.total_lemak_tak_jenuh_ganda ?: 0.0),
                    total_lemak_tak_jenuh_tunggal = (acc.total_lemak_tak_jenuh_tunggal
                        ?: 0.0) + (foodTable.total_lemak_tak_jenuh_tunggal ?: 0.0),
                    total_kolestrol = (acc.total_kolestrol ?: 0.0) + (foodTable.total_kolestrol
                        ?: 0.0),
                    total_gula = (acc.total_gula ?: 0.0) + (foodTable.total_gula ?: 0.0),
                    total_sodium = (acc.total_sodium ?: 0.0) + (foodTable.total_sodium ?: 0.0),
                    total_kalium = (acc.total_kalium ?: 0.0) + (foodTable.total_kalium ?: 0.0),
                    total_serat = (acc.total_serat ?: 0.0) + (foodTable.total_serat ?: 0.0),
                )
            }
            val currentDate = Calendar.getInstance()
            val daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dailyEnergyMap = mutableMapOf<Int, Float>()

            repeat(daysInMonth) { dayIndex ->
                dailyEnergyMap[dayIndex + 1] = 0f
            }

            historyFood.forEach { item ->
                val cal = Calendar.getInstance()
                cal.time = item.created_at
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val energy = item.total_energy?.toFloat() ?: 0f
                dailyEnergyMap[day] = (dailyEnergyMap[day] ?: 0f) + energy
            }

            val dailyEnergyPoints = dailyEnergyMap.entries
                .sortedBy { it.key }
                .map { entry ->
                    Point(entry.key.toFloat(), entry.value)
                }
//                .mapIndexed { index, entry ->
//                    Point(index.toFloat(), entry.value)
//                }

            // 👇 Hitung max Y dan step-nya
            val maxY = dailyEnergyPoints.maxOfOrNull { it.y } ?: 0f
            val steps = 5
            val stepValue = if (maxY > 0f) ceil(maxY / steps).toInt() else 20

            // 👇 Update state
            _dashboardState.update {
                it.copy(
                    totalNutrition = totalNutrition,
                    dailyEnergyPoints = dailyEnergyPoints,
                    yAxisStep = stepValue,
                    yAxisSteps = steps
                )
            }

            if (historyFood.isEmpty()) {
                Log.d("dashboard", "No history data for selected date range")
            }
        }
    }
}