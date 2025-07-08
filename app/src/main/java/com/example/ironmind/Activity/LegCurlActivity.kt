package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class LegCurlActivity: AppCompatActivity() {

    private lateinit var videoLegCurlView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leg_curl)

        val toolbarLegCurl = findViewById<Toolbar>(R.id.toolbar_leg_curl)
        setSupportActionBar(toolbarLegCurl)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Leg Curl"

            toolbarLegCurl.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoLegCurlView = findViewById(R.id.Leg_Curl_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.leg_curl}")
        videoLegCurlView.setVideoURI(videoUri)

        videoLegCurlView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoLegCurlView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoLegCurlView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoLegCurlView.start()
    }
}