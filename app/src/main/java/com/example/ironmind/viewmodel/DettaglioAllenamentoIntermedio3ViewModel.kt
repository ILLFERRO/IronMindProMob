package com.example.ironmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class DettaglioAllenamentoIntermedio3ViewModel(application: Application) : AndroidViewModel(application) {

    fun getEsercizi(): List<Esercizio> = listOf(
        Esercizio("Leg Press", "4 serie da 12 ripetizioni", 4, 12, true, 80f),
        Esercizio("Leg Curl", "3 serie da 15 ripetizioni", 3, 15, true, 35f),
        Esercizio("Affondi Con Manubri", "3 serie da 10 ripetizioni per gamba", 3, 10, true, 15f),
        Esercizio("Crunch Su Tappetino", "3 serie da 20 ripetizioni", 3, 20, false),
        Esercizio("Plank", "3 serie da 30 secondi", 3, 30, false)
    )

    fun salvaScheda(nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, getApplication())
    }
}