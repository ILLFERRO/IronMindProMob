package com.example.ironmind.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ironmind.Model.Esercizio

class DettaglioAllenamentoPrincipiante2ViewModel : ViewModel() {

    fun getEsercizi(): List<Esercizio> {
        return listOf(
            Esercizio(
                nome = "Panca Piana Con Manubri",
                descrizione = "Esercizio per pettorali eseguito con manubri",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 12f
            ),
            Esercizio(
                nome = "Rematore Con Manubri",
                descrizione = "Esercizio per dorsali con manubri",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 14f
            ),
            Esercizio(
                nome = "Alzate Laterali Con Macchinario",
                descrizione = "Isolamento del deltoide laterale con macchina",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 25f
            ),
            Esercizio(
                nome = "Russian Twist con manubrio singolo",
                descrizione = "Esercizio per obliqui con torsione del busto",
                setPrevisti = 3,
                ripetizioniPreviste = 20,
                usaPeso = true,
                pesoPredefinito = 6f
            ),
            Esercizio(
                nome = "Plank Isometrico",
                descrizione = "Tenuta isometrica per il core",
                setPrevisti = 3,
                ripetizioniPreviste = 30,
                usaPeso = false
            )
        )
    }
}