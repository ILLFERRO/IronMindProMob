package com.example.ironmind.main

import android.content.Context
import android.util.Log

object SchedaManager {
    val schedePersonalizzate = mutableMapOf<String, MutableList<Esercizio>>()

    fun aggiungiEsercizio(nomeScheda: String, esercizio: Esercizio) {
        val lista = schedePersonalizzate.getOrPut(nomeScheda) { mutableListOf() }
        if (!lista.contains(esercizio)) {
            lista.add(esercizio)
        }
    }

    fun getScheda(nomeScheda: String, context: Context): List<Esercizio> {
        schedePersonalizzate[nomeScheda]?.let {
            return it
        }

        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val eserciziSerializzati = prefs.getString("scheda_$nomeScheda", null) ?: return emptyList()

        val esercizi = eserciziSerializzati.split(";").mapNotNull { entry ->
            val parts = entry.split("::")
            if (parts.size == 2) Esercizio(parts[0], parts[1]) else null
        }

        schedePersonalizzate[nomeScheda] = esercizi.toMutableList()
        return esercizi

        Log.d("SchedaManager", "Esercizi caricati per '$nomeScheda': $esercizi")
    }

    fun salvaScheda(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val lista = schedePersonalizzate[nomeScheda] ?: return
        val eserciziSerializzati = lista.joinToString(";") { "${it.nome}::${it.descrizione}" }

        editor.putString("scheda_$nomeScheda", eserciziSerializzati)
        editor.apply()

        Log.d("SchedaManager", "Esercizi salvati per '$nomeScheda': $eserciziSerializzati")
    }

    fun clearScheda(nomeScheda: String) {
        schedePersonalizzate[nomeScheda]?.clear()
    }

    fun contiene(nomeScheda: String, esercizio: Esercizio): Boolean {
        return schedePersonalizzate[nomeScheda]?.contains(esercizio) == true
    }

    fun rimuoviEsercizio(nomeScheda: String, esercizio: Esercizio) {
        schedePersonalizzate[nomeScheda]?.remove(esercizio)
    }

    fun caricaSchedaDaStorage(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val eserciziSerializzati = prefs.getString("scheda_$nomeScheda", null) ?: return

        val listaEsercizi = eserciziSerializzati.split(";").mapNotNull {
            val parti = it.split("::")
            if (parti.size == 2) {
                Esercizio(parti[0], parti[1])
            } else null
        }

        schedePersonalizzate[nomeScheda] = listaEsercizi.toMutableList()
    }
}