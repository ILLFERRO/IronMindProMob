package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class DipParalleleAssistitiActivity: AppCompatActivity() {

    private lateinit var videoDipParalleleAssistitiView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dip_parallele_assistiti)

        val toolbarDipParalleleAssistiti = findViewById<Toolbar>(R.id.toolbar_dip_parallele_assistiti)
        setSupportActionBar(toolbarDipParalleleAssistiti)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Dip Alle Parallele Assistiti"

            toolbarDipParalleleAssistiti.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoDipParalleleAssistitiView = findViewById(R.id.Dip_Alle_Parallele_Assistiti_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.dip_parallele_assistiti}")
        videoDipParalleleAssistitiView.setVideoURI(videoUri)

        videoDipParalleleAssistitiView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoDipParalleleAssistitiView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoDipParalleleAssistitiView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoDipParalleleAssistitiView.start()
    }
}