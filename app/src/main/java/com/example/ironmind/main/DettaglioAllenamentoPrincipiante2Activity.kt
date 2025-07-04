package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoPrincipiante2Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoPrincipiante2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_2)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_2)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 2"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Navigazione verso schermate esercizi
        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            startActivity(Intent(this, PancaPianaManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            startActivity(Intent(this, RematoreManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            startActivity(Intent(this, AlzateLateraliActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            startActivity(Intent(this, RussianTwistManubrioActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            startActivity(Intent(this, PlankIsometricoActivity::class.java))
        }

                // Inizio Allenamento
                findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Principiante 2"
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