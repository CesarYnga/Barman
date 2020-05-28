package com.cesarynga.barman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lstSampleTypes.setOnItemClickListener { _, _, position, _ ->
            val cls = when(position) {
                0 -> BarcodeGenerationSyncActivity::class.java
                1 -> BarcodeGenerationSyncKtxActivity::class.java
                2 -> BarcodeGenerationAsyncActivity::class.java
                3 -> BarcodeGenerationAsyncKtxActivity::class.java
                4 -> BarcodeGenerationSuspendedActivity::class.java
                else -> null
            }

            cls?.let {
                startActivity(Intent(this, it))
            }
        }
    }
}
