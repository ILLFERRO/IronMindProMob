package com.example.ironmind.main

import android.app.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar

class PromemoriaActivity : AppCompatActivity() {

    private lateinit var listaPromemoria: MutableList<Promemoria>
    private lateinit var adapter: PromemoriaAdapter
    private var prossimoId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promemoria)

        // Toolbar
        val toolbarPromemoria = findViewById<Toolbar>(R.id.toolbar_promemoria)
        toolbarPromemoria.title = "Promemoria"
        setSupportActionBar(toolbarPromemoria)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarPromemoria.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Carica i promemoria
        listaPromemoria = PromemoriaManager.carica(this)
        prossimoId = (listaPromemoria.maxOfOrNull { it.id } ?: 0) + 1

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPromemoria)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PromemoriaAdapter(this, listaPromemoria) {
            PromemoriaManager.salva(this, listaPromemoria)
            aggiornaVisibilitaVistaVuota()
        }
        recyclerView.adapter = adapter

        // Pulsante per aggiungere promemoria
        val buttonAdd = findViewById<ImageButton>(R.id.button_add_reminder)
        buttonAdd.setOnClickListener {
            mostraDialogoModifica(null)
        }

        // Mostra o nasconde la vista vuota
        aggiornaVisibilitaVistaVuota()
    }

    fun mostraDialogoModifica(promemoriaEsistente: Promemoria?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_nuovo_promemoria)

        val etNome = dialog.findViewById<EditText>(R.id.etNome)
        val etOra = dialog.findViewById<TextView>(R.id.etOra)
        val spinnerGiorno = dialog.findViewById<Spinner>(R.id.etGiorno)
        val btnSalva = dialog.findViewById<Button>(R.id.btnSalva)

        // Popola lo spinner con i giorni della settimana
        val giorni = resources.getStringArray(R.array.giorni_settimana)
        val adapterGiorni = ArrayAdapter(this, android.R.layout.simple_spinner_item, giorni) //creo un adapter che collega i dati (i giorni) alla vista dello spinner
        adapterGiorni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //imposta come viene visualizzato l'elenco a discesa quando l'utente clicca sullo spinner
        spinnerGiorno.adapter = adapterGiorni //assegna l'adapter allo spinner

        // Variabile per tenere traccia dell'ora scelta
        var oraSelezionata = "08:30"  // default

        // Se modifichi un promemoria esistente, carica i dati
        if (promemoriaEsistente != null) {
            etNome.setText(promemoriaEsistente.nome)
            oraSelezionata = promemoriaEsistente.ora
            etOra.setText(oraSelezionata)
            // Imposta la selezione dello spinner in base al giorno del promemoria
            val index = giorni.indexOf(promemoriaEsistente.giorno)
            if (index >= 0) spinnerGiorno.setSelection(index)
        } else {
            etOra.setText(oraSelezionata)
        }

        // Imposta il click sulla EditText per aprire TimePicker
        etOra.setOnClickListener {
            val parts = oraSelezionata.split(":")
            val ora = parts[0].toInt()
            val minuto = parts[1].toInt()

            val timePickerDialog = TimePickerDialog(
                this,
                { _, h, m ->
                    oraSelezionata = String.format("%02d:%02d", h, m)
                    etOra.setText(oraSelezionata)
                }, ora, minuto, true
            )
            timePickerDialog.show()
        }

        btnSalva.setOnClickListener {
            val nome = etNome.text.toString()
            val ora = oraSelezionata
            val giorno = spinnerGiorno.selectedItem.toString()

            if (nome.isNotEmpty()) {
                if (promemoriaEsistente == null) {
                    val nuovo = Promemoria(prossimoId++, nome, ora, giorno, true)
                    listaPromemoria.add(nuovo)
                    AlarmAvviso.impostaSveglia(this, nuovo)
                } else {
                    promemoriaEsistente.nome = nome
                    promemoriaEsistente.ora = ora
                    promemoriaEsistente.giorno = giorno
                    AlarmAvviso.impostaSveglia(this, promemoriaEsistente)
                }

                PromemoriaManager.salva(this, listaPromemoria)
                adapter.notifyDataSetChanged() //notifica ogni observer al cambiare dei dati
                aggiornaVisibilitaVistaVuota()
                dialog.dismiss() //rimuove il dialog
            } else {
                Toast.makeText(this, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // Funzione per mostrare o nascondere la vista vuota
    private fun aggiornaVisibilitaVistaVuota() {
        val viewVuota = findViewById<LinearLayout>(R.id.Viewvuota)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPromemoria)

        if (listaPromemoria.isEmpty()) {
            viewVuota.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            viewVuota.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}