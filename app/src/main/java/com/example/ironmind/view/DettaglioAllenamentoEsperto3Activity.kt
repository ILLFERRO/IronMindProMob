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
import com.example.ironmind.Activity.LegCurlSdraiatoActivity
import com.example.ironmind.Activity.MacchinaCrunchCaricoPiastraActivity
import com.example.ironmind.Activity.RussianTwistPallaMedicaActivity
import com.example.ironmind.Activity.SquatBilanciereActivity
import com.example.ironmind.viewmodel.DettaglioAllenamentoEsperto3ViewModel

class DettaglioAllenamentoEsperto3Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoEsperto3ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto3)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_3)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 3"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            startActivity(Intent(this, SquatBilanciereActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            startActivity(Intent(this, AffondiManubriActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            startActivity(Intent(this, LegCurlSdraiatoActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            startActivity(Intent(this, MacchinaCrunchCaricoPiastraActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            startActivity(Intent(this, RussianTwistPallaMedicaActivity::class.java))
        }


        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Esperto 3"
            val esercizi = viewModel.getEsercizi()
            viewModel.salvaScheda(this, nomeScheda, esercizi)

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}