package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CurlConcentratoManubrioActivity: AppCompatActivity() {

    private lateinit var videoCurlConcentratoManubrioView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curl_concentrato_manubrio)

        val toolbarCurlConcentratoManubrio = findViewById<Toolbar>(R.id.toolbar_curl_concentrato_manubrio)
        setSupportActionBar(toolbarCurlConcentratoManubrio)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Curl Concentrato Con Manubrio"

            toolbarCurlConcentratoManubrio.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoCurlConcentratoManubrioView = findViewById(R.id.Curl_Concentrato_Con_Manubrio_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.curl_concentrato_manubrio}")
        videoCurlConcentratoManubrioView.setVideoURI(videoUri)

        videoCurlConcentratoManubrioView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCurlConcentratoManubrioView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCurlConcentratoManubrioView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCurlConcentratoManubrioView.start()
    }
}