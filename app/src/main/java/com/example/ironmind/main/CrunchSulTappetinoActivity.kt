package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CrunchSulTappetinoActivity: AppCompatActivity() {

    private lateinit var videoCrunchSulTappetinoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crunch_sul_tappetino)

        val toolbarCrunchSulTappetino = findViewById<Toolbar>(R.id.toolbar_crunch_sul_tappetino)
        setSupportActionBar(toolbarCrunchSulTappetino)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Crunch Sul Tappetino"

            toolbarCrunchSulTappetino.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCrunchSulTappetinoView = findViewById(R.id.Crunch_Sul_Tappetino_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.crunch_sul_tappetino}")
        videoCrunchSulTappetinoView.setVideoURI(videoUri)

        // Loop del video
        videoCrunchSulTappetinoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCrunchSulTappetinoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCrunchSulTappetinoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCrunchSulTappetinoView.start()
    }
}