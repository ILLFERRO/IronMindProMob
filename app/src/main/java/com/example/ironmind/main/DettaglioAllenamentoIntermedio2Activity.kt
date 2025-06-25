package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class DettaglioAllenamentoIntermedio2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_2)

        val toolbarIntermedioDettaglio2 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_2)
        setSupportActionBar(toolbarIntermedioDettaglio2)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 2"
        }

        //Apertura Schermata LatMachine
        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            val intentLatMachine = Intent(this, LatMachineActivity::class.java)
            startActivity(intentLatMachine)
        }

        //Apertura Schermata RematoreConManubri
        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            val intentRematoreConManubri = Intent(this, RematoreManubriActivity::class.java)
            startActivity(intentRematoreConManubri)
        }

        //Apertura Schermata PulleyBasso
        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            val intentPulleyBasso = Intent(this, PulleyBassoActivity::class.java)
            startActivity(intentPulleyBasso)
        }

        //Apertura Schermata CurlManubri
        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            val intentCurlManubri = Intent(this, CurlManubriActivity::class.java)
            startActivity(intentCurlManubri)
        }

        //Apertura Schermata CurlBilanciereEZ
        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            val intentCurlBilanciereEZ = Intent(this, CurlBilanciereEzActivity::class.java)
            startActivity(intentCurlBilanciereEZ)
        }

        toolbarIntermedioDettaglio2.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}