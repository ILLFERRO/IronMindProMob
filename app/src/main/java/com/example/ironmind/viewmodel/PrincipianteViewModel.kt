package com.example.ironmind.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PrincipianteViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences =
        application.getSharedPreferences("IronMindPrefs", Application.MODE_PRIVATE)

    private val _fromMieSchede = MutableLiveData<Boolean>()
    val fromMieSchede: LiveData<Boolean> get() = _fromMieSchede

    private val _navigaDashboard = MutableLiveData<Boolean>()
    val navigaDashboard: LiveData<Boolean> get() = _navigaDashboard

    fun inizializza(fromIntent: Boolean) {
        _fromMieSchede.value = fromIntent
    }

    fun attivaScheda() {
        prefs.edit().putBoolean("schedaPrincipianteAttiva", true).apply()
        _navigaDashboard.value = true
    }

    fun disattivaScheda() {
        prefs.edit().putBoolean("schedaPrincipianteAttiva", false).apply()
        _navigaDashboard.value = true
    }

    fun resetNavigazione() {
        _navigaDashboard.value = false
    }
}