package com.example.ironmind.Model

    data class Promemoria(
        var id: Int,
        var nome: String,
        var ora: String,
        var giorno: String,
        var attivo: Boolean
    )