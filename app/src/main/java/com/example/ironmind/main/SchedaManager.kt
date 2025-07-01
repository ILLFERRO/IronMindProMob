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

    fun getScheda(nomeScheda: String, context: Context): List<Esercizio> { //Context serve a dare un contesto all'app, cioè una classe che dia risorse e funzioni di Android tipo SharedPreferences, Risorse, File di Sistema, questa funzione restituisce la lista di esercizi associati al nome di una scheda di allenamento. I dati sono letti da SharedPreferences se non sono già in memoria.
        schedePersonalizzate[nomeScheda]?.let { //controlla se la scheda è già in memoria
            return it //se è così la ritorna
        }

        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE) //Apre le SharedPreferences "IronMindPrefs"
        val eserciziJson = prefs.getString("scheda_$nomeScheda", null) ?: return emptyList() //cerca una scheda con un certo nome con la chiave "scheda_$nomeScheda", se non la trova resituisce una lista vuota

        //Deserializza il JSON in una lista di oggetti Esercizio
        val type = object : TypeToken<List<Esercizio>>() {}.type
        val esercizi = gson.fromJson<List<Esercizio>>(eserciziJson, type) //Usa Gson per convertire il JSON in una vera lista di oggetti Esercizio

        // 🔧 Fix per evitare crash se i dati vecchi mancano alcuni campi
        val eserciziCorretti = esercizi.map { esercizio -> //Fa un controllo su ogni esercizio per evitare null nei campi lista
            esercizio.copy( //Usa copy() per creare una copia di ogni oggetto Esercizio con liste vuote al posto dei null
                ripetizioniPerSet = esercizio.ripetizioniPerSet,
                pesoPerSet = esercizio.pesoPerSet,
                tempoRecuperoPerSet = esercizio.tempoRecuperoPerSet
            )
        }

        //salva la lista in memoria e la restituisce
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