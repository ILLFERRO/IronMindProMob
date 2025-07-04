package com.example.ironmind.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EspertoViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("IronMindPrefs", Application.MODE_PRIVATE)

    private val _navigaADashboard = MutableLiveData<Boolean>()
    val navigaADashboard: LiveData<Boolean> get() = _navigaADashboard

    fun inserisciScheda() {
        prefs.edit().putBoolean("schedaEspertoAttiva", true).apply()
        _navigaADashboard.value = true
    }

    fun eliminaScheda() {
        prefs.edit().putBoolean("schedaEspertoAttiva", false).apply()
        _navigaADashboard.value = true
    }

    fun resetNavigazione() {
        _navigaADashboard.value = false
    }
}