package com.cesarynga.barman

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.databinding.ActivityBarcodeGenerationBinding
import com.cesarynga.barman.generator.BarmanCodeFormat
import kotlinx.coroutines.Job

abstract class BarcodeGenerationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBarcodeGenerationBinding
    private var job: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            spnFormats.adapter = ArrayAdapter(
                this@BarcodeGenerationActivity,
                android.R.layout.simple_list_item_1,
                BarmanCodeFormat.values()
            )

            btnGenerateBitmap.setOnClickListener {
                job = onGenerateBarcodeButtonClick()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    protected fun contentText() = binding.edtContent.text.toString()

    protected fun barcodeFormat() = binding.spnFormats.selectedItem as BarmanCodeFormat

    protected val barcodeImageView: ImageView by lazy { binding.imgBarcode }

    fun setBarcodeBitmap(bitmap: Bitmap) {
        binding.imgBarcode.setImageBitmap(bitmap)
    }

    fun onBarcodeGenerationStart() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun onBarcodeGenerationFinished() {
        binding.progressBar.visibility = View.GONE
    }

    abstract fun onGenerateBarcodeButtonClick(): Job
}