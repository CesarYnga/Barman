package com.cesarynga.barman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.BarmanCodeFormat
import com.cesarynga.barman.generator.ktx.loadBarcode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val barmanGenerator = BarmanGenerator()
//        val bitmap = barmanGenerator.encodeBitmap("Barcode507072", BarcodeFormat.CODE_128, 1200, 425)

        edtContent.setText("Barcode507072")

        btnGenerateBitmap.setOnClickListener {
            imgBarcode.loadBarcode(edtContent.text.toString(), BarmanCodeFormat.CODE_128)
        }
    }
}
