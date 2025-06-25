package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class MacchinaCrunchCaricoPiastraActivity: AppCompatActivity() {

    private lateinit var videoMacchinaCrunchPiastraView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_macchina_crunch_carico_piastra)

        val toolbarMacchinaCrunchPiastra = findViewById<Toolbar>(R.id.toolbar_macchina_crunch_carico_piastra)
        setSupportActionBar(toolbarMacchinaCrunchPiastra)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Macchina Crunch A Piastra"

            toolbarMacchinaCrunchPiastra.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoMacchinaCrunchPiastraView = findViewById(R.id.Macchina_Crunch_Con_Carico_A_Piastra_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.macchina_crunch_carico_piastra}")
        videoMacchinaCrunchPiastraView.setVideoURI(videoUri)

        // Loop del video
        videoMacchinaCrunchPiastraView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoMacchinaCrunchPiastraView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoMacchinaCrunchPiastraView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoMacchinaCrunchPiastraView.start()
    }
}