package com.example.ironmind.main

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ShoulderPressActivity: AppCompatActivity() {

    private lateinit var videoShoulderPressView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoulder_press)

        val toolbarShoulderPress = findViewById<Toolbar>(R.id.toolbar_shoulder_press)
        setSupportActionBar(toolbarShoulderPress)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Shoulder Press"

            toolbarShoulderPress.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Configura VideoView con loop
        videoShoulderPressView = findViewById(R.id.Shoulder_Press_Video)
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.shoulder_press}") //Dice al VideoView di usare quel video (indicato da videoUri) come sorgente da riprodurre.
        videoShoulderPressView.setVideoURI(videoUri)

        // Loop del video
        videoShoulderPressView.setOnPreparedListener { mediaPlayer -> //Imposta un listener che viene chiamato quando il video è pronto per partire (OnPreparedListener).
            mediaPlayer.isLooping = true //mediaPlayer.isLooping = true abilita la riproduzione in loop del video, cioè appena finisce ricomincia da capo automaticamente.
            videoShoulderPressView.start() //videoShoulderPressView.start() fa partire la riproduzione del video.
        }
    }

    override fun onPause() { //Viene chiamato quando l’Activity sta per entrare in pausa
        super.onPause() //chiama il metodo della superclasse per mantenere il comportamento standard.
        videoShoulderPressView.pause() //videoShoulderPressView.pause() mette in pausa la riproduzione del video: il video si ferma, ma mantiene la posizione corrente.
    }

    override fun onResume() { //Viene chiamato quando l’Activity torna in primo piano, pronta a interagire con l’utente.
        super.onResume()
        videoShoulderPressView.start() //videoShoulderPressView.start() riprende (o avvia) la riproduzione del video dal punto in cui era stata messa in pausa (o dall’inizio se era fermo).
    }
}