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
                Esercizio(
                    nome = "Panca Piana Con Manubri",
                    descrizione = "Esercizio per pettorali eseguito con manubri",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 12f
                ),
                Esercizio(
                    nome = "Rematore Con Manubri",
                    descrizione = "Esercizio per dorsali con manubri",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 14f
                ),
                Esercizio(
                    nome = "Alzate Laterali Con Macchinario",
                    descrizione = "Isolamento del deltoide laterale con macchina",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Russian Twist con manubrio singolo",
                    descrizione = "Esercizio per obliqui con torsione del busto",
                    setPrevisti = 3,
                    ripetizioniPreviste = 20, // totali
                    usaPeso = true,
                    pesoPredefinito = 6f
                ),
                Esercizio(
                    nome = "Plank Isometrico",
                    descrizione = "Tenuta isometrica per il core",
                    setPrevisti = 3,
                    ripetizioniPreviste = 30, // secondi
                    usaPeso = false // corpo libero
                )
            )

            val nomeScheda = "Principiante 2"

            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi
            SchedaManager.salvaScheda(nomeScheda, this)

            getSharedPreferences("settings", MODE_PRIVATE)
                .edit().putString("scheda_salvata_nome", nomeScheda).apply()

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}