package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoPrincipiante1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_1)

        val toolbarPrincipianteDettaglio1 = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_1)
        setSupportActionBar(toolbarPrincipianteDettaglio1)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 1"
        }

        //Apertura Schermata ChestPress
        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            val intentChestPress = Intent(this, ChestPressActivity::class.java)
            startActivity(intentChestPress)
        }

        //Apertura Schermata LatMachine
        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            val intentLatMachine = Intent(this, LatMachineActivity::class.java)
            startActivity(intentLatMachine)
        }

        //Apertura Schermata LegPress
        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            val intentLegPress = Intent(this, LegPressActivity::class.java)
            startActivity(intentLegPress)
        }

        //Apertura Schermata ShoulderPress
        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            val intentShoulderPress = Intent(this, ShoulderPressActivity::class.java)
            startActivity(intentShoulderPress)
        }

        //Apertura Schermata CrunchSulTappetino
        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            val intentCrunchSulTappetino = Intent(this, CrunchSulTappetinoActivity::class.java)
            startActivity(intentCrunchSulTappetino)
        }

        toolbarPrincipianteDettaglio1.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio(
                    nome = "Chest Press",
                    descrizione = "Esercizio per i pettorali su macchina",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 40f
                ),
                Esercizio(
                    nome = "Lat Machine",
                    descrizione = "Trazioni assistite per dorsali",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 35f
                ),
                Esercizio(
                    nome = "Leg Press",
                    descrizione = "Spinta delle gambe sulla pressa orizzontale",
                    setPrevisti = 3,
                    ripetizioniPreviste = 15,
                    usaPeso = true,
                    pesoPredefinito = 80f
                ),
                Esercizio(
                    nome = "Shoulder Press",
                    descrizione = "Spinta sopra la testa per le spalle",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Crunch Su Tappetino",
                    descrizione = "Addominali a corpo libero",
                    setPrevisti = 3,
                    ripetizioniPreviste = 15,
                    usaPeso = false // corpo libero, quindi tempo recupero
                )
            )

            val nomeScheda = "Principiante 1"

            // Salva temporaneamente la scheda
            SchedaManager.schedePersonalizzate[nomeScheda] = esercizi
            SchedaManager.salvaScheda(nomeScheda, this)

            // Salva anche nelle SharedPreferences
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()

            // Avvia AllenamentoDinamicoUI
            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda) //trasferisce stringa nomeScheda da un'Activity a un altra tramite intent
            startActivity(intent)
        }
    }
}