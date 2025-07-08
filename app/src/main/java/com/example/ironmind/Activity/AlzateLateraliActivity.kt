package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class AlzateLateraliActivity: AppCompatActivity() {

    private lateinit var videoAlzateLateraliView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alzate_laterali)

        val toolbarAlzateLaterali = findViewById<Toolbar>(R.id.toolbar_alzate_laterali)
        setSupportActionBar(toolbarAlzateLaterali)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Alzate Laterali"

            toolbarAlzateLaterali.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoAlzateLateraliView = findViewById(R.id.Alzate_Laterali_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.alzate_laterali_macchina}")
        videoAlzateLateraliView.setVideoURI(videoUri)

        // Loop del video
        videoAlzateLateraliView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoAlzateLateraliView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoAlzateLateraliView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoAlzateLateraliView.start()
    }
}