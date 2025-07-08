package com.example.ironmind.Utils

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class SuonaAlTermineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suona_al_termine)

        val toolbarSuoneriaAttiva = findViewById<Toolbar>(R.id.toolbar_suona_al_termine)
        setSupportActionBar(toolbarSuoneriaAttiva)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Suona Al Termine"
        }

        toolbarSuoneriaAttiva.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSi = findViewById<Button>(R.id.btn_suona)
        val btnNo = findViewById<Button>(R.id.btn_non_suona)

        val preferenzeSuoneria = getSharedPreferences("settings", MODE_PRIVATE)

        btnSi.setOnClickListener {
            preferenzeSuoneria.edit().putBoolean("suoneria_attiva", true).apply()
            onBackPressedDispatcher.onBackPressed()
        }

        btnNo.setOnClickListener {
            preferenzeSuoneria.edit().putBoolean("suoneria_attiva", false).apply()
            onBackPressedDispatcher.onBackPressed()
        }
    }
}