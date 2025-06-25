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
import android.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import android.util.Log

class SchedaPersonalizzataActivity : AppCompatActivity() {

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

        // ✅ Recupera nome della scheda
        nomeScheda = intent.getStringExtra("nomeScheda") ?: "Scheda Temporanea"
        Log.d("SchedaPersonalizzata", "Nome scheda da intent: $nomeScheda")

        // ✅ Carica la scheda da memoria
        SchedaManager.caricaSchedaDaStorage(nomeScheda, this)

        val esercizi = SchedaManager.getScheda(nomeScheda, this).toMutableList()
        Log.d("SchedaPersonalizzata", "Esercizi caricati da storage: ${esercizi.size}")
        adapter = EserciziStaticiAdapter(esercizi)
        recyclerView.adapter = adapter

        val btnSalvaScheda = findViewById<Button>(R.id.btnSalvaScheda)
        btnSalvaScheda.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_salva_scheda, null)
            val nomeSchedaInput = dialogView.findViewById<EditText>(R.id.nomeSchedaEditText)

            AlertDialog.Builder(this)
                .setTitle("Salva la tua scheda")
                .setView(dialogView)
                .setPositiveButton("Salva") { _, _ ->
                    val nomeNuovo = nomeSchedaInput.text.toString().trim()
                    if (nomeNuovo.isNotEmpty()) {
                        // Ottieni gli esercizi correnti nella scheda temporanea
                        val eserciziCorrenti = SchedaManager.getScheda(nomeScheda, this)

                        // Aggiorna il manager con il nuovo nome
                        SchedaManager.schedePersonalizzate[nomeNuovo] = eserciziCorrenti.toMutableList()

                        // Salva la scheda con il nuovo nome
                        SchedaManager.salvaScheda(nomeNuovo, this)

                        // Aggiorna il nomeScheda locale (quello usato nell'activity)
                        nomeScheda = nomeNuovo

                        // Salva nome nuovo nelle SharedPreferences
                        getSharedPreferences("settings", MODE_PRIVATE)
                            .edit()
                            .putString("scheda_salvata_nome", nomeNuovo)
                            .apply()

                        Toast.makeText(this, "Scheda salvata come \"$nomeNuovo\"", Toast.LENGTH_SHORT).show()

                        // Aggiorna l'adapter e la UI se vuoi
                        aggiornaLista()
                    } else {
                        Toast.makeText(this, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Annulla", null)
                .show()
        }

        btnSalvaScheda.visibility = View.VISIBLE
        btnComincia.visibility = if (esercizi.isEmpty()) View.GONE else View.VISIBLE

        btnComincia.setOnClickListener {
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            val nomeSchedaSalvata = prefs.getString("scheda_salvata_nome", null)
            Log.d("SchedaPersonalizzata", "Nome scheda salvata da prefs: $nomeSchedaSalvata")

            val eserciziCorrenti = SchedaManager.getScheda(nomeSchedaSalvata ?: nomeScheda, this)
            Log.d("SchedaPersonalizzata", "Esercizi correnti prima di cominciare allenamento: ${eserciziCorrenti.size}")

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            if (!nomeSchedaSalvata.isNullOrEmpty()) {
                intent.putExtra("nomeScheda", nomeSchedaSalvata)
            } else {
                intent.putExtra("nomeScheda", nomeScheda) // usa "Scheda Temporanea"
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        aggiornaLista()
    }

    private fun aggiornaLista() {
        SchedaManager.caricaSchedaDaStorage(nomeScheda, this) // ✅ Ricarica anche qui nel dubbio
        val nuoviEsercizi = SchedaManager.getScheda(nomeScheda, this)
        Log.d("SchedaPersonalizzata", "Esercizi ricaricati in aggiornaLista: ${nuoviEsercizi.size}")
        adapter = EserciziStaticiAdapter(nuoviEsercizi.toMutableList())
        recyclerView.adapter = adapter
        btnComincia.visibility = if (nuoviEsercizi.isEmpty()) View.GONE else View.VISIBLE
    }
}