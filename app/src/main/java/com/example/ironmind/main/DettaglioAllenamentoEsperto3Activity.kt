package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class DettaglioAllenamentoEsperto3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto3)

        val toolbarEspertoDettaglio3 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_3)
        setSupportActionBar(toolbarEspertoDettaglio3)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 3"
        }

        //Apertura Schermata SquatConBilanciere
        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            val intentSquatConBilanciere = Intent(this, SquatBilanciereActivity::class.java)
            startActivity(intentSquatConBilanciere)
        }

        //Apertura Schermata AffondiManubri
        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            val intentAffondiManubri = Intent(this, AffondiManubriActivity::class.java)
            startActivity(intentAffondiManubri)
        }

        //Apertura Schermata LegCurlSdraiato
        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            val intentLegCurlSdraiato = Intent(this, LegCurlSdraiatoActivity::class.java)
            startActivity(intentLegCurlSdraiato)
        }

        //Apertura Schermata MacchinaCrunchConCaricoAPiastra
        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            val intentMacchinaCrunchConCaricoAPiastra = Intent(this, MacchinaCrunchCaricoPiastraActivity::class.java)
            startActivity(intentMacchinaCrunchConCaricoAPiastra)
        }

        //Apertura Schermata RussianTwistPallaMedica
        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            val intentRussianTwistPallaMedica = Intent(this, RussianTwistPallaMedicaActivity::class.java)
            startActivity(intentRussianTwistPallaMedica)
        }

        toolbarEspertoDettaglio3.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}