package com.example.ironmind.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class SchedaPersonalizzataCreataViewModel : ViewModel() {

    fun caricaEsercizi(nomeScheda: String, context: Context): List<Esercizio> {
        SchedaManager.caricaSchedaDaStorage(nomeScheda, context)
        return SchedaManager.getScheda(nomeScheda, context)
    }

    fun getStatisticheAllenamento(nomeScheda: String, context: Context): Pair<String, String> {
        val prefs: SharedPreferences = context.getSharedPreferences("allenamento_stats", Context.MODE_PRIVATE)

        val durataSec = prefs.getLong("durata_$nomeScheda", 0L)
        val dataAllenamento = prefs.getString("ultima_data_$nomeScheda", "N/D") ?: "N/D"

        val minuti = durataSec / 60
        val secondi = durataSec % 60
        val durataFormattata = String.format("%02d:%02d", minuti, secondi)

        return Pair(dataAllenamento, durataFormattata)
    }

    fun eliminaScheda(nomeScheda: String, context: Context) {
        SchedaManager.clearScheda(nomeScheda)
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val schedeNomi = prefs.getStringSet("mieSchedeNomi", mutableSetOf())?.toMutableSet()
        schedeNomi?.remove(nomeScheda)
        prefs.edit()
            .putStringSet("mieSchedeNomi", schedeNomi)
            .remove("scheda_$nomeScheda")
            .apply()
    }

    fun salvaNomeSchedaCorrente(context: Context, nomeScheda: String) {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putString("scheda_salvata_nome", nomeScheda)
            .apply()
    }
}