package com.example.cekpanganai.presentation.test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.ui.utils.decodeBitmapFromUriString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.File
import java.net.URI
import java.nio.FloatBuffer
import javax.inject.Inject

@HiltViewModel
class TestingViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    fun onNavigateToDashboard() = appNavigator.tryNavigateTo(Destination.DashboardScreen())

//    val model = Interpreter(loadModelFile("yolov11.tflite"))
//
//    val fileUrl = "file:///storage/emulated/0/Android/data/com.example.cekpanganai/files/Pictures/saved_image_1748011547820.jpeg"
//    val file = File(URI(fileUrl))
//    BitmapFactory.decodeFile(file.absolutePath)
//
//    val bitmap = decodeBitmapFromUriString(this, "") // Bitmap yang ingin dikenali
//
//    // Konversi bitmap ke ByteBuffer sesuai ukuran input model
//    val inputBuffer = convertBitmapToByteBuffer(bitmap)
//
//    // Siapkan output buffer (ukuran tergantung output YOLOv11 kamu)
//    val outputBuffer = Array(1) { Array(25200) { FloatArray(85) } } // Contoh output YOLOv5
//
//    model.run(inputBuffer, outputBuffer)
//
//    // Lakukan post-processing (NMS, thresholding, dll.)
//    val results = processOutput(outputBuffer)
//
//    private fun loadModelFile(modelName: String): MappedByteBuffer {
//        val fileDescriptor = assets.openFd(modelName)
//        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
//        val fileChannel = inputStream.channel
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
//    }
//
//    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
//        val inputImage = Bitmap.createScaledBitmap(bitmap, 640, 640, true) // sesuaikan dengan ukuran model
//        val byteBuffer = ByteBuffer.allocateDirect(1 * 640 * 640 * 3 * 4)
//        byteBuffer.order(ByteOrder.nativeOrder())
//
//        val intValues = IntArray(640 * 640)
//        inputImage.getPixels(intValues, 0, 640, 0, 0, 640, 640)
//
//        for (pixel in intValues) {
//            val r = (pixel shr 16 and 0xFF) / 255.0f
//            val g = (pixel shr 8 and 0xFF) / 255.0f
//            val b = (pixel and 0xFF) / 255.0f
//
//            byteBuffer.putFloat(r)
//            byteBuffer.putFloat(g)
//            byteBuffer.putFloat(b)
//        }
//
//        return byteBuffer
//    }

}