package com.example.ironmind.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class LatMachinePresaInversaActivity: AppCompatActivity() {

    private lateinit var videoLatMachinePresaInversaView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lat_machine_presa_inversa)

        val toolbarLatMachinePresaInversa = findViewById<Toolbar>(R.id.toolbar_lat_machine_presa_inversa)
        setSupportActionBar(toolbarLatMachinePresaInversa)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Lat Machine Presa Inversa"

            toolbarLatMachinePresaInversa.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoLatMachinePresaInversaView = findViewById(R.id.Lat_Machine_Presa_Inversa_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.lat_machine_presa_inversa}")
        videoLatMachinePresaInversaView.setVideoURI(videoUri)

        videoLatMachinePresaInversaView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoLatMachinePresaInversaView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoLatMachinePresaInversaView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoLatMachinePresaInversaView.start()
    }
}