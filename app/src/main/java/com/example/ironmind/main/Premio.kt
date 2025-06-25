package com.example.ironmind.main

data class Premio(
    val titolo: String,
    val descrizione: String,
    val iconaRes: Int,
    var sbloccato: Boolean = false
)