package com.example.ironmind.main

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import android.widget.Toast

class SetDefaultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_default)

        val toolbarSetDefault = findViewById<Toolbar>(R.id.toolbar_set_default)
        setSupportActionBar(toolbarSetDefault)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Set Di Default"
        }

        toolbarSetDefault.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val editSet = findViewById<EditText>(R.id.input_set_default)
        val saveBtn = findViewById<TextView>(R.id.save_button)

        // 1. Recupera il valore salvato all'avvio
        val preferenzeSet = getSharedPreferences("settings", MODE_PRIVATE)
        val savedSet = preferenzeSet.getString("Set_Default", "3") ?: "3"
        editSet.setText(savedSet)  // Imposta il valore nel campo EditText

        // 2. Salva il nuovo valore quando l'utente preme "Salva"
        saveBtn.setOnClickListener {
            val incremento = editSet.text.toString()

            if (incremento.isNotEmpty()) {
                val preferenzeSetDefault = getSharedPreferences("settings", MODE_PRIVATE)
                preferenzeSetDefault.edit().putString("Set_Default", incremento).apply()  // <- QUESTA È GIUSTA QUI

                // Torna indietro
                onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(this, "Inserisci un valore", Toast.LENGTH_SHORT).show()
            }
        }
    }
}