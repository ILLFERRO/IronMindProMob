package com.example.ironmind.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SchedaManager {
    val schedePersonalizzate = mutableMapOf<String, MutableList<Esercizio>>()

    private val gson = Gson()

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
        val eserciziJson = prefs.getString("scheda_$nomeScheda", null) ?: return emptyList()

        val type = object : TypeToken<List<Esercizio>>() {}.type
        val esercizi = gson.fromJson<List<Esercizio>>(eserciziJson, type)

        // 🔧 Fix per evitare crash se i dati vecchi mancano alcuni campi
        val eserciziCorretti = esercizi.map { esercizio ->
            esercizio.copy(
                ripetizioniPerSet = esercizio.ripetizioniPerSet ?: emptyList(),
                pesoPerSet = esercizio.pesoPerSet ?: emptyList(),
                tempoRecuperoPerSet = esercizio.tempoRecuperoPerSet ?: emptyList()
            )
        }

        schedePersonalizzate[nomeScheda] = eserciziCorretti.toMutableList()
        Log.d("SchedaManager", "Esercizi caricati per '$nomeScheda': $eserciziCorretti")
        return eserciziCorretti
    }

    fun salvaScheda(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val lista = schedePersonalizzate[nomeScheda] ?: return
        val eserciziJson = gson.toJson(lista)

        editor.putString("scheda_$nomeScheda", eserciziJson)
        editor.apply()

        Log.d("SchedaManager", "Esercizi salvati per '$nomeScheda': $eserciziJson")
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
        getScheda(nomeScheda, context) // Fa già tutto
    }
}