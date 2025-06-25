package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class LegPressActivity: AppCompatActivity() {

    private lateinit var videoLegPressView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leg_press)

        val toolbarLegPress = findViewById<Toolbar>(R.id.toolbar_leg_press)
        setSupportActionBar(toolbarLegPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Leg Press"

            toolbarLegPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoLegPressView = findViewById(R.id.Leg_Press_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.leg_press}")
        videoLegPressView.setVideoURI(videoUri)

        // Loop del video
        videoLegPressView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoLegPressView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoLegPressView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoLegPressView.start()
    }
}