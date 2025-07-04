package com.example.ironmind.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity
import com.example.ironmind.viewmodel.AllenamentoCompletatoViewModel

class AllenamentoCompletato : AppCompatActivity() {

    private lateinit var eserciziCompletati: ArrayList<Esercizio>
    private lateinit var nomeScheda: String
    private val viewModel: AllenamentoCompletatoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allenamento_completato)

        @Suppress("DEPRECATION")
        eserciziCompletati = intent.getSerializableExtra("eserciziCompletati") as? ArrayList<Esercizio> ?: arrayListOf()
        nomeScheda = intent.getStringExtra("nomeScheda") ?: "Scheda Temporanea"

        val btnSalva = findViewById<Button>(R.id.btnSalvaScheda)

        btnSalva.setOnClickListener {
            mostraDialogSalvataggioScheda()
        }
    }

    private fun mostraDialogSalvataggioScheda() {
        if (nomeScheda != "Scheda Temporanea") {
            // Scheda già nominata → salvataggio diretto
            viewModel.salvaSchedaTemporanea(
                eserciziCompletati = eserciziCompletati,
                context = this,
                nomeSalvato = nomeScheda
            ) {
                startActivity(Intent(this, DashBoardActivity::class.java))
                finish()
            }
        } else {
            // Scheda temporanea → mostra dialog per assegnare un nome
            val dialogView = layoutInflater.inflate(R.layout.dialog_salva_scheda, null)
            val nomeSchedaInput = dialogView.findViewById<EditText>(R.id.nomeSchedaEditText)

            AlertDialog.Builder(this)
                .setTitle("Dai un nome alla tua scheda")
                .setView(dialogView)
                .setPositiveButton("Salva") { _, _ ->
                    val nomeSchedaInserito = nomeSchedaInput.text.toString().trim()
                    if (nomeSchedaInserito.isNotEmpty()) {
                        viewModel.salvaSchedaDefinitiva(
                            nomeScheda = nomeSchedaInserito,
                            eserciziCompletati = eserciziCompletati,
                            context = this,
                            onSuccess = {
                                startActivity(Intent(this, DashBoardActivity::class.java))
                                finish()
                            },
                            onError = { errore ->
                                Toast.makeText(this, errore, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(this, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Annulla", null)
                .show()
        }
    }
}