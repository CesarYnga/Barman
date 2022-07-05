package com.cesarynga.barman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cesarynga.barman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            lstSampleTypes.setOnItemClickListener { _, _, position, _ ->
                val cls = when (position) {
                    0 -> BarcodeGenerationSyncActivity::class.java
                    1 -> BarcodeGenerationAsyncActivity::class.java
                    2 -> BarcodeGenerationAsyncKtxActivity::class.java
                    else -> null
                }

                cls?.let {
                    startActivity(Intent(this@MainActivity, it))
                }
            }
        }
    }
}
