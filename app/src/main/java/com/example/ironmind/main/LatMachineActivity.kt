package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class LatMachineActivity: AppCompatActivity() {

    private lateinit var videoLatMachineView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lat_machine)

        val toolbarLatMachine = findViewById<Toolbar>(R.id.toolbar_lat_machine)
        setSupportActionBar(toolbarLatMachine)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Lat Machine"

            toolbarLatMachine.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoLatMachineView = findViewById(R.id.Lat_Machine_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.lat_machine}")
        videoLatMachineView.setVideoURI(videoUri)

        videoLatMachineView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoLatMachineView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoLatMachineView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoLatMachineView.start()
    }
}