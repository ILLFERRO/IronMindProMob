package com.example.ironmind.main

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PromemoriaManager {
    private const val PREFS_NAME = "promemoria_prefs"
    private const val KEY_LISTA = "lista_promemoria"

    fun salva(context: Context, lista: List<Promemoria>) { //riceve Context per accedere a SharedPreferences
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(lista) //converte la lista di oggetti promemoria in una stringa json
        editor.putString(KEY_LISTA, json)
        editor.apply()
    }

    fun carica(context: Context): MutableList<Promemoria> { //serve a caricare una lista di Promemoria salvati nelle SharedPreferences, convertendola da Json a oggetto
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_LISTA, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Promemoria>>() {}.type // se stringa non esiste viene deserializzata in una MutableList<Promemoria>. Si usa un oggetto anonimo TypeToken<...>() per dire a Gson il tipo complesso della lista
            Gson().fromJson(json, type)
        } else { // se non esiste restituisce una lista vuota
            mutableListOf()
        }
    }
}