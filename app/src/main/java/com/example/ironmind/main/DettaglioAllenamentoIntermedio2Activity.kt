package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.DettaglioAllenamentoIntermedio2ViewModel

class DettaglioAllenamentoIntermedio2Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoIntermedio2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_2)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_2)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 2"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            startActivity(Intent(this, LatMachineActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            startActivity(Intent(this, RematoreManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            startActivity(Intent(this, PulleyBassoActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            startActivity(Intent(this, CurlManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            startActivity(Intent(this, CurlBilanciereEzActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Intermedio 2"
            val esercizi = viewModel.getEsercizi()
            viewModel.salvaScheda(nomeScheda, esercizi)

            getSharedPreferences("settings", MODE_PRIVATE)
                .edit().putString("scheda_salvata_nome", nomeScheda).apply()

            startActivity(Intent(this, AllenamentoDinamicoUI::class.java).apply {
                putExtra("nomeScheda", nomeScheda)
            })
        }
    }
}