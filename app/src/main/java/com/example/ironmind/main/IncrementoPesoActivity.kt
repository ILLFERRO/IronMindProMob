package com.example.ironmind.main

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.ironmind.R
import com.example.ironmind.viewmodel.IncrementoPesoViewModel

class IncrementoPesoActivity : AppCompatActivity() {

    private val viewModel: IncrementoPesoViewModel by viewModels()
    private lateinit var editWeight: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incremento_peso)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_incremento_peso)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Incremento Peso"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        editWeight = findViewById(R.id.input_weight)
        saveBtn = findViewById(R.id.save_button)

        viewModel.incrementoPeso.observe(this) { valore ->
            editWeight.setText(valore)
        }

        viewModel.caricaValoreSalvato()

        saveBtn.setOnClickListener {
            val valore = editWeight.text.toString()
            val successo = viewModel.salvaValore(valore)

            if (successo) {
                onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(this, "Inserisci un valore", Toast.LENGTH_SHORT).show()
            }
        }
    }
}