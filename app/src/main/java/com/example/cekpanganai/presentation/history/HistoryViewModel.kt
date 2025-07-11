package com.example.cekpanganai.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.data.database.dao.HistoryDao
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.presentation.historyDetail.HistoryDetailState
import com.example.cekpanganai.presentation.result.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val historyDao: HistoryDao
) : ViewModel() {
    fun onNavigateBack() = appNavigator.tryNavigateBack()
    fun onNavigateToDetailHistory(id: String) {
        appNavigator.tryNavigateTo(Destination.HistoryDetailScreen(id = id))
    }

    private val _historyState = MutableStateFlow(HistoryState())
    val historyState: StateFlow<HistoryState> = _historyState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    fun getHistories() {
        viewModelScope.launch {
            _historyState.update { it.copy(isLoading = true) }
            historyDao.getHistories().collectLatest() { historyData ->
                _historyState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        histories = historyData
                    )
                }
            }
        }
    }
}