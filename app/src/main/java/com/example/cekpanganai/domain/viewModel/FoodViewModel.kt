package com.example.cekpanganai.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.domain.repository.ResultRepository
import com.example.cekpanganai.ui.utils.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class FoodViewModel @Inject constructor(
//    private val foodRepository: ResultRepository
//) : ViewModel() {
//    //    private val _foodState = MutableStateFlow<Message<FoodItemEntity>>(Message.Loading)
//    private val _foodItemState = MutableStateFlow(FoodItemState())
//    val foodItemState: StateFlow<FoodItemState> = _foodItemState.asStateFlow()
//
//    fun getFoodById(id: String) {
//        viewModelScope.launch {
//            foodRepository.getFoodById(id).collect { foodItem ->
//                when (foodItem) {
//                    is Message.Loading -> {
//                        _foodItemState.value = FoodItemState(isLoading = true)
//                    }
//
//                    is Message.Success -> {
//                        _foodItemState.value =
//                            FoodItemState(isLoading = true, foodItem = foodItem.data)
//                    }
//
//                    else -> {
//                        _foodItemState.value = FoodItemState(isError = true, isLoading = false)
//                    }
//                }
//
//            }
//        }
//    }
//}

