package com.example.ironmind.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class TempoDiRiposoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tempo_di_riposo)

        val toolbarTempoDiRiposo = findViewById<Toolbar>(R.id.toolbar_tempo_riposo)
        setSupportActionBar(toolbarTempoDiRiposo)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Set Di Default"
        }

        toolbarTempoDiRiposo.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val minutePicker = findViewById<NumberPicker>(R.id.minutiPicker)
        val secondPicker = findViewById<NumberPicker>(R.id.secondiPicker)
        val btnSalva = findViewById<Button>(R.id.btn_salva)

        minutePicker.minValue = 0
        minutePicker.maxValue = 10

        secondPicker.minValue = 0
        secondPicker.maxValue = 59

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedMinutes = prefs.getInt("riposo_min", 0)
        val savedSeconds = prefs.getInt("riposo_sec", 30)

        minutePicker.value = savedMinutes
        secondPicker.value = savedSeconds

        btnSalva.setOnClickListener {
            val minuti = minutePicker.value
            val secondi = secondPicker.value

            prefs.edit()
                .putInt("riposo_min", minuti)
                .putInt("riposo_sec", secondi)
                .apply()

            onBackPressedDispatcher.onBackPressed()
        }
    }
}