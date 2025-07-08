package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class LegCurlSdraiatoActivity: AppCompatActivity() {

    private lateinit var videoLegCurlSdraiatoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leg_curl_sdraiato)

        val toolbarLegCurlSdraiato = findViewById<Toolbar>(R.id.toolbar_leg_curl_sdraiato)
        setSupportActionBar(toolbarLegCurlSdraiato)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Leg Curl Da Sdraiato"

            toolbarLegCurlSdraiato.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoLegCurlSdraiatoView = findViewById(R.id.Leg_Curl_Da_Sdraiato_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.leg_curl_sdraiato}")
        videoLegCurlSdraiatoView.setVideoURI(videoUri)

        videoLegCurlSdraiatoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoLegCurlSdraiatoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoLegCurlSdraiatoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoLegCurlSdraiatoView.start()
    }
}