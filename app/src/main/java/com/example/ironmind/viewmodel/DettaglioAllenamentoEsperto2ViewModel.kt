package com.example.ironmind.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager

class DettaglioAllenamentoEsperto2ViewModel : ViewModel() {

    fun getEsercizi(): List<Esercizio> {
        return listOf(
            Esercizio("Stacco Da Terra Con Bilanciere", "4 serie da 6 ripetizioni", 4, 6, true, 100f),
            Esercizio("Lat Machine Presa Inversa", "4 serie da 10 ripetizioni", 4, 10, true, 40f),
            Esercizio("Rematore Con Bilanciere", "3 serie da 8 ripetizioni", 3, 8, true, 60f),
            Esercizio("Curl Concentrato Manubrio", "3 serie da 10 ripetizioni", 3, 10, true, 12f),
            Esercizio("Curl Su Panca Inclinata", "3 serie da 12 ripetizioni", 3, 12, true, 10f)
        )
    }

    fun salvaScheda(context: Context, nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, context)

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()
    }
}