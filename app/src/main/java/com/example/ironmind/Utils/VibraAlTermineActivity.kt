package com.example.ironmind.Utils

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class VibraAlTermineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vibra_al_termine)

        val toolbarVibrazioneAttiva = findViewById<Toolbar>(R.id.toolbar_vibra_al_termine)
        setSupportActionBar(toolbarVibrazioneAttiva)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Vibra Al Termine"
        }

        toolbarVibrazioneAttiva.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSi = findViewById<Button>(R.id.btn_vibra)
        val btnNo = findViewById<Button>(R.id.btn_non_vibra)

        val preferenzeVibrazione = getSharedPreferences("settings", MODE_PRIVATE)

        btnSi.setOnClickListener {
            preferenzeVibrazione.edit().putBoolean("vibrazione_attiva", true).apply()
            onBackPressedDispatcher.onBackPressed()
        }

        btnNo.setOnClickListener {
            preferenzeVibrazione.edit().putBoolean("vibrazione_attiva", false).apply()
            onBackPressedDispatcher.onBackPressed()
        }
    }
}