package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CycletteTapisRoulantActivity: AppCompatActivity() {

    private lateinit var videoCycletteView: VideoView
    private lateinit var videoTapisRoulantView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cyclette_tapis_roulant)

        val toolbarCycletteTapisRoulantPress = findViewById<Toolbar>(R.id.toolbar_cyclette_tapis_roulant)
        setSupportActionBar(toolbarCycletteTapisRoulantPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Cyclette/Tapis Rpulant"

            toolbarCycletteTapisRoulantPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCycletteView = findViewById(R.id.Cyclette_Video)
        val videoUriCyclette = Uri.parse("android.resource://${packageName}/${R.raw.cyclette}")
        videoCycletteView.setVideoURI(videoUriCyclette)

        // Configura VideoView con loop
        videoTapisRoulantView = findViewById(R.id.Tapis_Roulant_Video)
        val videoUriTapisRoulant = Uri.parse("android.resource://${packageName}/${R.raw.tapis_roulant}")
        videoTapisRoulantView.setVideoURI(videoUriTapisRoulant)

        // Loop del video
        videoCycletteView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCycletteView.start()
        }

        // Loop del video
        videoTapisRoulantView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoTapisRoulantView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCycletteView.pause()
        videoTapisRoulantView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCycletteView.start()
        videoTapisRoulantView.start()
    }
}