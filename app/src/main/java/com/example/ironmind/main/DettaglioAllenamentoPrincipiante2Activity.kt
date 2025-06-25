package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio("Panca Piana Con Manubri", "3x12"),
                Esercizio("Rematore Con Manubri", "3x12"),
                Esercizio("Alzate Laterali Con Macchinario", "3x12"),
                Esercizio("Russian Twist con manubrio singolo", "3x20 (10 per lato)"),
                Esercizio("Plank Isometrico", "3x30 sec")
            )

            val nomeScheda = "Principiante 2"

            // Salva temporaneamente la scheda
            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi
            SchedaManager.salvaScheda(nomeScheda, this)

            // Salva nome per recuperarlo a fine allenamento
            getSharedPreferences("settings", MODE_PRIVATE)
                .edit().putString("scheda_salvata_nome", nomeScheda).apply()

            // Avvia AllenamentoDinamicoUI
            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}