package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class PancaPianaManubriActivity: AppCompatActivity() {

    private lateinit var videoPancaPianaManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panca_piana_manubri)

        val toolbarPancaPianaManubri = findViewById<Toolbar>(R.id.toolbar_panca_piana_manubri)
        setSupportActionBar(toolbarPancaPianaManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Panca Piana Con Manubri"

            toolbarPancaPianaManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoPancaPianaManubriView = findViewById(R.id.Panca_Piana_Con_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.panca_piana_con_manubri}")
        videoPancaPianaManubriView.setVideoURI(videoUri)

        videoPancaPianaManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoPancaPianaManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPancaPianaManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPancaPianaManubriView.start()
    }
}