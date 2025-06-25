package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CrociManubriPancaActivity: AppCompatActivity() {

    private lateinit var videoCrociManubriPancaView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_croci_manubri_panca)

        val toolbarCrociManubriPanca = findViewById<Toolbar>(R.id.toolbar_croci_manubri_panca)
        setSupportActionBar(toolbarCrociManubriPanca)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Croci Con Manubri Su Panca"

            toolbarCrociManubriPanca.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCrociManubriPancaView = findViewById(R.id.Croci_Manubri_Panca_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.croci_panca}")
        videoCrociManubriPancaView.setVideoURI(videoUri)

        // Loop del video
        videoCrociManubriPancaView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCrociManubriPancaView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCrociManubriPancaView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCrociManubriPancaView.start()
    }
}