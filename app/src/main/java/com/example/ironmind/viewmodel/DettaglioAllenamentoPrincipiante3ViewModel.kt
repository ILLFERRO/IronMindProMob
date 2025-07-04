package com.example.ironmind.main

import androidx.lifecycle.ViewModel

class DettaglioAllenamentoPrincipiante3ViewModel : ViewModel() {

    fun getEsercizi(): List<Esercizio> {
        return listOf(
            Esercizio(
                nome = "Squat Con Bilanciere",
                descrizione = "Squat completo con bilanciere su schiena",
                setPrevisti = 3,
                ripetizioniPreviste = 15,
                usaPeso = true,
                pesoPredefinito = 50f
            ),
            Esercizio(
                nome = "Tirata Stretta",
                descrizione = "Rematore verticale per schiena e trapezio",
                setPrevisti = 3,
                ripetizioniPreviste = 12,
                usaPeso = true,
                pesoPredefinito = 25f
            ),
            Esercizio(
                nome = "Leg Curl (Macchina)",
                descrizione = "Flessori posteriori con macchina da sdraiato",
                setPrevisti = 3,
                ripetizioniPreviste = 15,
                usaPeso = true,
                pesoPredefinito = 30f
            ),
            Esercizio(
                nome = "Calfe Raise Alla Leg Press",
                descrizione = "Sollevamento polpacci con pressa orizzontale",
                setPrevisti = 3,
                ripetizioniPreviste = 20,
                usaPeso = true,
                pesoPredefinito = 60f
            ),
            Esercizio(
                nome = "Cyclette o Tapis Roulant",
                descrizione = "Attività cardio leggera",
                setPrevisti = 1,
                ripetizioniPreviste = 10, // minuti
                usaPeso = false
            )
        )
    }
}
