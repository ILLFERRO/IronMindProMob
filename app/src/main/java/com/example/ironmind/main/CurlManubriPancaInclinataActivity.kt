package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CurlManubriPancaInclinataActivity: AppCompatActivity() {

    private lateinit var videoCurlManubriPancaInclinataView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curl_manubri_panca_inclinata)

        val toolbarCurlManubriPancaInclinata = findViewById<Toolbar>(R.id.toolbar_curl_manubri_panca_inclinata)
        setSupportActionBar(toolbarCurlManubriPancaInclinata)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Curl Con Manubri Su Panca Inclinata"

            toolbarCurlManubriPancaInclinata.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCurlManubriPancaInclinataView = findViewById(R.id.Curl_Con_Manubri_Su_Panca_Inclinata_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.curl_panca_inclinata}")
        videoCurlManubriPancaInclinataView.setVideoURI(videoUri)

        // Loop del video
        videoCurlManubriPancaInclinataView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCurlManubriPancaInclinataView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCurlManubriPancaInclinataView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCurlManubriPancaInclinataView.start()
    }
}