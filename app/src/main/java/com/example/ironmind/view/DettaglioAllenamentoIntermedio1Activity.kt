package com.example.ironmind.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.Activity.ChestPressActivity
import com.example.ironmind.Activity.CrociManubriPancaActivity
import com.example.ironmind.Activity.EstensioniTricipitiManubrioActivity
import com.example.ironmind.Activity.PushDownTricipitiCaviActivity
import com.example.ironmind.Activity.ShoulderPressActivity
import com.example.ironmind.viewmodel.DettaglioAllenamentoIntermedio1ViewModel

class DettaglioAllenamentoIntermedio1Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoIntermedio1ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_1)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 1"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            startActivity(Intent(this, ChestPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            startActivity(Intent(this, ShoulderPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            startActivity(Intent(this, CrociManubriPancaActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            startActivity(Intent(this, PushDownTricipitiCaviActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            startActivity(Intent(this, EstensioniTricipitiManubrioActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Intermedio 1"
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