package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class AffondiManubriActivity: AppCompatActivity() {

    private lateinit var videoAffondiManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affondi_manubri)

        val toolbarAffondiManubri = findViewById<Toolbar>(R.id.toolbar_affondi_manubri)
        setSupportActionBar(toolbarAffondiManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Affondi Manubri"

            toolbarAffondiManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoAffondiManubriView = findViewById(R.id.Affondi_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.affondi_manubri}")
        videoAffondiManubriView.setVideoURI(videoUri)

        // Loop del video
        videoAffondiManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoAffondiManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoAffondiManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoAffondiManubriView.start()
    }
}