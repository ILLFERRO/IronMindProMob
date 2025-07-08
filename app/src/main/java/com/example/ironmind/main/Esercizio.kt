package com.example.ironmind.main

import java.io.Serializable

data class Esercizio(
    val nome: String,
    val descrizione: String,

    val setPrevisti: Int = 0,
    val ripetizioniPreviste: Int = 0,
    val usaPeso: Boolean = true,
    val pesoPredefinito: Float? = null,

    var setCompletati: Int = 0,
    var ripetizioniPerSet: List<Int> = emptyList(),
    var pesoPerSet: List<Float> = emptyList(),
    var tempoRecuperoPerSet: List<Int> = emptyList()

) : Serializable