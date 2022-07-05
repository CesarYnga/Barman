package com.cesarynga.barman

import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.ktx.loadBarcodeAsync
import kotlinx.coroutines.Job

class BarcodeGenerationAsyncKtxActivity : BarcodeGenerationActivity() {

    override fun onGenerateBarcodeButtonClick(): Job {
        onBarcodeGenerationStart()
        return barcodeImageView.loadBarcodeAsync(
            contentText(),
            barcodeFormat(),
            Barman.Dimensions(1200, 425)
        ) {
            onBarcodeGenerationFinished()
        }
    }
}
