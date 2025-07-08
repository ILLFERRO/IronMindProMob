package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ShoulderPressActivity: AppCompatActivity() {

    private lateinit var videoShoulderPressView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoulder_press)

        val toolbarShoulderPress = findViewById<Toolbar>(R.id.toolbar_shoulder_press)
        setSupportActionBar(toolbarShoulderPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Shoulder Press"

            toolbarShoulderPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoShoulderPressView = findViewById(R.id.Shoulder_Press_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.shoulder_press}")
        videoShoulderPressView.setVideoURI(videoUri)

        videoShoulderPressView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoShoulderPressView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoShoulderPressView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoShoulderPressView.start()
    }
}