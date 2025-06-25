package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class StaccoDaTerraBilanciereActivity: AppCompatActivity() {

    private lateinit var videoStaccoDaTerraBilanciereView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stacco_da_terra_bilanciere)

        val toolbarStaccoDaTerraBilanciere = findViewById<Toolbar>(R.id.toolbar_stacco_terra_bilanciere)
        setSupportActionBar(toolbarStaccoDaTerraBilanciere)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Stacco Da Terra Con Bilanciere"

            toolbarStaccoDaTerraBilanciere.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoStaccoDaTerraBilanciereView = findViewById(R.id.Stacco_Da_Terra_Con_Bilanciere_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.stacco_da_terra_con_bilanciere}")
        videoStaccoDaTerraBilanciereView.setVideoURI(videoUri)

        // Loop del video
        videoStaccoDaTerraBilanciereView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoStaccoDaTerraBilanciereView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoStaccoDaTerraBilanciereView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoStaccoDaTerraBilanciereView.start()
    }
}