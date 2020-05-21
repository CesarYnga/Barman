package com.cesarynga.barman.generator

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

/**
 * Barcode bitmaps generation
 */
class Barman {

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

    private fun encode(content: String, format: BarmanCodeFormat, width: Int, height: Int): BitMatrix {
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
     * Generate a square bitmap representing the [content] encoded with the [format].
     * @param content The content to encode in the barcode
     * @param format The barcode format to generate
     * @param size The preferred width and height of the bitmap in pixels
     * @return [Bitmap] representing encoded barcode image
     */
    fun generateBitmap(content: String, format: BarmanCodeFormat, size: Int): Bitmap {
        return generateBitmap(content, format, size, size)
    }
}