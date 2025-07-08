package com.example.ironmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Utils.SchedaManager

class DettaglioAllenamentoPrincipiante1ViewModel(application: Application) : AndroidViewModel(application) {

    val nomeScheda = "Principiante 1"

    fun getEsercizi(): List<Esercizio> {
        return listOf(
            Esercizio(
                nome = "Chest Press",
                descrizione = "Esercizio per i pettorali su macchina",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 40f
            ),
            Esercizio(
                nome = "Lat Machine",
                descrizione = "Trazioni assistite per dorsali",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 35f
            ),
            Esercizio(
                nome = "Leg Press",
                descrizione = "Spinta delle gambe sulla pressa orizzontale",
                setPrevisti = 3,
                ripetizioniPreviste = 15,
                usaPeso = true,
                pesoPredefinito = 80f
            ),
            Esercizio(
                nome = "Shoulder Press",
                descrizione = "Spinta sopra la testa per le spalle",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 25f
            ),
            Esercizio(
                nome = "Crunch Su Tappetino",
                descrizione = "Addominali a corpo libero",
                setPrevisti = 3,
                ripetizioniPreviste = 15,
                usaPeso = false
            )
        )
    }

    fun salvaScheda() {
        val context = getApplication<Application>().applicationContext
        SchedaManager.schedePersonalizzate[nomeScheda] = getEsercizi().toMutableList()
        SchedaManager.salvaScheda(nomeScheda, context)

        val prefs = context.getSharedPreferences("settings", Application.MODE_PRIVATE)
        prefs.edit().putString("scheda_salvata_nome", nomeScheda).apply()
    }
}