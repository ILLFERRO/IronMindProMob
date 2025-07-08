package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class SchedaGruppiMuscolariActivity : AppCompatActivity() {

    private val nomeScheda = "Scheda Temporanea"
    private lateinit var btnVaiScheda: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheda_gruppi_muscolari)

        val toolbarSchedaGruppiMuscolari = findViewById<Toolbar>(R.id.toolbar_scheda_gruppi_muscolari)
        setSupportActionBar(toolbarSchedaGruppiMuscolari)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Gruppi Muscolari"
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewGruppi)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GruppiMuscolariAdapter(GruppiMuscolariRepository.listaGruppi) { gruppo ->
            val intent = Intent(this, EserciziPerGruppoActivity::class.java)
            intent.putExtra("gruppo_nome", gruppo.nome)
            intent.putExtra("gruppo_descrizione", gruppo.descrizione)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        btnVaiScheda = findViewById(R.id.btnVaiASchedaPersonalizzata)

        btnVaiScheda.setOnClickListener {
            val nomeScheda = "Scheda Temporanea"

            val eserciziSelezionati = SchedaManager.getScheda(nomeScheda, this)

            if (eserciziSelezionati.isNotEmpty()) {
                SchedaManager.schedePersonalizzate[nomeScheda] = eserciziSelezionati.toMutableList()
                SchedaManager.salvaScheda(nomeScheda, this)
            }

            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()

            val intent = Intent(this, SchedaPersonalizzataActivity::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }

        aggiornaVisibilitàPulsante()

        toolbarSchedaGruppiMuscolari.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        aggiornaVisibilitàPulsante()
    }

    private fun aggiornaVisibilitàPulsante() {
        val esercizi = SchedaManager.schedePersonalizzate[nomeScheda]
        btnVaiScheda.visibility = if (!esercizi.isNullOrEmpty()) View.VISIBLE else View.GONE
    }
}