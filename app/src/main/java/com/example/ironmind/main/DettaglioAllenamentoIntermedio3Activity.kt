package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoIntermedio3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_3)

        val toolbarIntermedioDettaglio3 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_3)
        setSupportActionBar(toolbarIntermedioDettaglio3)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 3"
        }

        //Apertura Schermata LegPress
        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            val intentLegPress = Intent(this, LegPressActivity::class.java)
            startActivity(intentLegPress)
        }

        //Apertura Schermata LegCurl
        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            val intentLegCurl = Intent(this, LegCurlActivity::class.java)
            startActivity(intentLegCurl)
        }


        //Apertura Schermata AffondiManubri
        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            val intentAffondiManubri = Intent(this, AffondiManubriActivity::class.java)
            startActivity(intentAffondiManubri)
        }

        //Apertura Schermata CrunchSulTappetino
        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            val intentCrunchSulTappetino = Intent(this, CrunchSulTappetinoActivity::class.java)
            startActivity(intentCrunchSulTappetino)
        }

        //Apertura Schermata PlankIsometrico
        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            val intentPlankIsometrico = Intent(this, PlankIsometricoActivity::class.java)
            startActivity(intentPlankIsometrico)
        }

        toolbarIntermedioDettaglio3.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Bottone "Comincia Allenamento"
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio("Leg Press", "4x12"),
                Esercizio("Leg Curl", "3x15"),
                Esercizio("Affondi Con Manubri", "3x10 (per gamba)"),
                Esercizio("Crunch Su Tappetino", "3x20"),
                Esercizio("Plank", "3x30 (secondi)")
            )

            val nomeScheda = "Intermedio 3"

            // Salvataggio scheda (implementa SchedaManager a parte)
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