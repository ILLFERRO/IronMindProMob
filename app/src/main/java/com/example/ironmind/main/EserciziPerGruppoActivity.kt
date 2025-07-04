package com.example.ironmind.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class EserciziPerGruppoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EserciziAdapter
    private lateinit var nomeScheda: String  // ✅ campo globale per nome scheda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esercizi_per_gruppo)

        val gruppoNome = intent.getStringExtra("gruppo_nome") ?: "Esercizi"
        val gruppoDescrizione = intent.getStringExtra("gruppo_descrizione") ?: ""
        nomeScheda = intent.getStringExtra("nomeScheda") ?: "Scheda Temporanea"  // ✅ inizializza qui

        // Toolbar
        val toolbarEserciziPerGruppo = findViewById<Toolbar>(R.id.toolbar_esercizi_per_gruppo)
        setSupportActionBar(toolbarEserciziPerGruppo)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = gruppoNome
        }
        toolbarEserciziPerGruppo.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Titolo e descrizione
        findViewById<TextView>(R.id.titoloGruppo).text = gruppoNome
        findViewById<TextView>(R.id.descrizioneGruppo).text = gruppoDescrizione

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewEsercizi)
        recyclerView.layoutManager = LinearLayoutManager(this) //setto il layoutManager che la RecyclerView userà

        val listaEsercizi = EserciziRepository.eserciziPerGruppo[gruppoNome] ?: emptyList()

        adapter = EserciziAdapter(nomeScheda, listaEsercizi) { esercizio -> //definisco un adapter di nome EserciziAdapter e passo un nomeScheda e la listaEsercizi, e poi definisco una lambda functions che mi definisce cosa succede quando clicco il pulsante accanto all'esercizio
            if (SchedaManager.contiene(nomeScheda, esercizio)) {
                SchedaManager.rimuoviEsercizio(nomeScheda, esercizio)
            } else {
                SchedaManager.aggiungiEsercizio(nomeScheda, esercizio)
            }
            adapter.notifyDataSetChanged() //dopo aver modificato la scheda aggiorna la RecyclerView
        }

        recyclerView.adapter = adapter //assegno adapter alla RecyclerView
    }

    override fun onPause() {
        super.onPause()
        // ✅ Salva sempre con il nome corretto
        SchedaManager.salvaScheda(nomeScheda, this)
    }
}