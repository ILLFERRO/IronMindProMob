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
                Esercizio("Panca Piana Con Bilanciere", "4x8"),
                Esercizio("Shoulder Press Con Manubri", "4x10"),
                Esercizio("Chest Press", "3x10"),
                Esercizio("Alzate Laterali Con Manubri", "3x12"),
                Esercizio("Dip Alle Parallele assistiti", "3x12")
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