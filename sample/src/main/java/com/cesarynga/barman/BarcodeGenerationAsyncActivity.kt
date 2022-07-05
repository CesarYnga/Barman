package com.cesarynga.barman

import com.cesarynga.barman.generator.Barman
import kotlinx.coroutines.Job

class BarcodeGenerationAsyncActivity : BarcodeGenerationActivity() {

    override fun onGenerateBarcodeButtonClick(): Job {
        onBarcodeGenerationStart()
        val barman = Barman()
        return barman.generateBitmapAsync(
            contentText(),
            barcodeFormat(),
            Barman.Dimensions(1200, 425)
        ) {
            setBarcodeBitmap(it)
            onBarcodeGenerationStart()
        }
    }
}
