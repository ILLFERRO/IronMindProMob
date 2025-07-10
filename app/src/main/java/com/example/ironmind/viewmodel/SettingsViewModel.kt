package com.example.ironmind.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsPrefs: SharedPreferences =
        application.getSharedPreferences("settings", Application.MODE_PRIVATE)

    private val _startTrainingGoal = MutableLiveData<String>()
    val startTrainingGoal: LiveData<String> get() = _startTrainingGoal

    private val _pesoIncremento = MutableLiveData<String>()
    val pesoIncremento: LiveData<String> get() = _pesoIncremento

    private val _mostraNotifica = MutableLiveData<Boolean>()
    val mostraNotifica: LiveData<Boolean> get() = _mostraNotifica

    private val _vibrazioneAttiva = MutableLiveData<Boolean>()
    val vibrazioneAttiva: LiveData<Boolean> get() = _vibrazioneAttiva

    private val _suoneriaAttiva = MutableLiveData<Boolean>()
    val suoneriaAttiva: LiveData<Boolean> get() = _suoneriaAttiva

    private val _setDefault = MutableLiveData<String>()
    val setDefault: LiveData<String> get() = _setDefault

    private val _tempoRiposo = MutableLiveData<Pair<Int, Int>>() // minuti, secondi
    val tempoRiposo: LiveData<Pair<Int, Int>> get() = _tempoRiposo

    fun caricaImpostazioni() {
        _startTrainingGoal.value = settingsPrefs.getString("start_training_day", "1 giorno a settimana")
        _pesoIncremento.value = settingsPrefs.getString("peso_incremento", "2.5")
        _mostraNotifica.value = settingsPrefs.getBoolean("mostra_notifica", false)
        _vibrazioneAttiva.value = settingsPrefs.getBoolean("vibrazione_attiva", false)
        _suoneriaAttiva.value = settingsPrefs.getBoolean("suoneria_attiva", false)
        _setDefault.value = settingsPrefs.getString("Set_Default", "3")

        val minuti = settingsPrefs.getInt("riposo_min", 0)
        val secondi = settingsPrefs.getInt("riposo_sec", 30)
        _tempoRiposo.value = Pair(minuti, secondi)
    }

    fun resetAllenamentiCompletati() {
        settingsPrefs.edit().putInt("allenamenti_completati", 0).apply()
    }
}