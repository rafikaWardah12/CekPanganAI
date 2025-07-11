package com.example.cekpanganai.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.data.database.dao.UserDao
import com.example.cekpanganai.data.database.entity.UserEntity
import com.example.cekpanganai.ui.utils.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FormProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val userDao: UserDao,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    fun onNavigateToBMIScore() {
        appNavigator.tryNavigateTo(Destination.BMI_SCORE_ROUTE)
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            try {
//                val user = UserEntity(
//                    name = name,
//                    age = age,
//                    gender = gender,
//                    height = height,
//                    weight = weight,
//                    bmi = null,
//                    imgProfile = null,
//                    createdAt = Date(),
//                    updatedAt = Date(),
//                    id = 1
//                )
                userDao.insertUser(user)
                Log.d("profile", "User berhasil masuk database: $user")
                _profileState.update {
                    it.copy(
                        userById = user,
                        isError = false,
                        message = Message.Success(user)
                    )
                }
            } catch (e: Exception) {
                Log.d("profile", "User gagal masuk database: ${e.localizedMessage}", e)
                _profileState.update {
                    it.copy(
                        isError = true,
                        message = Message.Error("Gagal Menambahkan User: ${e.localizedMessage}")
                    )
                }
            }
        }
    }
}

