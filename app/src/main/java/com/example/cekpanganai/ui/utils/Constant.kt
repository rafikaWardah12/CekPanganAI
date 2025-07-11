package com.example.cekpanganai.ui.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.compose.ui.graphics.Color

data object Padding {
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 12.dp
    val large = 18.dp
    val extraLarge = 52.dp
}

data object Spacing {
    val extraSmall = 6.dp
    val small = 8.dp
    val medium = 10.dp
    val large = 12.dp
}

object AppShadows {
    val defaultShadow: Modifier
        get() = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            ambientColor = Color.Gray,
            spotColor = Color.Gray
        )
}

fun formatDate(date: Date?): String {
    if (date == null) return "Tanggal tidak tersedia"
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("id", "ID"))
    sdf.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return "${sdf.format(date)} WIB"
}

enum class DateRange {
    TODAY,
    LAST_3_DAYS,
    LAST_7_DAYS,
    LAST_30_DAYS
}

fun filterDateConverter(range: DateRange): Pair<Long, Long> {
    val endOfDay = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis

    val startOfRange = Calendar.getInstance().apply {
        when (range) {
            DateRange.TODAY -> Unit
            DateRange.LAST_3_DAYS -> add(Calendar.DAY_OF_YEAR, -2)
            DateRange.LAST_7_DAYS -> add(Calendar.DAY_OF_YEAR, -6)
            DateRange.LAST_30_DAYS -> add(Calendar.DAY_OF_YEAR, -29)
        }
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
    return startOfRange to endOfDay
}

object Constant {
    const val MODEL_PATH = "model.tflite"
    const val LABELS_PATH = "label.txt"
}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

fun getBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        it.flush()
        it.close()
    }
    return file
}

fun decodeBitmapFromUriString(context: Context, imageUriStr: String?): Bitmap? {
    return imageUriStr?.let { uriStr ->
        val uri = Uri.parse(uriStr)
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    }
}

fun exportImageToDownloads(context: Context, contentUri: Uri, fileName: String): File? {
    val downloadsDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val destFile = File(downloadsDir, fileName)

    return try {
        context.contentResolver.openInputStream(contentUri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }
        destFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun moveFileToPermanentFolder(context: Context, sourceUriString: String): Uri? {
    try {
        Log.d("moveFile", "sourceUriString: $sourceUriString")
        val sourceUri = Uri.parse(sourceUriString)

        val inputStream: InputStream? = when (sourceUri.scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                Log.d("moveFile", "Using content resolver")
                context.contentResolver.openInputStream(sourceUri)
            }

            ContentResolver.SCHEME_FILE -> {
                Log.d("moveFile", "Using file resolver")
                val file = File(sourceUri.path ?: return null)
                if (!file.exists()) {
                    Log.e("moveFile", "File from file:// URI does not exist.")
                    return null
                }
                FileInputStream(file)
            }

            else -> {
                Log.e("moveFile", "Unsupported URI scheme: ${sourceUri.scheme}")
                return null
            }
        }

        if (inputStream == null) {
            Log.e("moveFile", "Failed to open input stream.")
            return null
        }

        val permanentDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (permanentDir == null) {
            Log.e("moveFile", "Permanent directory is null.")
            return null
        }

        if (!permanentDir.exists()) {
            val created = permanentDir.mkdirs()
            Log.d("moveFile", "Directory created: $created")
        }

        val fileName = "saved_image_${System.currentTimeMillis()}.jpeg"
        val destinationFile = File(permanentDir, fileName)

        val outputStream = FileOutputStream(destinationFile)
        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        // Only delete content URIs (to avoid deleting real files accidentally)
        if (sourceUri.scheme == ContentResolver.SCHEME_CONTENT) {
            context.contentResolver.delete(sourceUri, null, null)
        }

        Log.d("moveFile", "File moved to: ${destinationFile.absolutePath}")
        return Uri.fromFile(destinationFile)
    } catch (e: Exception) {
        Log.e("moveFile", "Exception: ${e.message}", e)
        return null
    }
}

