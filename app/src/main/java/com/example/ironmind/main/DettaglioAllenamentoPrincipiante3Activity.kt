package com.example.ironmind.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import android.content.Intent
import android.widget.Button
import androidx.cardview.widget.CardView

class DettaglioAllenamentoPrincipiante3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_3)

        val toolbarPrincipianteDettaglio3 = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_3)
        setSupportActionBar(toolbarPrincipianteDettaglio3)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 3"
        }

        //Apertura Schermata SquatConBilanciere
        findViewById<CardView>(R.id.Card_Esercizio_1_3).setOnClickListener {
            val intentSquatConBilanciere = Intent(this, SquatBilanciereActivity::class.java)
            startActivity(intentSquatConBilanciere)
        }

        //Apertura Schermata TirataStretta
        findViewById<CardView>(R.id.Card_Esercizio_2_3).setOnClickListener {
            val intentTirataStretta = Intent(this, TirataStrettaActivity::class.java)
            startActivity(intentTirataStretta)
        }

        //Apertura Schermata LegCurl
        findViewById<CardView>(R.id.Card_Esercizio_3_3).setOnClickListener {
            val intentLegCurl = Intent(this, LegCurlActivity::class.java)
            startActivity(intentLegCurl)
        }

        //Apertura Schermata CalfRaiseLegPress
        findViewById<CardView>(R.id.Card_Esercizio_4_3).setOnClickListener {
            val intentCalfRaiseLegPress = Intent(this, CalfRaiseAllaLegPressActivity::class.java)
            startActivity(intentCalfRaiseLegPress)
        }

        //Apertura Schermata Cyclette/TapisRoulant
        findViewById<CardView>(R.id.Card_Esercizio_5_3).setOnClickListener {
            val intentCycletteTapisRoulant = Intent(this, CycletteTapisRoulantActivity::class.java)
            startActivity(intentCycletteTapisRoulant)
        }

        toolbarPrincipianteDettaglio3.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Pulsante per avviare allenamento dinamico
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio("Squat Con Bilanciere", "3x15"),
                Esercizio("Tirata Stretta", "3x12"),
                Esercizio("Leg Curl (Macchina)", "3x15"),
                Esercizio("Calfe Raise Alla Leg Press", "3x20"),
                Esercizio("Cyclette o Tapis Roulant", "10 minuti (res. leggera)")
            )

            val nomeScheda = "Principiante 3"

            // Salva la scheda nei dati temporanei
            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi
            SchedaManager.salvaScheda(nomeScheda, this)

            // Salva il nome della scheda attiva per recupero al termine
            getSharedPreferences("settings", MODE_PRIVATE)
                .edit().putString("scheda_salvata_nome", nomeScheda).apply()

            // Avvia schermata di allenamento dinamico
            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}