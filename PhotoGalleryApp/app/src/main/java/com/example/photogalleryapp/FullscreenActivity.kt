package com.example.photogalleryapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imageView = findViewById<ImageView>(R.id.fullscreenImage)
        val resId = intent.getIntExtra("image_res", R.drawable.photo1)

        imageView.setImageResource(resId)

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }
}