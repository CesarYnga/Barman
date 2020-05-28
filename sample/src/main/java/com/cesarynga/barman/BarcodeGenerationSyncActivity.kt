package com.cesarynga.barman

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.BarmanCodeFormat
import kotlinx.android.synthetic.main.activity_barcode_generation.*

class BarcodeGenerationSyncActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_generation)

        spnFormats.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BarmanCodeFormat.values()
        )

        btnGenerateBitmap.setOnClickListener {
            val barman = Barman()
            val bitmap = barman.generateBitmap(
                edtContent.text.toString(),
                spnFormats.selectedItem as BarmanCodeFormat,
                1200,
                425
            )
            imgBarcode.setImageBitmap(bitmap)
        }
    }
}
