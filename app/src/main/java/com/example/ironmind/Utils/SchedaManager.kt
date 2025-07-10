package com.example.ironmind.Utils

import android.content.Context
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
        schedePersonalizzate[nomeScheda]?.let { return it }

        if (context == null) return emptyList()

        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)

        var eserciziJson: String? = prefs.getString("scheda_$nomeScheda", null)

        if (eserciziJson == null) {
            val set = prefs.getStringSet("scheda_$nomeScheda", null)
            if (!set.isNullOrEmpty()) {

                val typeExercise = object : TypeToken<Esercizio>() {}.type
                val lista = mutableListOf<Esercizio>()

                for (item in set) {
                    try {
                        lista += Gson().fromJson<Esercizio>(item, typeExercise)
                    } catch (_: Exception) {
                    }
                }

                if (lista.isNotEmpty()) {
                    eserciziJson = Gson().toJson(lista)

                    prefs.edit()
                        .putString("scheda_$nomeScheda", eserciziJson)
                        .apply()
                }
            }
        }

        if (eserciziJson.isNullOrEmpty()) return emptyList()

        val listType = object : TypeToken<List<Esercizio>>() {}.type
        val esercizi: List<Esercizio> = Gson().fromJson(eserciziJson, listType)

        schedePersonalizzate[nomeScheda] = esercizi.toMutableList()

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