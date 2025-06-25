package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CurlBilanciereEzActivity: AppCompatActivity() {

    private lateinit var videoCurlBilanciereEZView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curl_bilanciere_ezactivity)

        val toolbarCurlBilanciereEZ = findViewById<Toolbar>(R.id.toolbar_curl_bilanciere_ez)
        setSupportActionBar(toolbarCurlBilanciereEZ)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Curl Con Bilanciere EZ"

            toolbarCurlBilanciereEZ.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCurlBilanciereEZView = findViewById(R.id.Curl_Bilanciere_EZ_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.curl_bilanciere_ez}")
        videoCurlBilanciereEZView.setVideoURI(videoUri)

        // Loop del video
        videoCurlBilanciereEZView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCurlBilanciereEZView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCurlBilanciereEZView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCurlBilanciereEZView.start()
    }
}