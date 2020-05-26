package com.cesarynga.barman

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.generator.Barman
import com.cesarynga.barman.generator.BarmanCodeFormat
import com.cesarynga.barman.generator.ktx.loadBarcodeAsync
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtContent.setText("Barcode507072")

//        val barman = Barman()
//        val bitmap = barman.generateBitmap("Barcode507072", BarmanCodeFormat.CODE_128, 1200, 425)
//        btnGenerateBitmap.setOnClickListener {
//            progressBar.visibility = View.VISIBLE
//            job = barman.generateBitmapAsync(
//                edtContent.text.toString(),
//                BarmanCodeFormat.CODE_128,
//                1200,
//                425
//            ) {
//                imgBarcode.setImageBitmap(it)
//                progressBar.visibility = View.GONE
//            }
//        }

        btnGenerateBitmap.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            job = imgBarcode.loadBarcodeAsync(edtContent.text.toString(), BarmanCodeFormat.QR_CODE) {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
