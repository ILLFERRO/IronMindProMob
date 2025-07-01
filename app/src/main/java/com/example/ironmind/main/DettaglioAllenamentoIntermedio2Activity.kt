package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        // Comincia Allenamento
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio(
                    nome = "Lat Machine Presa Larga",
                    descrizione = "4 serie da 10 ripetizioni",
                    setPrevisti = 4,
                    ripetizioniPreviste = 10,
                    usaPeso = true,
                    pesoPredefinito = 40f
                ),
                Esercizio(
                    nome = "Rematore Manubrio",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Pulley Basso",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 30f
                ),
                Esercizio(
                    nome = "Curl Manubri",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 10f
                ),
                Esercizio(
                    nome = "Curl Con Bilanciere EZ",
                    descrizione = "3 serie da 10 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 10,
                    usaPeso = true,
                    pesoPredefinito = 15f
                )
            )

            val nomeScheda = "Intermedio 2"

            // Salva scheda nei dati persistenti
            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi
            SchedaManager.salvaScheda(nomeScheda, this)

            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}