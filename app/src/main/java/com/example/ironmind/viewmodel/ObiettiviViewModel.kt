package com.example.ironmind.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ObiettiviViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("settings", Application.MODE_PRIVATE)

    private val _giornoInizio = MutableLiveData<Int>()
    val giornoInizio: LiveData<Int> get() = _giornoInizio

    fun caricaGiornoSalvato() {
        _giornoInizio.value = prefs.getInt("start_training_day_number", 1)
    }

    fun salvaGiorno(numeroGiorno: Int, testoObiettivo: String) {
        prefs.edit()
            .putInt("start_training_day_number", numeroGiorno)
            .putString("start_training_day", testoObiettivo)
            .putInt("obiettivo_settimanale", numeroGiorno)
            .apply()
        _giornoInizio.value = numeroGiorno
    }
}