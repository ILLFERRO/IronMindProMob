package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.DettaglioAllenamentoEsperto1ViewModel

class DettaglioAllenamentoEsperto1Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoEsperto1ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto_1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_1)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 1"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            startActivity(Intent(this, PancaPianaBilanciereActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            startActivity(Intent(this, ShoulderPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            startActivity(Intent(this, ChestPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            startActivity(Intent(this, AlzateLateraliActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            startActivity(Intent(this, DipParalleleAssistitiActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Esperto 1"
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