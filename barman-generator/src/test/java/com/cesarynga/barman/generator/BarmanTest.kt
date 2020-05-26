package com.cesarynga.barman.generator

import android.os.Build
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

const val fakeContent = "fake content"
const val fakeSize = 400

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class BarmanTest {

    private lateinit var barman: Barman

    @Before
    fun setUp() {
        barman = Barman()
    }

    @Test
    fun `barcode image has requested size`() {
        val bitmap = barman.generateBitmap(fakeContent, BarmanCodeFormat.QR_CODE, fakeSize, fakeSize)
        assertEquals(fakeSize, bitmap.width)
        assertEquals(fakeSize, bitmap.height)
    }
}