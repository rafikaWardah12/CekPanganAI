package com.example.cekpanganai.presentation.detect

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditImageViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

}