package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoIntermedio1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_intermedio_1)

        val toolbarIntermedioDettaglio1 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Intermedio_1)
        setSupportActionBar(toolbarIntermedioDettaglio1)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Intermedio 1"
        }

        //Apertura Schermata ChestPress
        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            val intentChestPress = Intent(this, ChestPressActivity::class.java)
            startActivity(intentChestPress)
        }

        //Apertura Schermata ShoulderPress
        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            val intentShoulderPress = Intent(this, ShoulderPressActivity::class.java)
            startActivity(intentShoulderPress)
        }

        //Apertura Schermata CrociManubriPanca
        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            val intentCrociManubriPanca = Intent(this, CrociManubriPancaActivity::class.java)
            startActivity(intentCrociManubriPanca)
        }

        //Apertura Schermata PushDownTricipitiCavi
        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            val intentPushDownTricipitiCavi = Intent(this, PushDownTricipitiCaviActivity::class.java)
            startActivity(intentPushDownTricipitiCavi)
        }

        //Apertura Schermata EstensioniTricipitiManubri
        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            val intentEstensioniTricipitiManubri = Intent(this, EstensioniTricipitiManubrioActivity::class.java)
            startActivity(intentEstensioniTricipitiManubri)
        }

        toolbarIntermedioDettaglio1.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Pulsante Comincia Allenamento
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio(
                    nome = "Chest Press",
                    descrizione = "4 serie da 10 ripetizioni",
                    setPrevisti = 4,
                    ripetizioniPreviste = 10,
                    usaPeso = true,
                    pesoPredefinito = 40f // puoi cambiare il peso come preferisci
                ),
                Esercizio(
                    nome = "Shoulder Press",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Croci Con Manubri Su Panca",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 10f
                ),
                Esercizio(
                    nome = "Pushdown Tricipiti Cavi",
                    descrizione = "3 serie da 15 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 15,
                    usaPeso = true,
                    pesoPredefinito = 20f
                ),
                Esercizio(
                    nome = "Estensioni Tricipiti Manubrio",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 12f
                )
            )

            val nomeScheda = "Intermedio 1"

            // Salva la scheda
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