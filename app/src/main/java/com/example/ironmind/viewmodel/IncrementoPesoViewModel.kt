package com.example.ironmind.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class IncrementoPesoViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences =
        application.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _incrementoPeso = MutableLiveData<String>()
    val incrementoPeso: LiveData<String> = _incrementoPeso

    fun caricaValoreSalvato() {
        val saved = prefs.getString("peso_incremento", "2.5") ?: "2.5"
        _incrementoPeso.value = saved
    }

    fun salvaValore(nuovoValore: String): Boolean {
        return if (nuovoValore.isNotEmpty()) {
            prefs.edit().putString("peso_incremento", nuovoValore).apply()
            true
        } else {
            false
        }
    }
}