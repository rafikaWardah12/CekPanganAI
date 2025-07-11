package com.example.cekpanganai.ui.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.example.cekpanganai.MainActivity
import com.yalantis.ucrop.UCrop
import java.io.File

object ImageUtils {
    const val REQUEST_CODE_PICK_IMAGE = 1001

    @JvmStatic
    fun openGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    @JvmStatic
    fun startCrop(activity: Activity, uri: Uri) {
        val photoFile =
            File(activity.externalCacheDir, "capture_image${System.currentTimeMillis()}.jpeg")
        val destinationUri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.provider",
            photoFile
        )

        val options = UCrop.Options().apply {
            setToolbarTitle("Crop Image")
            setFreeStyleCropEnabled(true)
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
            setCompressionQuality(90)

        }

        activity.grantUriPermission(
            "com.yalantis.ucrop",
            destinationUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(1f, 1f)
            .start(activity)
    }

    fun startCropCompose(
        activity: Activity,
        uri: Uri,
        cropLauncher: ActivityResultLauncher<Intent>
    ) {
        val destinationUri = Uri.fromFile(
            File(activity.externalCacheDir, "capture_image_${System.currentTimeMillis()}.jpeg")
        )

        val options = UCrop.Options().apply {
            setFreeStyleCropEnabled(true)
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
            setCompressionQuality(90)
        }

        val intent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(1f, 1f)
            .getIntent(activity)

        cropLauncher.launch(intent)
    }


    @JvmStatic
    fun takeCrop(activity: Activity, croppedUri: Uri) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("cropped_image", croppedUri.toString())
            putExtra("navigateTo", "editImage")
//            putExtra("navigateTo", "processDetect")
            flags =
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        activity.startActivity(intent)
        Log.d("crop", "Sudah masuk intentnya: $intent dan $croppedUri")
    }

    @JvmStatic
    fun handleCropResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    val selectedImageUri = data?.data
                    selectedImageUri?.let { startCrop(activity, it) }
                }

                UCrop.REQUEST_CROP -> {
                    val resultUri = UCrop.getOutput(data!!)
                    resultUri?.let { takeCrop(activity, it) }
                }

                UCrop.RESULT_ERROR -> {
                    val cropError = UCrop.getError(data!!)
                    Toast.makeText(
                        activity,
                        "Crop error: ${cropError?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}