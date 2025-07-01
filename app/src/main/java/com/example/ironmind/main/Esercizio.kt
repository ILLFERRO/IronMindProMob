package com.example.ironmind.main

import java.io.Serializable

data class Esercizio(
    val nome: String,
    val descrizione: String,

    // Informazioni previste
    val setPrevisti: Int = 0,
    val ripetizioniPreviste: Int = 0,
    val usaPeso: Boolean = true,
    val pesoPredefinito: Float? = null, // Se peso noto

    // Dati raccolti durante l'allenamento
    var setCompletati: Int = 0,
    var ripetizioniPerSet: List<Int> = emptyList(), // es: [8,8,10]
    var pesoPerSet: List<Float> = emptyList(), // o tempo se corpo libero
    var tempoRecuperoPerSet: List<Int> = emptyList() // solo se corpo libero, in secondi

) : Serializable //indica che la data class può essere scritta in un file , salvata nelle SharedPreferences, passata tra Activity tramite Intent oppure inviata su rete o salvata su database