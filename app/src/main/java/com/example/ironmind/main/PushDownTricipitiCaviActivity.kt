package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class PushDownTricipitiCaviActivity: AppCompatActivity() {

    private lateinit var videoPushDownTricipitiCaviView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_down_tricipiti_cavi)

        val toolbarPushDownTricipitiCavi = findViewById<Toolbar>(R.id.toolbar_pushdown_tricipiti_cavi)
        setSupportActionBar(toolbarPushDownTricipitiCavi)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "PushDown Tricipiti Cavi"

            toolbarPushDownTricipitiCavi.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        videoPushDownTricipitiCaviView = findViewById(R.id.PushDown_Tricipiti_Cavi_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.pushdown_tricipiti_cavi}")
        videoPushDownTricipitiCaviView.setVideoURI(videoUri)

        videoPushDownTricipitiCaviView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoPushDownTricipitiCaviView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPushDownTricipitiCaviView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPushDownTricipitiCaviView.start()
    }
}