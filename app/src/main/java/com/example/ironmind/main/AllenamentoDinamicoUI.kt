package com.example.ironmind.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
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

            val prefsStats = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
            val schedeCompletate = prefsStats.getStringSet("schede_completate", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            if (!schedeCompletate.contains(nomeScheda)) {
                Log.d("DEBUG_SALVATAGGIO", "Finisci: sto salvando la scheda: $nomeScheda")
                schedeCompletate.add(nomeScheda)
                prefsStats.edit().putStringSet("schede_completate", schedeCompletate).apply()
            } else {
                Log.d("DEBUG_SALVATAGGIO", "Finisci: scheda già salvata: $nomeScheda")
            }

            // ✅ Pulisce schede errate
            val schedeValide = setOf(
                "Principiante 1", "Principiante 2", "Principiante 3",
                "Intermedio 1", "Intermedio 2", "Intermedio 3",
                "Esperto 1", "Esperto 2", "Esperto 3"
            )
            pulisciSchedeCompletate(prefsStats, schedeValide)

            aggiornaEControllaPremi()

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

            // 🔥 Salva la scheda completata
            val prefsStats = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
            val schedeCompletate = prefsStats.getStringSet("schede_completate", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            if (!schedeCompletate.contains(nomeScheda)) {
                Log.d("DEBUG_SALVATAGGIO", "Sto salvando la scheda: $nomeScheda")
                schedeCompletate.add(nomeScheda)
                prefsStats.edit().putStringSet("schede_completate", schedeCompletate).apply()
            } else {
                Log.d("DEBUG_SALVATAGGIO", "Scheda già salvata: $nomeScheda")
            }

            // ✅ Pulisce eventuali schede errate salvate in passato
            val schedeValide = setOf(
                "Principiante 1", "Principiante 2", "Principiante 3",
                "Intermedio 1", "Intermedio 2", "Intermedio 3",
                "Esperto 1", "Esperto 2", "Esperto 3"
            )
            pulisciSchedeCompletate(prefsStats, schedeValide)

            aggiornaEControllaPremi()

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

        // ✅ Se ci sono set salvati (peso, ripetizioni), li ripristina
        if (esercizio.pesoPerSet.isNotEmpty() && esercizio.ripetizioniPerSet.isNotEmpty()) {
            val numSet = minOf(
                esercizio.pesoPerSet.size,
                esercizio.ripetizioniPerSet.size
            )

            for (i in 0 until numSet) {
                val peso = esercizio.pesoPerSet[i]
                val rip = esercizio.ripetizioniPerSet[i]
                aggiungiCardSet(peso, rip)
            }
        } else {
            // ✅ Altrimenti usa i valori di default
            repeat(setIniziali) { aggiungiCardSet() }
        }
    }

    private fun aggiungiCardSet(peso: Float = 10f, ripetizioni: Int = 10) {
        val setView = LayoutInflater.from(this).inflate(R.layout.item_set_allenamento, layoutContainer, false)

        val valoreRip = setView.findViewById<TextView>(R.id.txtRipetizioni)
        val valorePeso = setView.findViewById<TextView>(R.id.txtPeso)
        val btnRipPiù = setView.findViewById<Button>(R.id.btnAumentaRipetizioni)
        val btnRipMeno = setView.findViewById<Button>(R.id.btnDiminuisciRipetizioni)
        val btnPesoPiù = setView.findViewById<Button>(R.id.btnAumentaPeso)
        val btnPesoMeno = setView.findViewById<Button>(R.id.btnDiminuisciPeso)
        val btnEliminaSet = setView.findViewById<Button>(R.id.btnEliminaSet)
        val btnConcludiSet = setView.findViewById<Button>(R.id.btnConcludiSet)
        val timerText = setView.findViewById<TextView>(R.id.timerSetTextView)

        valoreRip.text = ripetizioni.toString()
        valorePeso.text = String.format("%.1f kg", peso)

        val esercizioCorrente = scheda[esercizioCorrenteIndex]

        // 🔹 Aggiungi nuovi valori alle liste (in posizione finale)
        esercizioCorrente.ripetizioniPerSet = esercizioCorrente.ripetizioniPerSet + ripetizioni
        esercizioCorrente.pesoPerSet = esercizioCorrente.pesoPerSet + peso
        esercizioCorrente.tempoRecuperoPerSet = esercizioCorrente.tempoRecuperoPerSet + tempoRecuperoSec
        esercizioCorrente.setCompletati = esercizioCorrente.ripetizioniPerSet.size
        salvaSchedaCorrente()

        val setIndex = layoutContainer.childCount // posizione attuale del set
        setView.findViewById<TextView>(R.id.txtNumeroSet).text = "Set ${setIndex + 1}"

        btnRipPiù.setOnClickListener {
            val nuovoRip = valoreRip.text.toString().toInt() + 1
            valoreRip.text = nuovoRip.toString()
            esercizioCorrente.ripetizioniPerSet = esercizioCorrente.ripetizioniPerSet.toMutableList().apply {
                this[setIndex] = nuovoRip
            }
            salvaSchedaCorrente()
        }

        btnRipMeno.setOnClickListener {
            val rip = valoreRip.text.toString().toInt()
            if (rip > 1) {
                val nuovoRip = rip - 1
                valoreRip.text = nuovoRip.toString()
                esercizioCorrente.ripetizioniPerSet = esercizioCorrente.ripetizioniPerSet.toMutableList().apply {
                    this[setIndex] = nuovoRip
                }
                salvaSchedaCorrente()
            }
        }

        btnPesoPiù.setOnClickListener {
            val nuovoPeso = valorePeso.text.toString().toCleanFloat() + pesoIncremento
            valorePeso.text = String.format("%.1f kg", nuovoPeso)
            esercizioCorrente.pesoPerSet = esercizioCorrente.pesoPerSet.toMutableList().apply {
                this[setIndex] = nuovoPeso
            }
            salvaSchedaCorrente()
        }

        btnPesoMeno.setOnClickListener {
            val pesoAttuale = valorePeso.text.toString().toCleanFloat()
            if (pesoAttuale - pesoIncremento >= 0f) {
                val nuovoPeso = pesoAttuale - pesoIncremento
                valorePeso.text = String.format("%.1f kg", nuovoPeso)
                esercizioCorrente.pesoPerSet = esercizioCorrente.pesoPerSet.toMutableList().apply {
                    this[setIndex] = nuovoPeso
                }
                salvaSchedaCorrente()
            }
        }

        btnEliminaSet.setOnClickListener {
            layoutContainer.removeView(setView)

            esercizioCorrente.ripetizioniPerSet = esercizioCorrente.ripetizioniPerSet.toMutableList().apply {
                removeAt(setIndex)
            }
            esercizioCorrente.pesoPerSet = esercizioCorrente.pesoPerSet.toMutableList().apply {
                removeAt(setIndex)
            }
            esercizioCorrente.tempoRecuperoPerSet = esercizioCorrente.tempoRecuperoPerSet.toMutableList().apply {
                removeAt(setIndex)
            }
            esercizioCorrente.setCompletati = esercizioCorrente.ripetizioniPerSet.size
            salvaSchedaCorrente()
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

    private fun salvaSchedaCorrente() {
        SchedaManager.schedePersonalizzate[nomeScheda] = scheda
        SchedaManager.salvaScheda(nomeScheda, this)

        val durataMillis = System.currentTimeMillis() - startTime
        val durataSec = durataMillis / 1000
        val totaleSet = scheda.sumOf { it.setCompletati }

        val prefs = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
        prefs.edit()
            .putLong("durata_totale_sec", durataSec)
            .putInt("numero_totale_set", totaleSet)
            .apply()
    }

    private fun aggiornaEControllaPremi() {
        val prefs = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
        val editor = prefs.edit()

        // Statistiche totali
        val allenamenti = prefs.getInt("allenamenti_totali", 0) + 1
        val serie = prefs.getInt("serie_totali", 0) + scheda.sumOf { it.setCompletati }
        val ripetizioni = prefs.getInt("ripetizioni_totali", 0) + scheda.sumOf { it.ripetizioniPerSet.sum() }
        val pesoTotale = prefs.getFloat("peso_totale", 0f) + scheda.sumOf { it.pesoPerSet.sumOf { peso -> peso.toDouble() } }.toFloat()

        editor.putInt("allenamenti_totali", allenamenti)
        editor.putInt("serie_totali", serie)
        editor.putInt("ripetizioni_totali", ripetizioni)
        editor.putFloat("peso_totale", pesoTotale)
        editor.apply()

        val premiPrefs = getSharedPreferences("premi_sbloccati", MODE_PRIVATE)
        val premiEditor = premiPrefs.edit()

        // Aggiorna le date degli allenamenti
        val allenamentoDates = prefs.getStringSet("allenamento_date", emptySet())?.toMutableSet() ?: mutableSetOf()
        val oggi = java.time.LocalDate.now()
        val formatter = java.time.format.DateTimeFormatter.ISO_DATE
        allenamentoDates.add(oggi.format(formatter))
        prefs.edit().putStringSet("allenamento_date", allenamentoDates).apply()

        // Calcola settimana attuale
        val currentWeek = oggi.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val currentYear = oggi.year

        // Conta quanti allenamenti in questa settimana (lun-dom)
        val allenamentiSettimana = allenamentoDates.count { dateStr ->
            val date = java.time.LocalDate.parse(dateStr, formatter)
            val week = date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR)
            val year = date.year
            week == currentWeek && year == currentYear
        }

        // Salva settimane attive (almeno 3 allenamenti)
        val settimaneCon3Allenamenti = prefs.getStringSet("settimane_attive", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (allenamentiSettimana >= 3) {
            settimaneCon3Allenamenti.add("$currentYear-$currentWeek")
            prefs.edit().putStringSet("settimane_attive", settimaneCon3Allenamenti).apply()
        }

        val settimaneAttiveCount = settimaneCon3Allenamenti.size
        Log.d("DEBUG_PREMI", "Allenamenti in settimana: $allenamentiSettimana")
        Log.d("DEBUG_PREMI", "Settimane attive: $settimaneCon3Allenamenti")
        Log.d("DEBUG_PREMI", "Premio Settimana Attiva sbloccato? ${premiPrefs.getBoolean("Settimana Attiva", false)}")
        val costanza = settimaneAttiveCount >= 4

        // Controlla schede completate per livelli
        val schedeCompletate = prefs.getStringSet("schede_completate", emptySet()) ?: emptySet()

        Log.d("DEBUG_SCHEDE", "Schede completate: $schedeCompletate")

        val principianteCompleto = listOf("Principiante 1", "Principiante 2", "Principiante 3").all { it in schedeCompletate }
        val intermedioCompleto = listOf("Intermedio 1", "Intermedio 2", "Intermedio 3").all { it in schedeCompletate }
        val espertoCompleto = listOf("Esperto 1", "Esperto 2", "Esperto 3").all { it in schedeCompletate }

        // Sblocca premi
        PremiRepository.listaPremi.forEach { premio ->
            if (!premio.sbloccato) {
                when (premio.titolo) {
                    "Primo Allenamento" -> if (allenamenti >= 1) premio.sbloccato = true
                    "Palestrato" -> if (allenamenti >= 100) premio.sbloccato = true
                    "Serie su Serie" -> if (serie >= 1000) premio.sbloccato = true
                    "Ripeti" -> if (ripetizioni >= 1000) premio.sbloccato = true
                    "Sollevatore" -> if (pesoTotale >= 1000f) premio.sbloccato = true
                    "Settimana Attiva" -> if (settimaneCon3Allenamenti.contains("$currentYear-$currentWeek")) premio.sbloccato = true
                    "Costanza" -> if (costanza) premio.sbloccato = true
                    "Principiante!" -> if (principianteCompleto) premio.sbloccato = true
                    "Intermedio!" -> if (intermedioCompleto) premio.sbloccato = true
                    "Esperto!" -> if (espertoCompleto) premio.sbloccato = true
                }
                if (premio.sbloccato) {
                    premiEditor.putBoolean(premio.titolo, true)
                }
            }
        }

        premiEditor.apply()
    }

    private fun pulisciSchedeCompletate(prefsStats: SharedPreferences, schedeValide: Set<String>) {
        val completate = prefsStats.getStringSet("schede_completate", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val nuoveCompletate = completate.filter { it in schedeValide }.toMutableSet()
        prefsStats.edit().putStringSet("schede_completate", nuoveCompletate).apply()
    }
}