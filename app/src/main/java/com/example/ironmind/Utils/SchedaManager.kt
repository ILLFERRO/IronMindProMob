package com.example.ironmind.Utils

import android.content.Context
import android.util.Log
import com.example.ironmind.Model.Esercizio
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

    fun getScheda(nomeScheda: String, context: Context? = null): List<Esercizio> {
        /* 1. se l’abbiamo già in RAM, usiamola */
        schedePersonalizzate[nomeScheda]?.let { return it }

        if (context == null) return emptyList()

        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)

        Log.d(
            "DEBUGBakcup",
            "Prefs-raw = " +
                    prefs.all
                        .filterKeys { it.startsWith("scheda_") }
                        .mapValues { it.value?.javaClass?.simpleName }
        )

        /* 2. lettura String pura (formato corretto) */
        var eserciziJson: String? = prefs.getString("scheda_$nomeScheda", null)

        /* 3. fallback: la chiave è StringSet */
        if (eserciziJson == null) {
            val set = prefs.getStringSet("scheda_$nomeScheda", null)
            if (!set.isNullOrEmpty()) {

                val typeExercise = object : TypeToken<Esercizio>() {}.type
                val lista = mutableListOf<Esercizio>()

                for (item in set) {
                    try {
                        lista += Gson().fromJson<Esercizio>(item, typeExercise)
                    } catch (_: Exception) {
                        /* non è un singolo esercizio → ignora */
                    }
                }

                if (lista.isNotEmpty()) {
                    /** 3a. ricostruisci l’array JSON in UN’UNICA stringa */
                    eserciziJson = Gson().toJson(lista)

                    /** 3b. sovrascrivi la preferenza nel formato corretto */
                    prefs.edit()
                        .putString("scheda_$nomeScheda", eserciziJson) // stringa pulita
                        .apply()                                       // (non serve più remove)
                }
            }
        }

        /* 4. se ancora null → lista vuota */
        if (eserciziJson.isNullOrEmpty()) return emptyList()

        /* 5. deserializza l’array definitivo */
        val listType = object : TypeToken<List<Esercizio>>() {}.type
        val esercizi: List<Esercizio> = Gson().fromJson(eserciziJson, listType)

        /* 6. metti in cache e logga */
        schedePersonalizzate[nomeScheda] = esercizi.toMutableList()

        Log.d("DEBUGBakcup", "Scheda $nomeScheda → esercizi = ${esercizi.size}")
        return esercizi
    }

    fun salvaScheda(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val lista = schedePersonalizzate[nomeScheda] ?: return
        val eserciziJson = gson.toJson(lista)

        editor.putString("scheda_$nomeScheda", eserciziJson)
        editor.apply()
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
        getScheda(nomeScheda, context)
    }
}