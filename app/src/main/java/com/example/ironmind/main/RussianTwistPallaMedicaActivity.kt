package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class RussianTwistPallaMedicaActivity: AppCompatActivity() {

    private lateinit var videoRussianTwistPallaMedicaView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_russian_twist_palla_medica)

        val toolbarRussianTwistPallaMedica = findViewById<Toolbar>(R.id.toolbar_russian_twist_palla_medica)
        setSupportActionBar(toolbarRussianTwistPallaMedica)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Russian Twist Palla Medica"

            toolbarRussianTwistPallaMedica.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoRussianTwistPallaMedicaView = findViewById(R.id.Russian_Twist_Palla_Medica_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.russian_twist_palla_medica}")
        videoRussianTwistPallaMedicaView.setVideoURI(videoUri)

        videoRussianTwistPallaMedicaView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoRussianTwistPallaMedicaView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoRussianTwistPallaMedicaView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoRussianTwistPallaMedicaView.start()
    }
}