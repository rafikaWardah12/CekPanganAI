package com.example.cekpanganai.presentation.profile

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.R
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.data.database.dao.UserDao
import com.example.cekpanganai.data.database.entity.UserEntity
import com.example.cekpanganai.presentation.result.ResultState
import com.example.cekpanganai.ui.utils.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val userDao: UserDao,
) : ViewModel() {
    fun onNavigateBack() {
        appNavigator.tryNavigateBack()
    }

    private val _userState = MutableStateFlow(ProfileState())
    val userState: StateFlow<ProfileState> = _userState.asStateFlow()

    private val _bmiState = MutableStateFlow(BMIState())
    val bmiState = _bmiState

    //        init {
//        viewModelScope.launch {
//            try {
//                val user = userDao.getUserById("1")
//                _userState.value = ProfileState(userById = user)
//
//                val height = user.height
//                val weight = user.weight
//                if (height != null && weight != null) {
//                    calculateBMI(height, weight)
//                }
//            } catch (e: Exception) {
//                _userState.update {
//                    it.copy(
//                        isError = true,
//                        message = Message.Error("Gagal untuk mengkalkulasi bmi: ${e.localizedMessage}")
//                    )
//                }
//            }
//        }
//    }

    fun calculateBMI(user: UserEntity) {
        viewModelScope.launch {
            if (user.height == 0.0) return@launch
            val heightM = (user.height ?: 0.0) / 100.0
            val bmi = (user.weight ?: 0.0) / (heightM * heightM)
            val category = bmiCategory.fromBMI(bmi)

            val updatedUser = user.copy(bmi = bmi)

            _bmiState.value = BMIState(bmi = bmi, category = category)

            userDao.insertUser(updatedUser) // update ke database
            _userState.update { it.copy(userById = updatedUser) } // update state juga

            Log.d("BMI", "Update data bmi berhasil di DB: $updatedUser")
        }
    }

    fun getBmi(bmi: Double) {
        val category = bmiCategory.fromBMI(bmi)
        _bmiState.value = BMIState(bmi = bmi, category = category)
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val userFirst = userDao.getUserById("1")
                _userState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        userById = userFirst,
                        message = Message.Success(userFirst)
                    )
                }
            } catch (e: Exception) {
                _userState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = Message.Error("Gagal Memuat data User: ${e.localizedMessage}")
                    )
                }
            }
        }
    }
}