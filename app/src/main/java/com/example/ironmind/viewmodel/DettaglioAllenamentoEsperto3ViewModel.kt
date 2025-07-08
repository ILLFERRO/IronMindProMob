package com.example.ironmind.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class DettaglioAllenamentoEsperto3ViewModel : ViewModel() {

    fun getEsercizi(): List<Esercizio> {
        return listOf(
            Esercizio("Squat Con Bilanciere", "4 serie da 8 ripetizioni", 4, 8, true, 90f),
            Esercizio("Affondi Camminati Con Manubri", "3 serie da 12 ripetizioni per gamba", 3, 12, true, 15f),
            Esercizio("Leg Curl Da Sdraiato", "3 serie da 15 ripetizioni", 3, 15, true, 35f),
            Esercizio("Macchina Crunch Con Carico A Piastra", "3 serie da 20 ripetizioni", 3, 20, false),
            Esercizio("Russian Twist Con Palla Medica", "3 serie da 40 ripetizioni (20 per lato)", 3, 40, false)
        )
    }

    fun salvaScheda(context: Context, nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, context)

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()
    }
}