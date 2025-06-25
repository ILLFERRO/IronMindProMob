package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class DettaglioAllenamentoEsperto2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto_2)

        val toolbarEspertoDettaglio2 = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_2)
        setSupportActionBar(toolbarEspertoDettaglio2)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 2"
        }

        //Apertura Schermata StaccoDaTerraBilanciere
        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            val intentStaccoDaTerraBilanciere = Intent(this, StaccoDaTerraBilanciereActivity::class.java)
            startActivity(intentStaccoDaTerraBilanciere)
        }

        //Apertura Schermata LatMachinePresaInversa
        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            val intentLatMachinePresaInversa = Intent(this, LatMachinePresaInversaActivity::class.java)
            startActivity(intentLatMachinePresaInversa)
        }

        //Apertura Schermata RematoreConBilanciere
        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            val intentRematoreConBilanciere = Intent(this, RematoreBilanciereActivity::class.java)
            startActivity(intentRematoreConBilanciere)
        }

        //Apertura Schermata CurlConcentratoConManubrio
        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            val intentCurlConcentratoConManubrio = Intent(this, CurlConcentratoManubrioActivity::class.java)
            startActivity(intentCurlConcentratoConManubrio)
        }

        //Apertura Schermata CurlConManubriSuPancaInclinata
        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            val intentCurlConManubriSuPancaInclinata = Intent(this, CurlManubriPancaInclinataActivity::class.java)
            startActivity(intentCurlConManubriSuPancaInclinata)
        }

        toolbarEspertoDettaglio2.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Bottone "Comincia Allenamento"
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val esercizi = arrayListOf(
                Esercizio("Stacco Da Terra Con Bilanciere", "4x6"),
                Esercizio("Lat Machine Presa Inversa", "4x10"),
                Esercizio("Rematore Con Bilanciere", "3x8"),
                Esercizio("Curl Concentrato Manubrio", "3x10"),
                Esercizio("Curl Su Panca Inclinata", "3x12")
            )

            val nomeScheda = "Esperto 2"

            // Salva scheda (assumendo tu abbia un gestore simile a SchedaManager)
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