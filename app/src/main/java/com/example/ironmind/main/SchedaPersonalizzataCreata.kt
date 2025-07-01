package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R
import android.content.Intent
import android.widget.Toast

class SchedaPersonalizzataCreata : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EserciziStaticiAdapter
    private lateinit var btnElimina: Button
    private lateinit var nomeScheda: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheda_personalizzata_creata)

        // ✅ Recupera nome della scheda
        nomeScheda = intent.getStringExtra("nomeScheda") ?: return finish()

        // ✅ Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_scheda_creata)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = nomeScheda
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // ✅ Titolo Scheda
        findViewById<TextView>(R.id.titoloScheda).text = nomeScheda
        // ✅ Recupera tempo totale e data ultimo allenamento
        val prefsStats = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
        val durataSec = prefsStats.getLong("durata_totale_sec_$nomeScheda", 0L)
        val dataAllenamento = prefsStats.getString("data_ultimo_allenamento_$nomeScheda", "N/D")

        val minuti = durataSec / 60
        val secondi = durataSec % 60
        val durataFormattata = String.format("%02d:%02d", minuti, secondi)

        // ✅ Mostra nella UI (supponendo che tu aggiunga due TextView nel layout)
        findViewById<TextView>(R.id.txtDataAllenamento).text = "Ultimo allenamento: $dataAllenamento"
        findViewById<TextView>(R.id.txtDurataAllenamento).text = "Durata: $durataFormattata"

        // ✅ Carica dati da memoria
        SchedaManager.caricaSchedaDaStorage(nomeScheda, this)

        // ✅ RecyclerView
        recyclerView = findViewById(R.id.recyclerViewEsercizi)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val lista = SchedaManager.getScheda(nomeScheda, this)
        adapter = EserciziStaticiAdapter(lista)
        recyclerView.adapter = adapter

        // ✅ Bottone Elimina
        btnElimina = findViewById(R.id.btnEliminaScheda)
        btnElimina.setOnClickListener { eliminaScheda() }

        // ✅ Bottone Comincia Allenamento
        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val eserciziSalvati = SchedaManager.getScheda(nomeScheda, this)

            if (eserciziSalvati.isNotEmpty()) {
                // Salva il nome della scheda per continuità
                getSharedPreferences("settings", MODE_PRIVATE)
                    .edit().putString("scheda_salvata_nome", nomeScheda).apply()

                val intent = Intent(this, AllenamentoDinamicoUI::class.java)
                intent.putExtra("nomeScheda", nomeScheda)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Nessun esercizio trovato per questa scheda", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun eliminaScheda() {
        SchedaManager.clearScheda(nomeScheda)

        val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
        val schedeNomi = prefs.getStringSet("mieSchedeNomi", mutableSetOf())?.toMutableSet()
        schedeNomi?.remove(nomeScheda)
        prefs.edit().putStringSet("mieSchedeNomi", schedeNomi)
            .remove("scheda_$nomeScheda")
            .apply()
        finish()
    }
}