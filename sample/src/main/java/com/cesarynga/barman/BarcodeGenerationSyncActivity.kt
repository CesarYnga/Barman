package com.cesarynga.barman

import com.cesarynga.barman.generator.Barman
import kotlinx.coroutines.*

class BarcodeGenerationSyncActivity : BarcodeGenerationActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onGenerateBarcodeButtonClick(): Job {
        return coroutineScope.launch {
            val barman = Barman()
            val bitmap = withContext(Dispatchers.Default) {
                barman.generateBitmap(
                    contentText(),
                    barcodeFormat(),
                    Barman.Dimensions(1200, 425)
                )
            }
            setBarcodeBitmap(bitmap)
        }
    }
}
