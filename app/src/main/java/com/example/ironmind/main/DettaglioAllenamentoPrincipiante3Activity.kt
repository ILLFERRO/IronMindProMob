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

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio(
                    nome = "Squat Con Bilanciere",
                    descrizione = "Squat completo con bilanciere su schiena",
                    setPrevisti = 3,
                    ripetizioniPreviste = 15,
                    usaPeso = true,
                    pesoPredefinito = 50f
                ),
                Esercizio(
                    nome = "Tirata Stretta",
                    descrizione = "Rematore verticale per schiena e trapezio",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Leg Curl (Macchina)",
                    descrizione = "Flessori posteriori con macchina da sdraiato",
                    setPrevisti = 3,
                    ripetizioniPreviste = 15,
                    usaPeso = true,
                    pesoPredefinito = 30f
                ),
                Esercizio(
                    nome = "Calfe Raise Alla Leg Press",
                    descrizione = "Sollevamento polpacci con pressa orizzontale",
                    setPrevisti = 3,
                    ripetizioniPreviste = 20,
                    usaPeso = true,
                    pesoPredefinito = 60f
                ),
                Esercizio(
                    nome = "Cyclette o Tapis Roulant",
                    descrizione = "Attività cardio leggera",
                    setPrevisti = 1,
                    ripetizioniPreviste = 10, // minuti
                    usaPeso = false
                )
            )

            val nomeScheda = "Principiante 3"

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