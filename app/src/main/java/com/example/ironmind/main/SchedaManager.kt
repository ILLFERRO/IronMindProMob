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

    fun getScheda(nomeScheda: String, context: Context? = null): List<Esercizio> {
        schedePersonalizzate[nomeScheda]?.let {
            return it
        }

        // Se non è in cache e il context è nullo (cioè sei in test), restituisci lista vuota
        if (context == null) return emptyList()

        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)
        val eserciziJson = prefs.getString("scheda_$nomeScheda", null) ?: return emptyList()

        val type = object : TypeToken<List<Esercizio>>() {}.type
        val esercizi = gson.fromJson<List<Esercizio>>(eserciziJson, type)

        val eserciziCorretti = esercizi.map { esercizio ->
            esercizio.copy(
                ripetizioniPerSet = esercizio.ripetizioniPerSet,
                pesoPerSet = esercizio.pesoPerSet,
                tempoRecuperoPerSet = esercizio.tempoRecuperoPerSet
            )
        }

        schedePersonalizzate[nomeScheda] = eserciziCorretti.toMutableList()
        Log.d("SchedaManager", "Esercizi caricati per '$nomeScheda': $eserciziCorretti")
        return eserciziCorretti
    }

    fun salvaScheda(nomeScheda: String, context: Context) {
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE) //Ottiene le SharedPreferences chiamate "IronMindPrefs" in modalità privata (solo l'app può accedervi). Serve per salvare i dati in modo persistente, anche dopo la chiusura dell'app.
        val editor = prefs.edit() //Crea un editor, necessario per modificare o salvare dati nelle SharedPreferences.

        val lista = schedePersonalizzate[nomeScheda] ?: return //Cerca nella cache (schedePersonalizzate) la lista di esercizi associata alla nomeScheda. Se non esiste, la funzione si interrompe (return), perché non c’è nulla da salvare.
        val eserciziJson = gson.toJson(lista) //converte la lista di esercizi in una stringa JSON salvabile come testo

        editor.putString("scheda_$nomeScheda", eserciziJson) //prendo la scheda con il nome che ho, e gli ci metto gli esercizi collegati
        editor.apply() //applico le modifiche

        Log.d("SchedaManager", "Esercizi salvati per '$nomeScheda': $eserciziJson")
    }

    fun clearScheda(nomeScheda: String) { //cerca la lista nella cache, se la trova la svuota, eliminando gli esercizi solo dalla memoria RAM
        schedePersonalizzate[nomeScheda]?.clear()
    }

    fun contiene(nomeScheda: String, esercizio: Esercizio): Boolean { //qui controlla se la scheda contiene un certo esercizio e torna un valore booleano true se così è
        return schedePersonalizzate[nomeScheda]?.contains(esercizio) == true
    }

    fun rimuoviEsercizio(nomeScheda: String, esercizio: Esercizio) { //qui fa la stessa cosa per rimuovere un esercizio
        schedePersonalizzate[nomeScheda]?.remove(esercizio)
    }

    fun caricaSchedaDaStorage(nomeScheda: String, context: Context) { //Cerca la scheda in cache, se non c'è, la carica dalle SharedPreferences, la deserializza (da JSON a oggetti) e la mette nella cache
        getScheda(nomeScheda, context)
    }
}