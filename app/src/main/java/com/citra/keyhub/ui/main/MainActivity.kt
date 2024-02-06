package com.citra.keyhub.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.citra.keyhub.R
import com.citra.keyhub.databinding.ActivityMainBinding
import com.citra.keyhub.ui.detection.CameraActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openCamera.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java))
        }
    }
}