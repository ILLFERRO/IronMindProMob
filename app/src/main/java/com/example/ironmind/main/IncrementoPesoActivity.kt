package com.example.ironmind.main

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import android.widget.Toast

class IncrementoPesoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incremento_peso)

        val toolbarIncrementoPeso = findViewById<Toolbar>(R.id.toolbar_incremento_peso)
        setSupportActionBar(toolbarIncrementoPeso)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Incremento Peso"
        }

        toolbarIncrementoPeso.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val editWeight = findViewById<EditText>(R.id.input_weight)
        val saveBtn = findViewById<TextView>(R.id.save_button)

        // Carica il valore salvato
        val preferenzePeso = getSharedPreferences("settings", MODE_PRIVATE)
        val savedPeso = preferenzePeso.getString("peso_incremento", "2.5")
        editWeight.setText(savedPeso)

        saveBtn.setOnClickListener {
            val incremento = editWeight.text.toString()

            if (incremento.isNotEmpty()) {
                val preferenzeIncrementoPeso = getSharedPreferences("settings", MODE_PRIVATE)
                preferenzeIncrementoPeso.edit().putString("peso_incremento", incremento).apply()

                // Torna indietro come se avessi cliccato la freccia della toolbar
                onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(this, "Inserisci un valore", Toast.LENGTH_SHORT).show()
            }
        }
    }
}