package com.example.ironmind.utils

fun formattaRecupero(secondi: Int): String { //funzione di utilità che prende un numero intero e restituisce una stringa
    val min = secondi / 60
    val sec = secondi % 60
    return "Recupero: %02d:%02d".format(min, sec)
}