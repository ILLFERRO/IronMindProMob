package com.example.ironmind.Utils

fun formattaRecupero(secondi: Int): String {
    val min = secondi / 60
    val sec = secondi % 60
    return "Recupero: %02d:%02d".format(min, sec)
}