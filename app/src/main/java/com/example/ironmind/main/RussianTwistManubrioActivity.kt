package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class RussianTwistManubrioActivity: AppCompatActivity() {

    private lateinit var videoRussianTwistManubriView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_russian_twist_manubrio)

        val toolbarRussianTwistManubri = findViewById<Toolbar>(R.id.toolbar_russian_twist_manubri)
        setSupportActionBar(toolbarRussianTwistManubri)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Russian Twist Con Manubri"

            toolbarRussianTwistManubri.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoRussianTwistManubriView = findViewById(R.id.Russian_Twist_Con_Manubri_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.russian_twist_con_manubrio}")
        videoRussianTwistManubriView.setVideoURI(videoUri)

        // Loop del video
        videoRussianTwistManubriView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoRussianTwistManubriView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoRussianTwistManubriView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoRussianTwistManubriView.start()
    }
}