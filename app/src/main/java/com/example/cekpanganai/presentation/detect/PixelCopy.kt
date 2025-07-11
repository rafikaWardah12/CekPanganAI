package com.example.cekpanganai.presentation.detect

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import java.io.File
import java.io.FileOutputStream

//fun captureViewToUri(
//    activity: Activity,
//    view: View,
//    context: Context,
//    onResult: (Uri?) -> Unit
//) {
//    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//
//    val location = IntArray(2)
//    view.getLocationInWindow(location)
//    val rect = Rect(location[0], location[1], location[0] + view.width, location[1] + view.height)
//
//    PixelCopy.request(
//        activity.window,
//        rect,
//        bitmap,
//        { result ->
//            if (result == PixelCopy.SUCCESS) {
//                val uri = saveBitmapToPermanentFolder(context, bitmap)
//                onResult(uri)
//            } else {
//                onResult(null)
//            }
//        },
//        Handler(Looper.getMainLooper())
//    )
//}

fun captureViewToUri(
    activity: Activity,
    view: View,
    context: Context,
    onCaptured: (Uri?) -> Unit
) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)

    try {
        // Simpan langsung ke folder permanen
        val permanentDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (permanentDir == null) {
            onCaptured(null)
            return
        }
        if (!permanentDir.exists()) permanentDir.mkdirs()

        val fileName = "captured_image_${System.currentTimeMillis()}.jpeg"
        val destinationFile = File(permanentDir, fileName)

        FileOutputStream(destinationFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            destinationFile
        )
        onCaptured(uri)
    } catch (e: Exception) {
        e.printStackTrace()
        onCaptured(null)
    }
}


fun saveBitmapToPermanentFolder(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val permanentDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (permanentDir == null) return null
        if (!permanentDir.exists()) permanentDir.mkdirs()

        val fileName = "captured_image_${System.currentTimeMillis()}.jpeg"
        val destinationFile = File(permanentDir, fileName)

        FileOutputStream(destinationFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        Uri.fromFile(destinationFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun captureOnlyImageCanvas(
    activity: Activity,
    context: Context,
    imageUriString: String,
    boxList: List<BoundingBox>,
    onCaptured: (Uri?) -> Unit
) {
    val captureContainer = FrameLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(1080, 1080) // Ukuran disesuaikan
        visibility = View.INVISIBLE
    }

    val composeView = ComposeView(context).apply {
        setContent {
        }
    }

    captureContainer.addView(composeView)
    activity.addContentView(captureContainer, ViewGroup.LayoutParams(1080, 1080))

    // Tunggu layout selesai
    composeView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            composeView.viewTreeObserver.removeOnGlobalLayoutListener(this)

            captureViewToUri(
                activity = activity,
                view = composeView,
                context = context
            ) { uri ->
                onCaptured(uri)
                (captureContainer.parent as? ViewGroup)?.removeView(captureContainer)
            }
        }
    })
}

