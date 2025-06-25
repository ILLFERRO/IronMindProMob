package com.example.ironmind.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ProfiloSessoActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioUomo: RadioButton
    private lateinit var radioDonna: RadioButton
    private lateinit var buttonSalva: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilo_sesso)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profilo_sesso)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Sesso"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        radioGroup = findViewById(R.id.radioGroupSesso)
        radioUomo = findViewById(R.id.radioUomo)
        radioDonna = findViewById(R.id.radioDonna)
        buttonSalva = findViewById(R.id.buttonSalvaSesso)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val sessoSalvato = prefs.getString("profilo_sesso", "")

        if (sessoSalvato == "Uomo") {
            radioUomo.isChecked = true
        } else if (sessoSalvato == "Donna") {
            radioDonna.isChecked = true
        }

        buttonSalva.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Seleziona un'opzione", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sessoSelezionato = when (selectedId) {
                R.id.radioUomo -> "Uomo"
                R.id.radioDonna -> "Donna"
                else -> ""
            }

            prefs.edit().putString("profilo_sesso", sessoSelezionato).apply()
            Toast.makeText(this, "Dati salvati", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}