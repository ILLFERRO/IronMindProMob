package com.example.ironmind.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import android.content.Intent
import android.os.Handler
import android.view.View

fun String.toCleanFloat(): Float {
    return this.replace(" kg", "").replace(",", ".").trim().toFloat()
}

class AllenamentoDinamicoUI : AppCompatActivity() {

    private lateinit var layoutContainer: LinearLayout
    private val eserciziCompletati = mutableListOf<Esercizio>()
    private lateinit var titoloEsercizio: TextView
    private lateinit var tempoRecupero: TextView
    private lateinit var btnAggiungiSet: Button
    private lateinit var btnProssimoEsercizio: Button
    private lateinit var btnTerminaAllenamento: Button

    private lateinit var timerTextView: TextView
    private lateinit var btnFinisciAllenamento: Button
    private var startTime: Long = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private lateinit var nomeScheda: String
    private lateinit var scheda: MutableList<Esercizio>

    private var esercizioCorrenteIndex = 0
    private var pesoIncremento: Float = 2.5f
    private var tempoRecuperoSec: Int = 60
    private var setIniziali: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allenamento_dinamico_ui)

        //Timer
        startTime = System.currentTimeMillis()
        timerTextView = findViewById(R.id.timerAllenamento)
        btnFinisciAllenamento = findViewById(R.id.btnFinisciAllenamento)

        handler = Handler(mainLooper)
        runnable = object : Runnable {
            override fun run() {
                val elapsed = System.currentTimeMillis() - startTime
                val minutes = (elapsed / 1000) / 60
                val seconds = (elapsed / 1000) % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

        // Recupera nome scheda
        val preferenzes = getSharedPreferences("settings", MODE_PRIVATE)
        val nomeSalvato = preferenzes.getString("scheda_salvata_nome", "").orEmpty()
        val nomeIntent = intent.getStringExtra("nomeScheda").orEmpty()
        nomeScheda = if (nomeSalvato.isNotEmpty()) nomeSalvato
        else if (nomeIntent.isNotEmpty()) nomeIntent
        else "Scheda Temporanea"

        scheda = SchedaManager.getScheda(nomeScheda, this).toMutableList()

        if (scheda.isEmpty()) {
            Toast.makeText(this, "La scheda è vuota, impossibile iniziare l'allenamento.", Toast.LENGTH_LONG).show()
            finish()  // o magari torna indietro, chiudi activity
            return
        }

        layoutContainer = findViewById(R.id.recyclerViewSet)
        titoloEsercizio = findViewById(R.id.titoloEsercizio)
        tempoRecupero = findViewById(R.id.timerRecupero)
        btnAggiungiSet = findViewById(R.id.btnAggiungiSet)
        btnProssimoEsercizio = findViewById(R.id.btnProssimoEsercizio)
        btnTerminaAllenamento = Button(this).apply {
            text = "Termina Allenamento"
            visibility = Button.GONE
            setBackgroundColor(resources.getColor(R.color.purple_700))
            setTextColor(resources.getColor(android.R.color.white))
        }

        findViewById<LinearLayout>(R.id.layoutBottom).addView(btnTerminaAllenamento)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        setIniziali = prefs.getString("Set_Default", "3")?.toIntOrNull() ?: 3
        val min = prefs.getInt("riposo_min", 0)
        val sec = prefs.getInt("riposo_sec", 60)
        tempoRecuperoSec = min * 60 + sec

        aggiornaTestoRecupero()

        tempoRecupero.setOnClickListener { mostraDialogModificaRecupero() }

        btnFinisciAllenamento.setOnClickListener {
            for (i in 0..esercizioCorrenteIndex) {
                val esercizio = scheda[i]
                if (!eserciziCompletati.contains(esercizio)) {
                    eserciziCompletati.add(esercizio)
                }
            }

            val intentAllenamentoCompletato = Intent(this, AllenamentoCompletato::class.java)
            intentAllenamentoCompletato.putExtra("eserciziCompletati", ArrayList(eserciziCompletati))
            startActivity(intentAllenamentoCompletato)
            finish()
        }

        btnAggiungiSet.setOnClickListener {
            if (layoutContainer.childCount == 0) {
                aggiungiCardSet()
            } else {
                val lastSet = layoutContainer.getChildAt(layoutContainer.childCount - 1)
                val pesoView = lastSet.findViewById<TextView>(R.id.txtPeso)
                val ripView = lastSet.findViewById<TextView>(R.id.txtRipetizioni)
                val peso = pesoView.text.toString().toCleanFloat()
                val rip = ripView.text.toString().toInt()
                aggiungiCardSet(peso, rip)
            }
        }

        btnProssimoEsercizio.setOnClickListener {
            if (!eserciziCompletati.contains(scheda[esercizioCorrenteIndex])) {
                eserciziCompletati.add(scheda[esercizioCorrenteIndex])
            }

            if (esercizioCorrenteIndex < scheda.size - 1) {
                esercizioCorrenteIndex++
                mostraEsercizio()
            }
            if (esercizioCorrenteIndex == scheda.size - 1) {
                btnProssimoEsercizio.visibility = Button.GONE
                btnTerminaAllenamento.visibility = Button.VISIBLE
            }
        }

        btnTerminaAllenamento.setOnClickListener {
            if (!eserciziCompletati.contains(scheda[esercizioCorrenteIndex])) {
                eserciziCompletati.add(scheda[esercizioCorrenteIndex])
            }

            val intentAllenamentoCompletato = Intent(this, AllenamentoCompletato::class.java)
            intentAllenamentoCompletato.putExtra("eserciziCompletati", ArrayList(eserciziCompletati))
            startActivity(intentAllenamentoCompletato)
            finish()
        }
        mostraEsercizio()
    }

    private fun mostraDialogModificaRecupero() {
        val dialogView = layoutInflater.inflate(R.layout.modifica_tempo_recupero, null)
        val minutePicker = dialogView.findViewById<NumberPicker>(R.id.minutiPicker)
        val secondPicker = dialogView.findViewById<NumberPicker>(R.id.secondiPicker)

        minutePicker.minValue = 0
        minutePicker.maxValue = 10
        secondPicker.minValue = 0
        secondPicker.maxValue = 59

        minutePicker.value = tempoRecuperoSec / 60
        secondPicker.value = tempoRecuperoSec % 60

        AlertDialog.Builder(this)
            .setTitle("Modifica tempo di recupero")
            .setView(dialogView)
            .setPositiveButton("Salva") { _, _ ->
                // ✅ aggiorna solo il tempo LOCALE per questo esercizio
                tempoRecuperoSec = minutePicker.value * 60 + secondPicker.value
                aggiornaTestoRecupero()
            }
            .setNegativeButton("Annulla", null)
            .show()
    }

    private fun aggiornaTestoRecupero() {
        val min = tempoRecuperoSec / 60
        val sec = tempoRecuperoSec % 60
        tempoRecupero.text = "Recupero: %02d:%02d".format(min, sec)
    }

    private fun mostraEsercizio() {
        if (scheda.isEmpty()) {
            // Protezione: se per qualche motivo chiamata con lista vuota
            Toast.makeText(this, "La scheda è vuota.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val esercizio = scheda[esercizioCorrenteIndex]
        supportActionBar?.title = esercizio.nome
        titoloEsercizio.text = esercizio.nome
        layoutContainer.removeAllViews()

        // ✅ Reimposta tempoRecuperoSec ai valori di default salvati nelle impostazioni
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val min = prefs.getInt("riposo_min", 0)
        val sec = prefs.getInt("riposo_sec", 60)
        tempoRecuperoSec = min * 60 + sec
        aggiornaTestoRecupero()

        repeat(setIniziali) { aggiungiCardSet() }
    }

    private fun aggiungiCardSet(peso: Float = 10f, ripetizioni: Int = 10) {
        val setView = LayoutInflater.from(this).inflate(R.layout.item_set_allenamento, layoutContainer, false)

        val titoloSet = setView.findViewById<TextView>(R.id.txtNumeroSet)
        val valoreRip = setView.findViewById<TextView>(R.id.txtRipetizioni)
        val valorePeso = setView.findViewById<TextView>(R.id.txtPeso)
        val btnRipPiù = setView.findViewById<Button>(R.id.btnAumentaRipetizioni)
        val btnRipMeno = setView.findViewById<Button>(R.id.btnDiminuisciRipetizioni)
        val btnPesoPiù = setView.findViewById<Button>(R.id.btnAumentaPeso)
        val btnPesoMeno = setView.findViewById<Button>(R.id.btnDiminuisciPeso)
        val btnEliminaSet = setView.findViewById<Button>(R.id.btnEliminaSet)
        val btnConcludiSet = setView.findViewById<Button>(R.id.btnConcludiSet)
        val timerText = setView.findViewById<TextView>(R.id.timerSetTextView)

        val setNumero = layoutContainer.childCount + 1
        titoloSet.text = "Set $setNumero"
        valoreRip.text = ripetizioni.toString()
        valorePeso.text = String.format("%.1f kg", peso)

        btnRipPiù.setOnClickListener {
            valoreRip.text = (valoreRip.text.toString().toInt() + 1).toString()
        }

        btnRipMeno.setOnClickListener {
            val rip = valoreRip.text.toString().toInt()
            if (rip > 1) valoreRip.text = (rip - 1).toString()
        }

        btnPesoPiù.setOnClickListener {
            val p = valorePeso.text.toString().toCleanFloat() + pesoIncremento
            valorePeso.text = String.format("%.1f kg", p)
        }

        btnPesoMeno.setOnClickListener {
            val p = valorePeso.text.toString().toCleanFloat()
            if (p - pesoIncremento >= 0f) {
                valorePeso.text = String.format("%.1f kg", p - pesoIncremento)
            }
        }

        btnEliminaSet.setOnClickListener {
            layoutContainer.removeView(setView)
        }

        btnConcludiSet.setOnClickListener {
            btnConcludiSet.isEnabled = false
            timerText.visibility = View.VISIBLE

            RecuperoTimerManager.avviaTimer(
                context = this@AllenamentoDinamicoUI,
                durataSecondi = tempoRecuperoSec,
                onTick = { tempoRimanente ->
                    val min = tempoRimanente / 60
                    val sec = tempoRimanente % 60
                    timerText.text = "Recupero: %02d:%02d".format(min, sec)
                },
                onFinish = {
                    timerText.text = "Recupero completato!"
                    layoutContainer.removeView(setView)
                }
            )
        }

        layoutContainer.addView(setView)
    }
}