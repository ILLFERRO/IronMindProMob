package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ChestPressActivity : AppCompatActivity() {

    private lateinit var videoChestPressView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chest_press)

        val toolbarChestPress = findViewById<Toolbar>(R.id.toolbar_chest_press)
        setSupportActionBar(toolbarChestPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Chest Press"

            toolbarChestPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoChestPressView = findViewById(R.id.Chest_Press_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.chest_press}")
        videoChestPressView.setVideoURI(videoUri)

        videoChestPressView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoChestPressView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoChestPressView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoChestPressView.start()
    }
}