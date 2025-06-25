package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R

class DettaglioAllenamentoEsperto1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto_1)

        val toolbarEspertoDettaglio1 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_1)
        setSupportActionBar(toolbarEspertoDettaglio1)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 1"
        }

        //Apertura Schermata PancaPianaBilanciere
        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            val intentPancaPianaBilanciere = Intent(this, PancaPianaBilanciereActivity::class.java)
            startActivity(intentPancaPianaBilanciere)
        }

        //Apertura Schermata ShoulderPress
        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            val intentShoulderPress = Intent(this, ShoulderPressActivity::class.java)
            startActivity(intentShoulderPress)
        }

        //Apertura Schermata ChestPress
        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            val intentChestPress = Intent(this, ChestPressActivity::class.java)
            startActivity(intentChestPress)
        }

        //Apertura Schermata AlzateLaterali
        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            val intentAlzateLaterali = Intent(this, AlzateLateraliActivity::class.java)
            startActivity(intentAlzateLaterali)
        }

        //Apertura Schermata DipParalleleAssistiti
        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            val intentDipParalleleAssistiti = Intent(this, DipParalleleAssistitiActivity::class.java)
            startActivity(intentDipParalleleAssistiti)
        }

        toolbarEspertoDettaglio1.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Bottone "Comincia Allenamento"
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio(
                    nome = "Panca Piana Con Bilanciere",
                    descrizione = "4 serie da 8 ripetizioni",
                    setPrevisti = 4,
                    ripetizioniPreviste = 8,
                    usaPeso = true,
                    pesoPredefinito = 70f
                ),
                Esercizio(
                    nome = "Shoulder Press Con Manubri",
                    descrizione = "4 serie da 10 ripetizioni",
                    setPrevisti = 4,
                    ripetizioniPreviste = 10,
                    usaPeso = true,
                    pesoPredefinito = 25f
                ),
                Esercizio(
                    nome = "Chest Press",
                    descrizione = "3 serie da 10 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 10,
                    usaPeso = true,
                    pesoPredefinito = 40f
                ),
                Esercizio(
                    nome = "Alzate Laterali Con Manubri",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = true,
                    pesoPredefinito = 10f
                ),
                Esercizio(
                    nome = "Dip Alle Parallele assistiti",
                    descrizione = "3 serie da 12 ripetizioni",
                    setPrevisti = 3,
                    ripetizioniPreviste = 12,
                    usaPeso = false
                )
            )

            val nomeScheda = "Esperto 1"

            // Salva scheda (devi implementare SchedaManager come in altri esempi)
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