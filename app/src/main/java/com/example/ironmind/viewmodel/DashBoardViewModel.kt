package com.example.ironmind.auth

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DashBoardViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("settings", Application.MODE_PRIVATE)

    private val _allenamentiCompletati = MutableLiveData<Int>()
    val allenamentiCompletati: LiveData<Int> get() = _allenamentiCompletati

    private val _obiettivo = MutableLiveData<Int>()
    val obiettivo: LiveData<Int> get() = _obiettivo

    fun caricaDatiContatore() {
        val completati = prefs.getInt("allenamenti_completati", 0)
        val obiettivo = prefs.getInt("obiettivo_settimanale", 3)
        _allenamentiCompletati.value = completati
        _obiettivo.value = obiettivo
    }
}