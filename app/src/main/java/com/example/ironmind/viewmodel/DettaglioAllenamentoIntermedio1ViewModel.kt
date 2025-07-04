package com.example.ironmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager

class DettaglioAllenamentoIntermedio1ViewModel(application: Application) : AndroidViewModel(application) {

    fun getEsercizi(): List<Esercizio> = listOf(
        Esercizio("Chest Press", "4 serie da 10 ripetizioni", 4, 10, true, 40f),
        Esercizio("Shoulder Press", "3 serie da 12 ripetizioni", 3, 12, true, 25f),
        Esercizio("Croci Con Manubri Su Panca", "3 serie da 12 ripetizioni", 3, 12, true, 10f),
        Esercizio("Pushdown Tricipiti Cavi", "3 serie da 15 ripetizioni", 3, 15, true, 20f),
        Esercizio("Estensioni Tricipiti Manubrio", "3 serie da 12 ripetizioni", 3, 12, true, 12f)
    )

    fun salvaScheda(nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, getApplication())
    }
}