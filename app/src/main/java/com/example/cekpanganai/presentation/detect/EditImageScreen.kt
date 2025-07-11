package com.example.cekpanganai.presentation.detect

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.detect.camera.TFLiteClassifier
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.presentation.detect.camera.Camera
import com.example.cekpanganai.presentation.detect.component.ExitConfirmationDialog
import com.example.cekpanganai.presentation.result.ResultViewModel
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.utils.Constant
import com.example.cekpanganai.ui.utils.ImageUtils
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.delay

@Composable
fun EditImageScreen(
    modifier: Modifier = Modifier,
    detectViewModel: DetectViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    latestIntent: Intent?,
    resultViewModel: ResultViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity

    val detectionState by detectViewModel.detectionState.collectAsState()
    val boundingBoxes = detectionState.boxes
    val imageUriString = detectionState.imageUri
    val inferenceTime = detectionState.inferenceTime

    var isDetecting by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    val cropLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let {
                    val imageUri = it.toString()
                    detectViewModel.setImageUri(imageUri)
                }
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { it: Uri? ->
            it?.let { ImageUtils.startCropCompose(context as Activity, it, cropLauncher) }

        }

    val classifier = remember {
        TFLiteClassifier(
            context = context,
            modelPath = Constant.MODEL_PATH,
            labelPath = Constant.LABELS_PATH,
            detectorListener = object : TFLiteClassifier.DetectorListener {
                override fun onEmptyDetect() {
                    Log.d("Detector", "No objects detected.")
                    detectViewModel.onDetectionCompleted(boxes = emptyList(), inferenceTime = null)

                }

                override fun onDetect(boundingBox: List<BoundingBox>, inferenceTime: Long) {
                    Log.d("Detector", "Detected ${boundingBox.size} objects in $inferenceTime ms")
                    detectViewModel.onDetectionCompleted(
                        boxes = boundingBox,
                        inferenceTime = inferenceTime
                    )
                    boundingBox.forEach {
                        Log.d("Box", "${it.clsName}: ${it.cnf}")
                    }
                }
            }
        )
    }

// CODINGAN SEBELUMNYAAA
    LaunchedEffect(Unit) {
        resultViewModel.seedInitialData()
        delay(100)
        classifier.setup()
        if (latestIntent != null) {
            val image = latestIntent.getStringExtra("cropped_image")
            detectViewModel.setImageUri(image)
            Log.d("image", "image cropped: $image")
        }
    }

    LaunchedEffect(imageUriString) {
        if (imageUriString != null) {
            isDetecting = true
            val detect = classifier.detectImageFromUri(imageUriString)
            Log.d("image", "Detect cropped: $detect")
            isDetecting = false
        } else {
            Log.e("Image", "Failed to load URI: $imageUriString")
        }
    }

    if (showExitDialog) {
        ExitConfirmationDialog(onDismiss = { showExitDialog = false }, onConfirmExit = {
            detectViewModel.onNavigateToDashboard()
        })
    }

    Surface() {
        Column {
            CustomTopBar(
                title = stringResource(id = R.string.edit_image),
                isFilled = true,
                navigateBack = { showExitDialog = true })
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = Padding.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(color = GreenSecondary)
                ) {
                    if (isDetecting) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(64.dp),
                            color = Color.White,
                            strokeWidth = 6.dp
                        )
                    } else {
                        if (imageUriString != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUriString),
                                contentDescription = stringResource(
                                    id = R.string.result
                                ),
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillWidth
                            )
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height

                                for (box in boundingBoxes) {
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

                                    drawContext.canvas.nativeCanvas.apply {
                                        drawText(
                                            box.clsName + " " + box.cnf,
                                            left,
                                            top - 10f,
                                            Paint().apply {
                                                color = android.graphics.Color.RED
                                                textSize = 40f
                                                style = Paint.Style.FILL
                                            }
                                        )
                                    }
                                }
                            }
                            if (boundingBoxes.isEmpty()) {
                                Text(
                                    text = "Gambar tidak berhasil dideteksi. Coba deteksi lagi?",
                                    color = Color.Red,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Padding.large))
                Text(
                    text = "Inference Times: $inferenceTime ms",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Deteksi Selesai. Anda dapat menyimpan atau mengedit hasil.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Padding.extraLarge))
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.large)) {
                    CustomButton(
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_upload),
                                contentDescription = stringResource(id = R.string.upload_from_gallery),
                                tint = GreenPrimary,
                                modifier = Modifier.width(20.dp)
                            )
                        },
                        text = stringResource(id = R.string.upload_from_gallery),
                        onClick = {
                            galleryLauncher.launch("image/*")
                            Log.e("image", "image cropper is upload from gallery")
                        },
                        isWide = true,
                        isRounded = true,
                        isOutline = true,
                    )
                    CustomButton(
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_camera),
                                contentDescription = stringResource(id = R.string.capture_image),
                                tint = GreenPrimary,
                                modifier = Modifier.width(20.dp)
                            )
                        },
                        text = stringResource(id = R.string.capture_image),
                        onClick = {
                            val intent = Intent(context, Camera::class.java)
                            context.startActivity(intent)
                            Log.e("image", "sudah masuk ke kamera")
                        },
                        isWide = true,
                        isRounded = true,
                        isOutline = true,
                    )
                    CustomButton(
                        text = stringResource(id = R.string.save),
                        onClick = {
                            detectViewModel.onNavigateToResult()
                        },
                        isWide = true,
                        isRounded = true,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun test() {
    CekPanganAITheme {
//        EditImageScreen()
    }
}