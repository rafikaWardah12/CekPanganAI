package com.example.cekpanganai.presentation.test

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.ChartConstants
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import coil.compose.rememberAsyncImagePainter
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.detect.camera.TFLiteClassifier
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.presentation.result.ResultViewModel
import com.example.cekpanganai.ui.utils.Constant
import com.example.cekpanganai.ui.utils.Spacing
import kotlinx.coroutines.delay

@Composable
fun TestingScreen(
    modifier: Modifier = Modifier,
    testingViewModel: TestingViewModel = hiltViewModel(),
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    var selectedSliceDescription by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current as Activity
    val boundingBoxes = remember { mutableStateOf<List<BoundingBox>>(emptyList()) }

    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = "Protein",
                value = 23f,
                color = Color.Red,
                sliceDescription = { slicePercentage ->
                    "Slice name : label  \n Percentage  : $slicePercentage %"
                }
            ),
            PieChartData.Slice(
                label = "Protein",
                value = 23f,
                color = Color.Black,
                sliceDescription = { slicePercentage ->
                    "Slice name : label  \n Percentage  : $slicePercentage %"
                }
            ),
            PieChartData.Slice(
                label = "Lemak",
                value = 23f,
                color = Color.Blue,
                sliceDescription = { slicePercentage ->
                    "Slice name : label  \n Percentage  : $slicePercentage %"
                }
            ),
            PieChartData.Slice(
                label = "Protein",
                value = 10f,
                color = Color.Magenta,
                sliceDescription = { slicePercentage ->
                    "Slice name : label  \n Percentage  : $slicePercentage %"
                }
            )
        ),
        plotType = PlotType.Donut
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        activeSliceAlpha = 0.5f,
        animationDuration = 600,
        labelType = PieChartConfig.LabelType.PERCENTAGE,
        sliceLabelTextColor = Color.Green,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.START,
        isSumVisible = true,
        sumUnit = "70",
        labelVisible = true,
        labelColorType = PieChartConfig.LabelColorType.SLICE_COLOR,
        labelColor = Color.Green,
        backgroundColor = Color.DarkGray,
        inActiveSliceAlpha = 23f,
        isEllipsizeEnabled = false,
        chartPadding = 20,
        sliceMinTextWidthToEllipsize = 20.dp,
        isClickOnSliceEnabled = true,
        accessibilityConfig = AccessibilityConfig(
            chartDescription = ChartConstants.CHART_DESCRIPTION,
            shouldHandleBackWhenTalkBackPopUpShown = false,
            descriptionTextSize = TextUnit(1f, type = TextUnitType.Sp),
            titleTextSize = TextUnit(100f, type = TextUnitType.Sp)
        ),
    )


    val classifier = remember {
        TFLiteClassifier(
            context = context,
            modelPath = Constant.MODEL_PATH,   // Ganti jika nama file model berbeda
            labelPath = Constant.LABELS_PATH,           // Ganti jika nama file label berbeda
            detectorListener = object : TFLiteClassifier.DetectorListener {
                override fun onEmptyDetect() {
                    Log.d("Detector", "No objects detected.")
                    boundingBoxes.value = emptyList()

                }

                override fun onDetect(boundingBox: List<BoundingBox>, inferenceTime: Long) {
                    Log.d("Detector", "Detected ${boundingBox.size} objects in $inferenceTime ms")
                    boundingBoxes.value = boundingBox
                    boundingBox.forEach {
                        Log.d("Box", "${it.clsName}: ${it.cnf}")
                    }
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        resultViewModel.seedInitialData()
        delay(100)
        classifier.setup()
        classifier.detectImageFromUri(
            "file:///storage/emulated/0/Android/data/com.example.cekpanganai/files/Pictures/saved_image_1748011547820.jpeg"
        )
    }

    // Optional UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.large)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)  // Ubah sesuai proporsi gambar asli
        ) {
            // Gambar yang dideteksi
            Image(
                painter = rememberAsyncImagePainter(
                    model = "file:///storage/emulated/0/Android/data/com.example.cekpanganai/files/Pictures/saved_image_1748011547820.jpeg"
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Overlay Bounding Box
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                for (box in boundingBoxes.value) {
                    val left = box.x1 * canvasWidth
                    val top = box.y1 * canvasHeight
                    val right = box.x2 * canvasWidth
                    val bottom = box.y2 * canvasHeight

                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(left, top),
                        size = Size(right - left, bottom - top),
                        style = Stroke(width = 4f)
                    )

                    // Optional: Nama kelas
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            box.clsName,
                            left,
                            top - 10f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.RED
                                textSize = 40f
                                style = android.graphics.Paint.Style.FILL
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Mendeteksi gambar dari file...", modifier = Modifier.padding(16.dp))
        CustomButton(
            text = stringResource(id = R.string.save),
            onClick = { },
            isWide = true,
            isRounded = true,
        )
        CustomButton(
            text = "Masuk Dashboard",
            onClick = { testingViewModel.onNavigateToDashboard() },
            isWide = true,
            isRounded = true,
        )
    }
}