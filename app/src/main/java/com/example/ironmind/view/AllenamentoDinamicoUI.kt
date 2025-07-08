package com.example.ironmind.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ironmind.R
import com.example.ironmind.Utils.RecuperoTimerManager
import com.example.ironmind.viewmodel.AllenamentoDinamicoUIViewModel

fun String.toCleanFloat(): Float {
    return this.replace(" kg", "").replace(",", ".").trim().toFloat()
}

class AllenamentoDinamicoUI : AppCompatActivity() {

    private val viewModel: AllenamentoDinamicoUIViewModel by viewModels()

    private lateinit var layoutContainer: LinearLayout
    private lateinit var titoloEsercizio: TextView
    private lateinit var tempoRecupero: TextView
    private lateinit var btnAggiungiSet: Button
    private lateinit var btnProssimoEsercizio: Button
    private lateinit var btnTerminaAllenamento: Button

    private lateinit var timerTextView: TextView
    private lateinit var btnFinisciAllenamento: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allenamento_dinamico_ui)

        val nomeIntent = intent.getStringExtra("nomeScheda").orEmpty()
        viewModel.inizializzaScheda(nomeIntent)

        timerTextView = findViewById(R.id.timerAllenamento)
        btnFinisciAllenamento = findViewById(R.id.btnFinisciAllenamento)
        layoutContainer = findViewById(R.id.recyclerViewSet)
        titoloEsercizio = findViewById(R.id.titoloEsercizio)
        tempoRecupero = findViewById(R.id.timerRecupero)
        btnAggiungiSet = findViewById(R.id.btnAggiungiSet)
        btnProssimoEsercizio = findViewById(R.id.btnProssimoEsercizio)
        btnTerminaAllenamento = Button(this).apply {
            text = "Termina Allenamento"
            visibility = Button.GONE
            setBackgroundColor(ContextCompat.getColor(context, R.color.purple_700))
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
        }

        findViewById<LinearLayout>(R.id.layoutBottom).addView(btnTerminaAllenamento)

        viewModel.tempoAllenamento.observe(this) { seconds ->
            val min = seconds / 60
            val sec = seconds % 60
            timerTextView.text = String.format("%02d:%02d", min, sec)
        }

        viewModel.nomeScheda.observe(this) { nome ->
            title = nome
        }

        viewModel.scheda.observe(this) {
            mostraEsercizio()
        }

        viewModel.tempoRecuperoSec.observe(this) {
            aggiornaTestoRecupero()
        }

        tempoRecupero.setOnClickListener {
            mostraDialogModificaRecupero()
        }

        btnFinisciAllenamento.setOnClickListener {
            completaAllenamento()
        }

        btnAggiungiSet.setOnClickListener {
            viewModel.aggiungiSet(10f, 10, viewModel.tempoRecuperoSec.value ?: 60)
            aggiungiCardSet(10f, 10)
        }

        btnProssimoEsercizio.setOnClickListener {
            viewModel.prossimoEsercizio()
            mostraEsercizio()
            if (viewModel.isUltimoEsercizio()) {
                btnProssimoEsercizio.visibility = View.GONE
                btnTerminaAllenamento.visibility = View.VISIBLE
            }
        }

        btnTerminaAllenamento.setOnClickListener {
            completaAllenamento()
        }

        viewModel.avviaTimer()
    }

    private fun completaAllenamento() {
        viewModel.terminaAllenamento()
        viewModel.salvaSchedaCorrente()
        viewModel.aggiornaStatisticheFinali()
        viewModel.aggiornaEControllaPremi()
        val intent = Intent(this, AllenamentoCompletato::class.java)
        intent.putExtra("eserciziCompletati", ArrayList(viewModel.eserciziCompletati.value ?: emptyList()))
        intent.putExtra("nomeScheda", viewModel.nomeScheda.value ?: "Scheda Temporanea")
        startActivity(intent)
        finish()
    }

    private fun mostraDialogModificaRecupero() {
        val dialogView = layoutInflater.inflate(R.layout.modifica_tempo_recupero, null)
        val minutePicker = dialogView.findViewById<NumberPicker>(R.id.minutiPicker)
        val secondPicker = dialogView.findViewById<NumberPicker>(R.id.secondiPicker)

        minutePicker.minValue = 0
        minutePicker.maxValue = 10
        secondPicker.minValue = 0
        secondPicker.maxValue = 59

        val tempo = viewModel.tempoRecuperoSec.value ?: 60
        minutePicker.value = tempo / 60
        secondPicker.value = tempo % 60

        AlertDialog.Builder(this)
            .setTitle("Modifica tempo di recupero")
            .setView(dialogView)
            .setPositiveButton("Salva") { _, _ ->
                viewModel.aggiornaRecupero(minutePicker.value, secondPicker.value)
            }
            .setNegativeButton("Annulla", null)
            .show()
    }

    private fun aggiornaTestoRecupero() {
        val tempo = viewModel.tempoRecuperoSec.value ?: 60
        val min = tempo / 60
        val sec = tempo % 60
        tempoRecupero.text = "Recupero: %02d:%02d".format(min, sec)
    }

    private fun mostraEsercizio() {
        val esercizio = viewModel.getEsercizioCorrente() ?: return
        titoloEsercizio.text = esercizio.nome
        layoutContainer.removeAllViews()

        val numSet = minOf(esercizio.pesoPerSet.size, esercizio.ripetizioniPerSet.size)
        if (numSet > 0) {
            for (i in 0 until numSet) {
                aggiungiCardSet(esercizio.pesoPerSet[i], esercizio.ripetizioniPerSet[i], i)
            }
        } else {
            repeat(viewModel.getSetIniziali()) {
                viewModel.aggiungiSet(10f, 10, viewModel.tempoRecuperoSec.value ?: 60)
            }
            mostraEsercizio()
        }
    }

    private fun aggiungiCardSet(peso: Float = 10f, ripetizioni: Int = 10, indexSet: Int = -1) {
        val setView = LayoutInflater.from(this).inflate(R.layout.item_set_allenamento, layoutContainer, false)
        layoutContainer.addView(setView)

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

        val finalIndexSet = if (indexSet != -1) indexSet else layoutContainer.childCount - 1
        setView.findViewById<TextView>(R.id.txtNumeroSet).text = "Set ${finalIndexSet + 1}"

        btnRipPiù.setOnClickListener {
            val esercizio = viewModel.getEsercizioCorrente()
            val nuovoRip = valoreRip.text.toString().toInt() + 1
            valoreRip.text = nuovoRip.toString()
            val pesoAttuale = valorePeso.text.toString().toCleanFloat()
            if (esercizio != null && finalIndexSet < esercizio.pesoPerSet.size) {
                viewModel.aggiornaSetEsercizio(finalIndexSet, pesoAttuale, nuovoRip)
            }
        }

        btnRipMeno.setOnClickListener {
            val esercizio = viewModel.getEsercizioCorrente()
            val rip = valoreRip.text.toString().toInt()
            if (rip > 1) {
                val nuovoRip = rip - 1
                valoreRip.text = nuovoRip.toString()
                val pesoAttuale = valorePeso.text.toString().toCleanFloat()
                if (esercizio != null && finalIndexSet < esercizio.pesoPerSet.size) {
                    viewModel.aggiornaSetEsercizio(finalIndexSet, pesoAttuale, nuovoRip)
                }
            }
        }

        btnPesoPiù.setOnClickListener {
            val esercizio = viewModel.getEsercizioCorrente()
            val nuovoPeso = valorePeso.text.toString().toCleanFloat() + viewModel.getPesoIncremento()
            valorePeso.text = String.format("%.1f kg", nuovoPeso)
            val ripAttuali = valoreRip.text.toString().toInt()
            if (esercizio != null && finalIndexSet < esercizio.pesoPerSet.size) {
                viewModel.aggiornaSetEsercizio(finalIndexSet, nuovoPeso, ripAttuali)
            }
        }

        btnPesoMeno.setOnClickListener {
            val esercizio = viewModel.getEsercizioCorrente()
            val pesoAttuale = valorePeso.text.toString().toCleanFloat()
            if (pesoAttuale - viewModel.getPesoIncremento() >= 0f) {
                val nuovoPeso = pesoAttuale - viewModel.getPesoIncremento()
                valorePeso.text = String.format("%.1f kg", nuovoPeso)
                val ripAttuali = valoreRip.text.toString().toInt()
                if (esercizio != null && finalIndexSet < esercizio.pesoPerSet.size) {
                    viewModel.aggiornaSetEsercizio(finalIndexSet, nuovoPeso, ripAttuali)
                }
            }
        }

        btnEliminaSet.setOnClickListener {
            val esercizio = viewModel.getEsercizioCorrente()
            if (esercizio != null &&
                finalIndexSet < esercizio.pesoPerSet.size &&
                finalIndexSet < esercizio.ripetizioniPerSet.size &&
                finalIndexSet < esercizio.tempoRecuperoPerSet.size) {

                viewModel.eliminaSet(finalIndexSet)
                mostraEsercizio()
            } else {
                Toast.makeText(this, "Errore nell'eliminazione del set", Toast.LENGTH_SHORT).show()
            }
        }

        btnConcludiSet.setOnClickListener {
            btnConcludiSet.isEnabled = false
            timerText.visibility = View.VISIBLE
            RecuperoTimerManager.avviaTimer(
                context = this,
                durataSecondi = viewModel.tempoRecuperoSec.value ?: 60,
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
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopTimer()
    }
}