package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class CalfRaiseAllaLegPressActivity: AppCompatActivity() {

    private lateinit var videoCalfRaiseLegPressView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calf_raise_alla_leg_press)

        val toolbarCalfRaiseLegPress = findViewById<Toolbar>(R.id.toolbar_calf_raise_leg_press)
        setSupportActionBar(toolbarCalfRaiseLegPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Calf Raise Alla Leg Press"

            toolbarCalfRaiseLegPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoCalfRaiseLegPressView = findViewById(R.id.Calf_Raise_Leg_Press_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.calf_raise_leg_press}")
        videoCalfRaiseLegPressView.setVideoURI(videoUri)

        // Loop del video
        videoCalfRaiseLegPressView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoCalfRaiseLegPressView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoCalfRaiseLegPressView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoCalfRaiseLegPressView.start()
    }
}