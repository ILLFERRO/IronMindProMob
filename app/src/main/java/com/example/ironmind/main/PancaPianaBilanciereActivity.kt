package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class PancaPianaBilanciereActivity: AppCompatActivity() {

    private lateinit var videoPancaPianaBilanciereView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panca_piana_bilanciere)

        val toolbarPancaPianaBilanciere = findViewById<Toolbar>(R.id.toolbar_panca_piana_bilanciere)
        setSupportActionBar(toolbarPancaPianaBilanciere)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Panca Piana Con Bilanciere"

            toolbarPancaPianaBilanciere.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoPancaPianaBilanciereView = findViewById(R.id.Panca_Piana_Con_Bilanciere_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.panca_piana_bilanciere}")
        videoPancaPianaBilanciereView.setVideoURI(videoUri)

        videoPancaPianaBilanciereView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoPancaPianaBilanciereView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPancaPianaBilanciereView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPancaPianaBilanciereView.start()
    }
}