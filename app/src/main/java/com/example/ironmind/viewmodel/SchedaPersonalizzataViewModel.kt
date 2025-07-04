package com.example.ironmind.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager

class SchedaPersonalizzataViewModel : ViewModel() {

    fun caricaScheda(nome: String, context: Context): MutableList<Esercizio> {
        SchedaManager.caricaSchedaDaStorage(nome, context)
        return SchedaManager.getScheda(nome, context).toMutableList()
    }

    fun salvaSchedaConNome(
        context: Context,
        nomeAttuale: String,
        nuovoNome: String
    ): Boolean {
        val esercizi = SchedaManager.getScheda(nomeAttuale, context)
        if (esercizi.isEmpty()) return false

        SchedaManager.schedePersonalizzate[nuovoNome] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nuovoNome, context)

        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putString("scheda_salvata_nome", nuovoNome)
            .apply()

        return true
    }

    fun getNomeSchedaSalvata(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("scheda_salvata_nome", null)
    }
}