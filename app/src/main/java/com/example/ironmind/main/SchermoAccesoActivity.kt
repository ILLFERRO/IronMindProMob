package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class SchermoAccesoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schermo_acceso)

        val toolbarSchermoAcceso = findViewById<Toolbar>(R.id.toolbar_schermo_acceso)
        setSupportActionBar(toolbarSchermoAcceso)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tenere Schermo Acceso"
        }

        toolbarSchermoAcceso.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSi = findViewById<Button>(R.id.btn_si)
        val btnNo = findViewById<Button>(R.id.btn_no)

        val preferenze = getSharedPreferences("settings", MODE_PRIVATE)

        btnSi.setOnClickListener {
            preferenze.edit().putBoolean("schermo_acceso_attivo", true).apply()
            onBackPressedDispatcher.onBackPressed()
        }

        btnNo.setOnClickListener {
            preferenze.edit().putBoolean("schermo_acceso_attivo", false).apply()
            onBackPressedDispatcher.onBackPressed()
        }
    }
}