package com.example.cekpanganai.presentation.detect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetectViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    private var _detectionState = MutableStateFlow(DetectionState())
    var detectionState: StateFlow<DetectionState> = _detectionState

    fun onDetectionCompleted(boxes: List<BoundingBox>, inferenceTime: Long?) {
        _detectionState.update {
            it.copy(boxes = boxes, inferenceTime = inferenceTime)
        }
    }

    fun setImageUri(imageUri: String?) {
        _detectionState.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun setBitmap(bitmap: ImageBitmap?) {
        _detectionState.update {
            it.copy(bitmap = bitmap)
        }
    }

    fun reset() {
        _detectionState.update {
            it.copy(
                imageUri = null,
                boxes = emptyList()
            )
        }
    }


    fun onNavigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun onNavigateToEditScreen() = appNavigator.tryNavigateTo(Destination.EditImageScreen())

    fun onNavigateToDashboard() = appNavigator.tryNavigateTo(Destination.DashboardScreen())

    fun onNavigateToResult() = appNavigator.tryNavigateTo(Destination.ResultScreen())

}

data class DetectionState(
    val imageUri: String? = null,
    val bitmap: ImageBitmap? = null,
    val boxes: List<BoundingBox> = emptyList(),
    val inferenceTime: Long? = null,
)