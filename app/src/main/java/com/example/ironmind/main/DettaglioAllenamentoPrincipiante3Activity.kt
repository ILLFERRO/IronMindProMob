package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoPrincipiante3Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoPrincipiante3ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_3)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_3)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 3"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            startActivity(Intent(this, SquatBilanciereActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            startActivity(Intent(this, TirataStrettaActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            startActivity(Intent(this, LegCurlActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            startActivity(Intent(this, CalfRaiseAllaLegPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            startActivity(Intent(this, CycletteTapisRoulantActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Principiante 3"
            val esercizi = viewModel.getEsercizi()

            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
            SchedaManager.salvaScheda(nomeScheda, this)

            getSharedPreferences("settings", MODE_PRIVATE)
                .edit()
                .putString("scheda_salvata_nome", nomeScheda)
                .apply()

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}