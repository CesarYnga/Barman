package com.cesarynga.barman

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.BarmanCodeFormat
import com.cesarynga.barman.generator.ktx.BarmanDimensions
import com.cesarynga.barman.generator.ktx.loadBarcode
import kotlinx.android.synthetic.main.activity_barcode_generation.*

class BarcodeGenerationSyncKtxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_generation)

        spnFormats.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BarmanCodeFormat.values()
        )

        btnGenerateBitmap.setOnClickListener {
            imgBarcode.loadBarcode(
                edtContent.text.toString(),
                spnFormats.selectedItem as BarmanCodeFormat,
                BarmanDimensions(1200, 425)
            )
        }
    }
}
