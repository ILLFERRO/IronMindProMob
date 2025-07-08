package com.example.ironmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class DettaglioAllenamentoEsperto1ViewModel(application: Application) : AndroidViewModel(application) {

    fun getEsercizi(): List<Esercizio> = listOf(
        Esercizio("Panca Piana Con Bilanciere", "4 serie da 8 ripetizioni", 4, 8, true, 70f),
        Esercizio("Shoulder Press Con Manubri", "4 serie da 10 ripetizioni", 4, 10, true, 25f),
        Esercizio("Chest Press", "3 serie da 10 ripetizioni", 3, 10, true, 40f),
        Esercizio("Alzate Laterali Con Manubri", "3 serie da 12 ripetizioni", 3, 12, true, 10f),
        Esercizio("Dip Alle Parallele assistiti", "3 serie da 12 ripetizioni", 3, 12, false)
    )

    fun salvaScheda(nomeScheda: String, esercizi: List<Esercizio>) {
        SchedaManager.schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        SchedaManager.salvaScheda(nomeScheda, getApplication())
    }
}