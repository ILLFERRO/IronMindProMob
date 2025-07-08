package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class PlankIsometricoActivity: AppCompatActivity() {

    private lateinit var videoPlankIsometricoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plank_isometrico)

        val toolbarPlankIsometrico = findViewById<Toolbar>(R.id.toolbar_plank_isometrico)
        setSupportActionBar(toolbarPlankIsometrico)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Plank Isometrico"

            toolbarPlankIsometrico.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoPlankIsometricoView = findViewById(R.id.Plank_Isometrico_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.plank_isometrico}")
        videoPlankIsometricoView.setVideoURI(videoUri)

        videoPlankIsometricoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoPlankIsometricoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPlankIsometricoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPlankIsometricoView.start()
    }
}