package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class RematoreBilanciereActivity: AppCompatActivity() {

    private lateinit var videoRematoreBilanciereView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rematore_bilanciere)

        val toolbarRematoreBilanciere = findViewById<Toolbar>(R.id.toolbar_rematore_bilanciere)
        setSupportActionBar(toolbarRematoreBilanciere)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Rematore Con Bilanciere"

            toolbarRematoreBilanciere.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoRematoreBilanciereView = findViewById(R.id.Rematore_Con_Bilanciere_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.rematore_bilanciere}")
        videoRematoreBilanciereView.setVideoURI(videoUri)

        videoRematoreBilanciereView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoRematoreBilanciereView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoRematoreBilanciereView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoRematoreBilanciereView.start()
    }
}