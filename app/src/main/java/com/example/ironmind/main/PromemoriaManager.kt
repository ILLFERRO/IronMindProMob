package com.example.ironmind.main

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PromemoriaManager {
    private const val PREFS_NAME = "promemoria_prefs"
    private const val KEY_LISTA = "lista_promemoria"

    fun salva(context: Context, lista: List<Promemoria>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(lista)
        editor.putString(KEY_LISTA, json)
        editor.apply()
    }

    fun carica(context: Context): MutableList<Promemoria> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_LISTA, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Promemoria>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}