package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoPrincipiante2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_2)

        val toolbarPrincipianteDettaglio2 = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_2)
        setSupportActionBar(toolbarPrincipianteDettaglio2)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 2"
        }

        //Apertura Schermata PancaPianaConManubri
        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            val intentPancaPianaConManubri = Intent(this, PancaPianaManubriActivity::class.java)
            startActivity(intentPancaPianaConManubri)
        }

        //Apertura Schermata RematoreConManubri
        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            val intentRematoreConManubri = Intent(this, RematoreManubriActivity::class.java)
            startActivity(intentRematoreConManubri)
        }

        //Apertura Schermata AlzateLaterali
        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            val intentAlzateLaterali = Intent(this, AlzateLateraliActivity::class.java)
            startActivity(intentAlzateLaterali)
        }

        //Apertura Schermata RussianTwistConManubrio
        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            val intentRussianTwistManubrio = Intent(this, RussianTwistManubrioActivity::class.java)
            startActivity(intentRussianTwistManubrio)
        }

        //Apertura Schermata PlankIsometrico
        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            val intentPlankIsometrico = Intent(this, PlankIsometricoActivity::class.java)
            startActivity(intentPlankIsometrico)
        }

        toolbarPrincipianteDettaglio2.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}