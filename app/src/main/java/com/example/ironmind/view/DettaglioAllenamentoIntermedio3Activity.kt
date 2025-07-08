package com.example.ironmind.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.Activity.AffondiManubriActivity
import com.example.ironmind.Activity.CrunchSulTappetinoActivity
import com.example.ironmind.Activity.LegCurlActivity
import com.example.ironmind.Activity.LegPressActivity
import com.example.ironmind.Activity.PlankIsometricoActivity
import com.example.ironmind.viewmodel.DettaglioAllenamentoIntermedio3ViewModel

class DettaglioAllenamentoIntermedio3Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoIntermedio3ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_3)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_3)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 3"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            startActivity(Intent(this, LegPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            startActivity(Intent(this, LegCurlActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            startActivity(Intent(this, AffondiManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            startActivity(Intent(this, CrunchSulTappetinoActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            startActivity(Intent(this, PlankIsometricoActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Intermedio 3"
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