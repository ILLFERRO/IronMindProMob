package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CurlManubriActivity: AppCompatActivity() {

    private lateinit var videoCurlManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curl_manubri)

        val toolbarCurlManubri = findViewById<Toolbar>(R.id.toolbar_curl_manubri)
        setSupportActionBar(toolbarCurlManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Curl Manubri"

            toolbarCurlManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoCurlManubriView = findViewById(R.id.Curl_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.curl_manubri}")
        videoCurlManubriView.setVideoURI(videoUri)

        videoCurlManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCurlManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCurlManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCurlManubriView.start()
    }
}