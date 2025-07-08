package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class TirataStrettaActivity: AppCompatActivity() {

    private lateinit var videoTirataStrettaView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tirata_stretta)

        val toolbarTirataStretta = findViewById<Toolbar>(R.id.toolbar_tirata_stretta)
        setSupportActionBar(toolbarTirataStretta)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tirata Stretta"

            toolbarTirataStretta.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoTirataStrettaView = findViewById(R.id.Tirata_Stretta_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.tirata_stretta}")
        videoTirataStrettaView.setVideoURI(videoUri)

        videoTirataStrettaView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoTirataStrettaView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoTirataStrettaView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoTirataStrettaView.start()
    }
}