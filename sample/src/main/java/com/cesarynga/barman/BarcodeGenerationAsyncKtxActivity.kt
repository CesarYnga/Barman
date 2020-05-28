package com.cesarynga.barman

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.BarmanCodeFormat
import com.cesarynga.barman.generator.ktx.BarmanDimensions
import com.cesarynga.barman.generator.ktx.loadBarcodeAsync
import kotlinx.android.synthetic.main.activity_barcode_generation.*
import kotlinx.coroutines.Job

class BarcodeGenerationAsyncKtxActivity : AppCompatActivity() {

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
            progressBar.visibility = View.VISIBLE
            val barman = Barman()
            job = imgBarcode.loadBarcodeAsync(
                edtContent.text.toString(),
                spnFormats.selectedItem as BarmanCodeFormat,
                BarmanDimensions(1200, 425)
            ) {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
