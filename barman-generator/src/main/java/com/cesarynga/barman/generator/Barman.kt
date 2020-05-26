package com.cesarynga.barman.generator

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

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
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param width The preferred width of the bitmap in pixels
     * @param height The preferred height of the bitmap in pixels
     * @return [Bitmap] representing encoded barcode image
     */
    fun generateBitmap(content: String, format: BarmanCodeFormat, width: Int, height: Int): Bitmap {
        val bitMatrix = encode(content, format, width, height)
        return createBitmap(bitMatrix)
    }

    /**
     * Suspend function that works the same as [generateBitmap].
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param width The preferred width of the bitmap in pixels
     * @param height The preferred height of the bitmap in pixels
     * @param dispatcher The coroutine dispatcher used to execute the barcode bitmap generation process
     * @return [Bitmap] representing encoded barcode image
     */
    suspend fun generateBitmapSuspended(
        content: String,
        format: BarmanCodeFormat,
        width: Int,
        height: Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Bitmap = withContext(dispatcher) {
        generateBitmap(content, format, width, height)
    }

    /**
     * Same as [generateBitmap] but executed in a background thread using a coroutine.
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param width The preferred width of the bitmap in pixels
     * @param height The preferred height of the bitmap in pixels
     * @param block Block to be executed after bitmap decoding finished and pass the resulting bitmap as its param
     * @return [Job] used in the coroutine that can be use to cancel it
     */
    fun generateBitmapAsync(
        content: String,
        format: BarmanCodeFormat,
        width: Int,
        height: Int,
        block: (Bitmap) -> Unit
    ) = coroutineScope.launch {
        val bitmap = generateBitmapSuspended(content, format, width, height)
        block(bitmap)
    }
}