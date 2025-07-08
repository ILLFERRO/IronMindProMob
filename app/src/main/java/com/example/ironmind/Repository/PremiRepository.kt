package com.example.ironmind.Repository

import com.example.ironmind.Model.Premio
import com.example.ironmind.R

object PremiRepository {

    val listaPremi = listOf(
        Premio("Primo Allenamento", "Hai completato il tuo primo allenamento!", R.drawable.premio_icona_1, false),
        Premio("Settimana Attiva", "Hai completato almeno 3 allenamenti in una settimana!", R.drawable.premio_icona_2, false),
        Premio("Costanza", "Hai mantenuto 4 settimane di allenamenti costanti!", R.drawable.premio_icona_3, false),
        Premio("Principiante!", "Hai completato tutti gli allenamenti principiante!", R.drawable.premio_icona_4, false),
        Premio("Intermedio!", "Hai completato tutti gli allenamenti intermedio!", R.drawable.premio_icona_5, false),
        Premio("Esperto!", "Hai completato tutti gli allenamenti esperto!", R.drawable.premio_icona_6, false),
        Premio("Serie su Serie", "Hai svolto più di 1000 serie!", R.drawable.premio_icona_7, false),
        Premio("Sollevatore", "Hai sollevato un totale di 1000 kg!", R.drawable.premio_icona_8, false),
        Premio("Ripeti", "Hai completato 1000 ripetizioni!", R.drawable.premio_icona_9, false),
        Premio("Palestrato", "Hai completato 100 allenamenti!", R.drawable.premio_icona_10, false)
    )
}