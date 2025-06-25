package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class SquatBilanciereActivity: AppCompatActivity() {

    private lateinit var videoSquatBilanciereView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squat_bilanciere)

        val toolbarSquatBilanciere = findViewById<Toolbar>(R.id.toolbar_squat_bilanciere)
        setSupportActionBar(toolbarSquatBilanciere)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Squat Con Bilanciere"

            toolbarSquatBilanciere.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoSquatBilanciereView = findViewById(R.id.Squat_Bilanciere_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.squat_con_bilanciere}")
        videoSquatBilanciereView.setVideoURI(videoUri)

        // Loop del video
        videoSquatBilanciereView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoSquatBilanciereView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoSquatBilanciereView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoSquatBilanciereView.start()
    }
}