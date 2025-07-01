package com.example.ironmind.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity

class AllenamentoCompletato : AppCompatActivity() {

    private lateinit var eserciziCompletati: ArrayList<Esercizio>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allenamento_completato)

        @Suppress("DEPRECATION")
        eserciziCompletati = intent.getSerializableExtra("eserciziCompletati") as? ArrayList<Esercizio> ?: arrayListOf() //recupero i dati passati da un'altra Activity. Cerco nell'Intent un dato serializzato chiamato eserciziCompletati, se non esiste lo trasformo in un ArrayList vuoto

        val btnSalva = findViewById<Button>(R.id.btnSalvaScheda)

        btnSalva.setOnClickListener {
            mostraDialogSalvataggioScheda()
        }
    }

    private fun mostraDialogSalvataggioScheda() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val nomeSalvato = prefs.getString("scheda_salvata_nome", "") ?: ""
        val nomeFinale = if (nomeSalvato.isNotEmpty()) nomeSalvato else "Scheda Temporanea"

        if (nomeFinale != "Scheda Temporanea") {
            // 🔁 Salva direttamente se non è temporanea
            SchedaManager.schedePersonalizzate[nomeFinale] = eserciziCompletati
            SchedaManager.salvaScheda(nomeFinale, this)
            salvaSchedaNome(nomeFinale)

            val completati = prefs.getInt("allenamenti_completati", 0)
            prefs.edit()
                .putInt("allenamenti_completati", completati + 1)
                .remove("scheda_salvata_nome") // Pulisce il nome per la prossima volta
                .apply()

            startActivity(Intent(this, DashBoardActivity::class.java))
            finish()
        } else {
            // 🔁 Mostra dialog per dare un nome definitivo
            val dialogView = layoutInflater.inflate(R.layout.dialog_salva_scheda, null)
            val nomeSchedaInput = dialogView.findViewById<EditText>(R.id.nomeSchedaEditText)

            AlertDialog.Builder(this)
                .setTitle("Dai un nome alla tua scheda")
                .setView(dialogView)
                .setPositiveButton("Salva") { _, _ ->
                    val nomeScheda = nomeSchedaInput.text.toString().trim()
                    if (nomeScheda.isNotEmpty()) {
                        SchedaManager.schedePersonalizzate[nomeScheda] = eserciziCompletati
                        SchedaManager.salvaScheda(nomeScheda, this)
                        salvaSchedaNome(nomeScheda)

                        val completati = prefs.getInt("allenamenti_completati", 0)
                        prefs.edit().putInt("allenamenti_completati", completati + 1).apply()

                        startActivity(Intent(this, DashBoardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Annulla", null)
                .show()
        }
    }

    private fun salvaSchedaNome(nomeScheda: String) {
        val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE) //apre SharedPreferences chiamate IronMindPrefs
        val editor = prefs.edit()
        val set = prefs.getStringSet("mieSchedeNomi", mutableSetOf())?.toMutableSet() ?: mutableSetOf() //legge l'insieme (set) dei nomi delle schede già salvate con chiave mieSchedeNomi. Se non trova niente creo un set vuoto ed uso MutableSet perchè il set letto dalle preferenze non è direttamente modificabile
        set.add(nomeScheda)
        editor.putStringSet("mieSchedeNomi", set)
        editor.apply()
    }
}