package com.example.ironmind.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PredefinedSchedeViewModel : ViewModel() {

    private val _schede = MutableLiveData<List<String>>()
    val schede: LiveData<List<String>> = _schede

    init {
        // In un caso reale questi dati potrebbero venire da Firebase o da un Repository
        _schede.value = listOf("Principiante", "Intermedio", "Esperto")
    }
}