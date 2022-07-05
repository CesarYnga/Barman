package com.cesarynga.barman.generator.ktx

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.scale
import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.BarmanCodeFormat
import kotlinx.coroutines.*

/**
 * Shows a barcode bitmap representing the [content] encoded with the [format].
 * If width and/or height stored in [dimensions] are less than or equal to 0,
 * then they will take the corresponding dimension of the current [ImageView].
 * Barcode generation process run in the main thread.
 *
 * @receiver an instance of [ImageView]
 * @param content The content to encode in the barcode
 * @param format The barcode format to generate
 * @param dimensions The preferred dimensions of the bitmap
 */
fun ImageView.loadBarcode(
    content: String,
    format: BarmanCodeFormat,
    dimensions: Barman.Dimensions = Barman.Dimensions(0, 0)
) {
    val barman = Barman()
    val bmpWidth = if (dimensions.width > 0) dimensions.width else this.width
    val bmpHeight = if (dimensions.height > 0) dimensions.height else this.height
    val bitmap = barman.generateBitmap(content, format, Barman.Dimensions(bmpWidth, bmpHeight))
    setImageBitmap(scaleBitmap(bitmap, this.width, this.height))
}

/**
 * Same as [loadBarcode] but executed in background using a coroutine.
 * If width and/or height stored in [dimensions] are less than or equal to 0,
 * then they will take the corresponding dimensions of the current [ImageView].
 *
 * @receiver an instance of [ImageView]
 * @param content The content to encode in the barcode
 * @param format The barcode format to generate
 * @param dimensions The preferred dimensions of the bitmap
 * @param dispatcher The coroutine dispatcher used to execute the barcode bitmap generation process
 * @return [Job] used in the coroutine that can be use to cancel it
 */
fun ImageView.loadBarcodeAsync(
    content: String,
    format: BarmanCodeFormat,
    dimensions: Barman.Dimensions = Barman.Dimensions(0, 0),
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    onFinished: (() -> Unit)? = null
): Job {
    val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val barman = Barman()
    val bmpWidth = if (dimensions.width > 0) dimensions.width else this.width
    val bmpHeight = if (dimensions.height > 0) dimensions.height else this.height
    return coroutineScope.launch {
        val resultBmp = withContext(dispatcher) {
            val bitmap = barman.generateBitmap(content, format, Barman.Dimensions(bmpWidth, bmpHeight))
            scaleBitmap(bitmap, this@loadBarcodeAsync.width, this@loadBarcodeAsync.height)
        }
        setImageBitmap(resultBmp)
        onFinished?.invoke()
    }
}

private fun scaleBitmap(srcBitmap: Bitmap, targetW: Int, targetH: Int): Bitmap {
    if (targetW <= 0 && targetH <= 0) {
        Log.w(
            "Barman", "Target width and height must be greater than 0. " +
                    "Cannot scale encoded barcode bitmap. " +
                    "The original bitmap with width ${srcBitmap.width} and height ${srcBitmap.height} will be displayed into the ImageView."
        )
        return srcBitmap
    }
    val inSampleSize = calculateInSampleSize(srcBitmap.width, srcBitmap.height, targetW, targetH)
    return srcBitmap.scale(srcBitmap.width / inSampleSize, srcBitmap.height / inSampleSize)
}

private fun calculateInSampleSize(
    srcWidth: Int,
    srcHeight: Int,
    reqWidth: Int,
    reqHeight: Int
): Int {
    // Raw height and width of image
    var inSampleSize = 1

    if (srcHeight > reqHeight || srcWidth > reqWidth) {

        val halfHeight: Int = srcHeight / 2
        val halfWidth: Int = srcWidth / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    Log.d("Barman", "in sample size: $inSampleSize")

    return inSampleSize
}