package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

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