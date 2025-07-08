package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class EstensioniTricipitiManubrioActivity: AppCompatActivity() {

    private lateinit var videoEstensioniTricipitiManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estensioni_tricipiti_manubrio)

        val toolbarEstensioniTricipitiManubri = findViewById<Toolbar>(R.id.toolbar_estensioni_tricipiti_manubri)
        setSupportActionBar(toolbarEstensioniTricipitiManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Estensioni Tricipiti Manubrio"

            toolbarEstensioniTricipitiManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoEstensioniTricipitiManubriView = findViewById(R.id.Estensioni_Tricipiti_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.estensioni_tricipiti_manubrio}")
        videoEstensioniTricipitiManubriView.setVideoURI(videoUri)

        videoEstensioniTricipitiManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoEstensioniTricipitiManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoEstensioniTricipitiManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoEstensioniTricipitiManubriView.start()
    }
}