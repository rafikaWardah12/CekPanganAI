package com.example.cekpanganai.presentation.detect

import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.MainViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.databinding.ActivityMainBinding
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomTopBar

@Composable
fun DetectScreen(
    modifier: Modifier = Modifier,
    detectViewModel: DetectViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val detectViewModelState by rememberUpdatedState(newValue = detectViewModel)

//    AndroidView(
//        factory = { context ->
//            val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
//
//            binding.btnClose.setOnClickListener {
//                Log.e("Intent", "terkirim")
//                detectViewModel.onNavigateBack()
//            }
////            binding.btnCaptureCamera.setOnClickListener {
////                detectViewModel.onNavigateToDashboard()
////            }
//            binding.root
//        },
//        modifier = Modifier.fillMaxSize()
//    )

//    Surface {
//        Column(modifier.verticalScroll(rememberScrollState())) {
//            CustomTopBar(
//                title = stringResource(id = R.string.detect),
//                navigateBack = { detectViewModel.onNavigateBack() })
//            Text(text = "Detect Screen")
//            CustomButton(
//                text = stringResource(id = R.string.save),
//                onClick = { detectViewModel.onNavigateToProcess() })
//        }
//    }
}