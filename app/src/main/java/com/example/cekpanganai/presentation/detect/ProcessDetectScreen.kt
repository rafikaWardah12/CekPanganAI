package com.example.cekpanganai.presentation.detect

import android.app.Activity
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.detect.camera.TFLiteClassifier
import com.example.cekpanganai.presentation.component.Logo
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.utils.Constant
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.decodeBitmapFromUriString
import kotlinx.coroutines.delay

@Composable
fun ProcessDetectScreen(
    latestIntent: Intent?,
    modifier: Modifier = Modifier,
    detectViewModel: DetectViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()

    val classifier = remember {
        TFLiteClassifier(
            context = context,
            modelPath = Constant.MODEL_PATH,   // Ganti jika nama file model berbeda
            labelPath = Constant.LABELS_PATH,           // Ganti jika nama file label berbeda
            detectorListener = object : TFLiteClassifier.DetectorListener {
                override fun onEmptyDetect() {
                    detectViewModel.onDetectionCompleted(emptyList(), null)
                    Log.d("Detector", "No objects detected.")
                }

                override fun onDetect(boundingBox: List<BoundingBox>, inferenceTime: Long) {
                    Log.d("Detector", "Detected ${boundingBox.size} objects in $inferenceTime ms")
                    detectViewModel.onDetectionCompleted(boundingBox, inferenceTime)
                    boundingBox.forEach {
                        Log.d("Box", "${it.clsName}: ${it.cnf}")
                    }
                }
            }
        )
    }

    var imageUriString by remember { mutableStateOf<String?>(null) }
    var isDetecting by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        classifier.setup()
        latestIntent?.getStringExtra("cropped_image")?.let { uri ->
            imageUriString = uri
//            detectViewModel.bitmap = decodeBitmapFromUriString(context, uri)?.asImageBitmap()
            classifier.detectImageFromUri(uri)
        }
    }


    Scaffold {
        Surface(modifier.padding(it), color = MaterialTheme.colorScheme.surface)
        {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .background(GreenPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scanner),
                        contentDescription = stringResource(
                            id = R.string.detect
                        )
                    )
//                    if (detectViewModel.bitmap != null) {
//                        Image(
//                            bitmap = detectViewModel.bitmap!!,
//                            contentDescription = stringResource(
//                                id = R.string.detect
//                            ),
//                            modifier = Modifier
////                                .height(340.dp)
////                                .width(340.dp)
//                                .fillMaxSize(),
//                            contentScale = ContentScale.FillWidth
//                        )
//                    }
//                    Image(
//                        painter = painterResource(id = R.drawable.contoh),
//                        contentDescription = stringResource(
//                            id = R.string.detect
//                        )
//                    )

                }
                Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Logo()
                    CircularProgressIndicator(color = GreenPrimary)
                    Spacer(modifier = Modifier.height(Padding.large))
                    Text(
                        text = if (isDetecting) stringResource(id = R.string.description_process_detect) else "Gambar Anda Telah Kami Analisis. Segera Kami Tampilkan Hasilnya",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
//        ProcessDetectScreen()
    }
}