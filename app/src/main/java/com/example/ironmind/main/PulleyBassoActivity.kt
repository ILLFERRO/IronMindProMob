package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class PulleyBassoActivity: AppCompatActivity() {

    private lateinit var videoPulleyBassoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulley_basso)

        val toolbarPulleyBasso = findViewById<Toolbar>(R.id.toolbar_pulley_basso)
        setSupportActionBar(toolbarPulleyBasso)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Pulley Basso"

            toolbarPulleyBasso.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoPulleyBassoView = findViewById(R.id.Pulley_Basso_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.pulley_basso}")
        videoPulleyBassoView.setVideoURI(videoUri)

        videoPulleyBassoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoPulleyBassoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPulleyBassoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPulleyBassoView.start()
    }
}