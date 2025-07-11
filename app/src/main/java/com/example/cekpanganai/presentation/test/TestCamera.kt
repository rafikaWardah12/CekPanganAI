package com.example.cekpanganai.presentation.test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.cekpanganai.MainActivity
import com.example.cekpanganai.databinding.ActivityMainBinding
import com.example.cekpanganai.presentation.detect.DetectViewModel
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.ui.utils.ImageUtils

import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//@AndroidEntryPoint
class TestCamera : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isFrontCamera = false

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var detector: TestTFLiteClassifier

    private lateinit var cameraExecutor: ExecutorService

    private var capture: ImageCapture? = null

    private val detectViewModel: DetectViewModel by viewModels()

    private lateinit var pickImage: ActivityResultLauncher<Intent>

    private var lastBoundingBox: List<BoundingBox>? = null
    private var lastInference: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        detector = TFLiteClassifier(baseContext, MODEL_PATH, LABELS_PATH, this)
//        detector.setup()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        //Pengecekan File model dan label
//        if (!applicationContext.assets.list("")!!.contains(MODEL_PATH)) {
//            Log.e("Debug", "Model Not found: $MODEL_PATH")
//        }
//        if (!applicationContext.assets.list("")!!.contains(LABELS_PATH)) {
//            Log.e("Debug", "Model Not found: $LABELS_PATH")
//        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnCaptureCamera.setOnClickListener {
            takePhoto()
        }

        binding.btnClose.setOnClickListener {
            Log.e("Debug", "button close not worked")
//            detectViewModel.onNavigateBack()

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("navigateTo", "back")
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
                Log.e("Intent", intent.toString())
            }
            this.startActivity(intent)
        }

        binding.btnGallery.setOnClickListener {
//            openGallery()
            ImageUtils.openGallery(this)
        }
    }

    fun takePhoto() {
        val imageCapture = capture ?: return

        val photoFile = File(externalCacheDir, "capture_image${System.currentTimeMillis()}.jpeg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("image", "Photo capture failed: ${exception.message}", exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri =
                        FileProvider.getUriForFile(
                            this@TestCamera,
                            "${packageName}.provider",
                            photoFile
                        )
                    Log.d("image", "Photo capture succeeded: $savedUri")

                    ImageUtils.startCrop(this@TestCamera, savedUri)
//                    Lamaaa
//                    val intent = Intent(this@Camera, MainActivity::class.java).apply {
////                        putExtra("navigateTo", "result")
//                        putExtra("boundingBox", Gson().toJson(lastBoundingBox))
////                putExtra("image", bitmapToByteArray(resultBitmap)) // Kirim gambar jika perlu
//                        putExtra("image", savedUri.toString())
//                        flags =
//                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    }

//                    Log.e("capture", "$lastInference" + "data" + "$lastBoundingBox")
//                    Log.e("image", "file: $photoFile")
                    startActivity(intent)
                }
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageUtils.handleCropResult(
            this@TestCamera,
            requestCode = requestCode,
            resultCode = resultCode,
            data = data
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val cameraProvider =
            cameraProvider ?: throw IllegalStateException("Camera Initialization Failed.")

        val rotation = binding.viewFinder.display?.rotation ?: Surface.ROTATION_0

        val cameraSelector = CameraSelector
            .Builder()
            .requireLensFacing(
                if (isFrontCamera) CameraSelector.LENS_FACING_FRONT
                else CameraSelector.LENS_FACING_BACK
            )
            .build()

        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetRotation(binding.viewFinder.display?.rotation ?: Surface.ROTATION_0)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()

        imageAnalyzer?.setAnalyzer(cameraExecutor) { imageProxy ->
            val bitmapBuffer =
                Bitmap.createBitmap(
                    imageProxy.width,
                    imageProxy.height,
                    Bitmap.Config.ARGB_8888
                )
            imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
            imageProxy.close()

            val matrix = Matrix().apply {
                postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())

                if (isFrontCamera) {
                    postScale(
                        -1f,
                        1f,
                        imageProxy.width.toFloat(),
                        imageProxy.height.toFloat()
                    )
                }
            }

            val rotateBitmap = Bitmap.createBitmap(
                bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height,
                matrix, true
            )
//            detector.detectObject(rotateBitmap)
            Log.d(TAG, "Camera started Bind")
            Log.d(TAG, "Permissions granted Bind: ${allPermissionsGranted()}")

        }

        capture = ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(binding.viewFinder.display?.rotation ?: Surface.ROTATION_0)
            .build()

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalyzer,
                capture
            )
            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it[Manifest.permission.CAMERA] == true) {
            startCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.clear()
        cameraExecutor.shutdown()
    }

    override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

//    override fun onEmptyDetect() {
//        binding.overlay.invalidate()
//    }
//
//    override fun onDetect(boundingBox: List<BoundingBox>, inferenceTime: Long) {
//        runOnUiThread {
//            lastBoundingBox = boundingBox
//            lastInference = inferenceTime
//
//            binding.inferenceTime.text = "${inferenceTime}ms"
//            binding.overlay.apply {
//                setResults(boundingBox)
//                invalidate()
//            }
//        }
//    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
        private val REQUEST_CODE_CROP_IMAGE = 1002
        private const val TAG = "Camera"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
    }
}