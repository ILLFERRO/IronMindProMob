package com.example.ironmind.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class AllenamentoCompletatoViewModel(application: Application) : AndroidViewModel(application) {

    fun salvaSchedaDefinitiva(
        nomeScheda: String,
        eserciziCompletati: List<Esercizio>,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (nomeScheda.isBlank()) {
            onError("Inserisci un nome valido")
            return
        }

        SchedaManager.schedePersonalizzate[nomeScheda] = eserciziCompletati.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, context)
        salvaSchedaNome(nomeScheda, context)

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val completati = prefs.getInt("allenamenti_completati", 0)
        prefs.edit()
            .putInt("allenamenti_completati", completati + 1)
            .remove("scheda_salvata_nome")
            .apply()

        onSuccess()
    }

    fun salvaSchedaTemporanea(
        eserciziCompletati: List<Esercizio>,
        context: Context,
        nomeSalvato: String?,
        onSuccess: () -> Unit
    ) {
        val nomeFinale = if (!nomeSalvato.isNullOrEmpty()) nomeSalvato else "Scheda Temporanea"
        SchedaManager.schedePersonalizzate[nomeFinale] = eserciziCompletati.toMutableList()
        SchedaManager.salvaScheda(nomeFinale, context)
        salvaSchedaNome(nomeFinale, context)

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val completati = prefs.getInt("allenamenti_completati", 0)
        prefs.edit()
            .putInt("allenamenti_completati", completati + 1)
            .remove("scheda_salvata_nome")
            .apply()

        onSuccess()
    }

    private fun salvaSchedaNome(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val set = prefs.getStringSet("mieSchedeNomi", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        set.add(nomeScheda)
        prefs.edit().putStringSet("mieSchedeNomi", set).apply()
    }
}