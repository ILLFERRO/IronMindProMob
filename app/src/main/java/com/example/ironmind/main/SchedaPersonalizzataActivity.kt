package com.example.ironmind.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.SchedaPersonalizzataViewModel

class SchedaPersonalizzataActivity : AppCompatActivity() {

    private val viewModel: SchedaPersonalizzataViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnComincia: Button
    private lateinit var adapter: EserciziStaticiAdapter
    private lateinit var nomeScheda: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheda_personalizzata)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_scheda_personalizzata)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        recyclerView = findViewById(R.id.recyclerViewScheda)
        btnComincia = findViewById(R.id.btnCominciaAllenamento)
        recyclerView.layoutManager = LinearLayoutManager(this)

        nomeScheda = intent.getStringExtra("nomeScheda") ?: "Scheda Temporanea"
        Log.d("SchedaPersonalizzata", "Nome scheda da intent: $nomeScheda")

        val esercizi = viewModel.caricaScheda(nomeScheda, this)
        adapter = EserciziStaticiAdapter(esercizi)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btnSalvaScheda).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_salva_scheda, null)
                val input = dialogView.findViewById<EditText>(R.id.nomeSchedaEditText)

                AlertDialog.Builder(this@SchedaPersonalizzataActivity)
                    .setTitle("Salva la tua scheda")
                    .setView(dialogView)
                    .setPositiveButton("Salva") { _, _ ->
                        val nuovoNome = input.text.toString().trim()
                        if (nuovoNome.isNotEmpty()) {
                            val successo = viewModel.salvaSchedaConNome(this@SchedaPersonalizzataActivity, nomeScheda, nuovoNome)
                            if (successo) {
                                nomeScheda = nuovoNome
                                Toast.makeText(this@SchedaPersonalizzataActivity, "Scheda salvata come \"$nuovoNome\"", Toast.LENGTH_SHORT).show()
                                aggiornaLista()
                            } else {
                                Toast.makeText(this@SchedaPersonalizzataActivity, "Errore nel salvataggio", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@SchedaPersonalizzataActivity, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Annulla", null)
                    .show()
            }
        }

        btnComincia.apply {
            visibility = if (esercizi.isEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                val nomeSalvato = viewModel.getNomeSchedaSalvata(this@SchedaPersonalizzataActivity)
                val intent = Intent(this@SchedaPersonalizzataActivity, AllenamentoDinamicoUI::class.java)
                intent.putExtra("nomeScheda", nomeSalvato ?: nomeScheda)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        aggiornaLista()
    }

    private fun aggiornaLista() {
        val nuoviEsercizi = viewModel.caricaScheda(nomeScheda, this)
        adapter = EserciziStaticiAdapter(nuoviEsercizi)
        recyclerView.adapter = adapter
        btnComincia.visibility = if (nuoviEsercizi.isEmpty()) View.GONE else View.VISIBLE
    }
}