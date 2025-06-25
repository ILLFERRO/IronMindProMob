package com.example.ironmind.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ProfiloPesoAltezzaActivity : AppCompatActivity() {

    private lateinit var numberPickerPeso: NumberPicker
    private lateinit var numberPickerAltezza: NumberPicker
    private lateinit var buttonSalva: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilo_peso_altezza)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profilo_peso_altezza)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Peso e Altezza"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        numberPickerPeso = findViewById(R.id.numberPickerPeso)
        numberPickerAltezza = findViewById(R.id.numberPickerAltezza)
        buttonSalva = findViewById(R.id.buttonSalvaPesoAltezza)

        // Peso: da 0 a 250 kg
        numberPickerPeso.minValue = 0
        numberPickerPeso.maxValue = 250

        // Altezza: da 100 a 300 cm
        numberPickerAltezza.minValue = 100
        numberPickerAltezza.maxValue = 300

        // Carica valori precedenti se esistono
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        numberPickerPeso.value = prefs.getInt("profilo_peso", 70)
        numberPickerAltezza.value = prefs.getInt("profilo_altezza", 170)

        buttonSalva.setOnClickListener {
            val peso = numberPickerPeso.value
            val altezza = numberPickerAltezza.value

            prefs.edit()
                .putInt("profilo_peso", peso)
                .putInt("profilo_altezza", altezza)
                .apply()

            Toast.makeText(this, "Dati salvati", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
