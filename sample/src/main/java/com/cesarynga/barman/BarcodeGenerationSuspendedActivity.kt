package com.cesarynga.barman

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.BarmanCodeFormat
import kotlinx.android.synthetic.main.activity_barcode_generation.*
import kotlinx.coroutines.*

class BarcodeGenerationSuspendedActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_generation)

        spnFormats.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BarmanCodeFormat.values()
        )

        btnGenerateBitmap.setOnClickListener {
            coroutineScope.launch {
                progressBar.visibility = View.VISIBLE
                val barman = Barman()
                val bitmap = barman.generateBitmapSuspended(
                    edtContent.text.toString(),
                    spnFormats.selectedItem as BarmanCodeFormat,
                    1200,
                    425
                )
                imgBarcode.setImageBitmap(bitmap)
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
