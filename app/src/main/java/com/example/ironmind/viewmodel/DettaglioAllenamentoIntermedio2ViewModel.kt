package com.example.ironmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager

class DettaglioAllenamentoIntermedio2ViewModel(application: Application) : AndroidViewModel(application) {

    fun getEsercizi(): List<Esercizio> = listOf(
        Esercizio("Lat Machine Presa Larga", "4 serie da 10 ripetizioni", 4, 10, true, 40f),
        Esercizio("Rematore Manubrio", "3 serie da 12 ripetizioni", 3, 12, true, 25f),
        Esercizio("Pulley Basso", "3 serie da 12 ripetizioni", 3, 12, true, 30f),
        Esercizio("Curl Manubri", "3 serie da 12 ripetizioni", 3, 12, true, 10f),
        Esercizio("Curl Con Bilanciere EZ", "3 serie da 10 ripetizioni", 3, 10, true, 15f)
    )

    fun salvaScheda(nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, getApplication())
    }
}