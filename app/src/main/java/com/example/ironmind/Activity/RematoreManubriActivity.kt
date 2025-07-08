package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class RematoreManubriActivity: AppCompatActivity() {

    private lateinit var videoRematoreManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rematore_manubri)

        val toolbarRematoreManubri = findViewById<Toolbar>(R.id.toolbar_rematore_manubri)
        setSupportActionBar(toolbarRematoreManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Rematore Con Manubri"

            toolbarRematoreManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoRematoreManubriView = findViewById(R.id.Rematore_Con_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.rematore_con_manubri}")
        videoRematoreManubriView.setVideoURI(videoUri)

        videoRematoreManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoRematoreManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoRematoreManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoRematoreManubriView.start()
    }
}