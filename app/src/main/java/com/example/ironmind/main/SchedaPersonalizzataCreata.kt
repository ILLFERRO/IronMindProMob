package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.SchedaPersonalizzataCreataViewModel

class SchedaPersonalizzataCreata : AppCompatActivity() {

    private val viewModel: SchedaPersonalizzataCreataViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EserciziStaticiAdapter
    private lateinit var btnElimina: Button
    private lateinit var nomeScheda: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheda_personalizzata_creata)

        nomeScheda = intent.getStringExtra("nomeScheda") ?: return finish()

        val toolbar = findViewById<Toolbar>(R.id.toolbar_scheda_creata)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = nomeScheda
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        findViewById<TextView>(R.id.titoloScheda).text = nomeScheda

        val (dataAllenamento, durataAllenamento) = viewModel.getStatisticheAllenamento(nomeScheda, this)
        findViewById<TextView>(R.id.txtDataAllenamento).text = "Ultimo allenamento: $dataAllenamento"
        findViewById<TextView>(R.id.txtDurataAllenamento).text = "Durata: $durataAllenamento"

        recyclerView = findViewById(R.id.recyclerViewEsercizi)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val esercizi = viewModel.caricaEsercizi(nomeScheda, this)
        adapter = EserciziStaticiAdapter(esercizi)
        recyclerView.adapter = adapter

        btnElimina = findViewById(R.id.btnEliminaScheda)
        btnElimina.setOnClickListener {
            viewModel.eliminaScheda(nomeScheda, this)
            finish()
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val eserciziSalvati = viewModel.caricaEsercizi(nomeScheda, this)
            if (eserciziSalvati.isNotEmpty()) {
                viewModel.salvaNomeSchedaCorrente(this, nomeScheda)
                val intent = Intent(this, AllenamentoDinamicoUI::class.java)
                intent.putExtra("nomeScheda", nomeScheda)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Nessun esercizio trovato per questa scheda", Toast.LENGTH_SHORT).show()
            }
        }
    }
}