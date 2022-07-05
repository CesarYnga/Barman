package com.cesarynga.barman.generator

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.*

/**
 * Barcode bitmaps generation
 */
class Barman {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private fun createBitmap(bitmapMatrix: BitMatrix): Bitmap {
        val width = bitmapMatrix.width
        val height = bitmapMatrix.height
        val pixels = IntArray(width * height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (i in 0 until height) {
            val offset = i * width
            for (j in 0 until width) {
                pixels[offset + j] = if (bitmapMatrix[j, i]) Color.BLACK else Color.WHITE
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    private fun encode(
        content: String,
        format: BarmanCodeFormat,
        width: Int,
        height: Int
    ): BitMatrix {
        return MultiFormatWriter().encode(content, format.barcodeFormat, width, height)
    }

    /**
     * Generate a bitmap representing the [content] encoded with the [format].
     *
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param dimensions The preferred dimensions of the bitmap
     * @return [Bitmap] representing encoded barcode image
     */
    fun generateBitmap(content: String, format: BarmanCodeFormat, dimensions: Dimensions = Dimensions(0, 0),): Bitmap {
        val bitMatrix = encode(content, format, dimensions.width, dimensions.height)
        return createBitmap(bitMatrix)
    }

    /**
     * Same as [generateBitmap] but executed in a background thread using a coroutine.
     *
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param dimensions The preferred dimensions of the bitmap
     * @param dispatcher The coroutine dispatcher used to execute the barcode bitmap generation process
     * @param block Block to be executed after bitmap decoding finished and pass the resulting bitmap as its param
     * @return [Job] used in the coroutine that can be use to cancel it
     */
    fun generateBitmapAsync(
        content: String,
        format: BarmanCodeFormat,
        dimensions: Dimensions = Dimensions(0, 0),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: (Bitmap) -> Unit
    ) = coroutineScope.launch {
        val bitmap = withContext(dispatcher) {
            generateBitmap(content, format, dimensions)
        }
        block(bitmap)
    }

    /**
     * Width and height in pixels for the barcode bitmap
     */
    data class Dimensions(val width: Int, val height: Int)
}