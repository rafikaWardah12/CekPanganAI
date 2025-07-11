package com.example.cekpanganai.presentation.detect.camera

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import com.example.cekpanganai.ui.utils.decodeBitmapFromUriString
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TFLiteClassifier(
    private val context: Context,
    private val modelPath: String,
    private val labelPath: String,
    private val detectorListener: DetectorListener
) {
    private var interpreter: Interpreter? = null
    private var labels = mutableListOf<String>()

    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numChannel = 0
    private var numElements = 0

    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(INPUT_MEAN, INPUT_STANDARD_DEVIATION))
        .add(CastOp(INPUT_IMAGE_TYPE))
        .build()

    fun setup() {
        val model = FileUtil.loadMappedFile(context, modelPath)
        val options = Interpreter.Options()
        options.numThreads = 4
        interpreter = Interpreter(model, options)

        try {
            val inputShape = interpreter?.getInputTensor(0)?.shape() ?: return
            val outputShape = interpreter?.getOutputTensor(0)?.shape() ?: return
            tensorWidth = inputShape[1]
            tensorHeight = inputShape[2]
            numChannel = outputShape[1]
            numElements = outputShape[2]
        } catch (e: Exception) {
            Log.e("TFLiteClassifier", "Error getting model dimensions", e)
        }

        try {
            val inputStream = context.assets.open(labelPath)
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.lineSequence().forEach { line ->
                if (line.isNotBlank()) labels.add(line)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun clear() {
        interpreter?.close()
        interpreter = null
    }

    fun detectImageFromUri(uriString: String) {
        val bitmap = decodeBitmapFromUriString(context, uriString)
        if (bitmap != null) {
            detectObject(bitmap)
        } else {
            Log.e("TFLiteClassifier", "Gagal decode bitmap dari URI: $uriString")
            detectorListener.onEmptyDetect()
        }
    }

    fun detectObject(frame: Bitmap) {
        interpreter ?: return
        if (tensorWidth == 0 || tensorHeight == 0 || numChannel == 0 || numElements == 0) return

        var inferenceTime = SystemClock.uptimeMillis()

        val resizedBitmap = Bitmap.createScaledBitmap(frame, tensorWidth, tensorHeight, false)
        val tensorImage = TensorImage(INPUT_IMAGE_TYPE)
        tensorImage.load(resizedBitmap)
        val processedImage = imageProcessor.process(tensorImage)
        val imageBuffer = processedImage.buffer

        val output =
            TensorBuffer.createFixedSize(intArrayOf(1, numChannel, numElements), OUTPUT_IMAGE_TYPE)
        interpreter?.run(imageBuffer, output.buffer)

        val bestBoxes = bestBox(output.floatArray)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        if (bestBoxes.isEmpty()) {
            detectorListener.onEmptyDetect()
        } else {
            detectorListener.onDetect(bestBoxes, inferenceTime)
        }
    }

    private fun bestBox(output: FloatArray): List<BoundingBox> {
        val numAnchors = 8400
        val numChannels = 43
        val boxes = mutableListOf<BoundingBox>()

        val inputShape = interpreter?.getInputTensor(0)?.shape() ?: return emptyList()
        val inputImageHeight = inputShape[1]
        val inputImageWidth = inputShape[2]

        for (anchor in 0 until numAnchors) {
            var maxConf = -1.0f
            var classIdx = -1

            for (cls in 4 until numChannels) {
                val conf = output[cls * numAnchors + anchor]
                if (conf > maxConf) {
                    maxConf = conf
                    classIdx = cls - 4
                }
            }

            if (maxConf > CONFIDENCE_THRESHOLD) {
                val cx = output[0 * numAnchors + anchor]
                val cy = output[1 * numAnchors + anchor]
                val w = output[2 * numAnchors + anchor]
                val h = output[3 * numAnchors + anchor]

                val x1 = (cx - w / 2f) / inputImageWidth
                val y1 = (cy - h / 2f) / inputImageHeight
                val x2 = (cx + w / 2f) / inputImageWidth
                val y2 = (cy + h / 2f) / inputImageHeight

                boxes.add(
                    BoundingBox(
                        x1 = x1,
                        y1 = y1,
                        x2 = x2,
                        y2 = y2,
                        cx = cx,
                        cy = cy,
                        w = w,
                        h = h,
                        cnf = maxConf,
                        cls = classIdx,
                        clsName = labels.getOrElse(classIdx) { "unknown" }
                    )
                )
            }
        }

        return applyNMS(boxes)
    }

    private fun applyNMS(boxes: List<BoundingBox>): MutableList<BoundingBox> {
        val sortedBoxes = boxes.sortedByDescending { it.cnf }.toMutableList()
        val selectedBoxes = mutableListOf<BoundingBox>()

        while (sortedBoxes.isNotEmpty()) {
            val first = sortedBoxes.removeAt(0)
            selectedBoxes.add(first)

            val iterator = sortedBoxes.iterator()
            while (iterator.hasNext()) {
                val nextBox = iterator.next()
                val iou = calculateIoU(first, nextBox)
                if (iou >= IOU_THRESHOLD) {
                    iterator.remove()
                }
            }
        }

        return selectedBoxes
    }

    private fun calculateIoU(box1: BoundingBox, box2: BoundingBox): Float {
        val xA = maxOf(box1.x1, box2.x1)
        val yA = maxOf(box1.y1, box2.y1)
        val xB = minOf(box1.x2, box2.x2)
        val yB = minOf(box1.y2, box2.y2)

        val interWidth = maxOf(0f, xB - xA)
        val interHeight = maxOf(0f, yB - yA)
        val interArea = interWidth * interHeight

        val box1Area = (box1.x2 - box1.x1) * (box1.y2 - box1.y1)
        val box2Area = (box2.x2 - box2.x1) * (box2.y2 - box2.y1)
        val unionArea = box1Area + box2Area - interArea

        return if (unionArea <= 0f) 0f else interArea / unionArea
    }

    interface DetectorListener {
        fun onEmptyDetect()
        fun onDetect(boundingBox: List<BoundingBox>, inferenceTime: Long)
    }

    companion object {
        private const val INPUT_MEAN = 0f
        private const val INPUT_STANDARD_DEVIATION = 255f
        private val INPUT_IMAGE_TYPE = DataType.FLOAT32
        private val OUTPUT_IMAGE_TYPE = DataType.FLOAT32

        //        private const val CONFIDENCE_THRESHOLD = 0.3f
        private const val CONFIDENCE_THRESHOLD = 0.5f
        private const val IOU_THRESHOLD = 0.5f
    }
}
