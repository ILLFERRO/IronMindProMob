package com.example.ironmind.auth

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ironmind.main.*

class DashBoardViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("settings", Application.MODE_PRIVATE)
    private val prefsSchede: SharedPreferences = application.getSharedPreferences("IronMindPrefs", Application.MODE_PRIVATE)

    private val _allenamentiCompletati = MutableLiveData<Int>()
    val allenamentiCompletati: LiveData<Int> get() = _allenamentiCompletati

    private val _obiettivo = MutableLiveData<Int>()
    val obiettivo: LiveData<Int> get() = _obiettivo

    private val _schede = MutableLiveData<List<Pair<String, () -> Unit>>>()
    val schede: LiveData<List<Pair<String, () -> Unit>>> get() = _schede

    fun caricaDati() {
        caricaDatiContatore()
        caricaSchede()
    }

    private fun caricaDatiContatore() {
        _allenamentiCompletati.value = prefs.getInt("allenamenti_completati", 0)
        _obiettivo.value = prefs.getInt("obiettivo_settimanale", 3)
    }

    private fun caricaSchede() {
        val context = getApplication<Application>()
        val listaSchede = mutableListOf<Pair<String, () -> Unit>>()

        val schedaPrincipianteAttiva = prefsSchede.getBoolean("schedaPrincipianteAttiva", false)
        val schedaIntermedioAttiva = prefsSchede.getBoolean("schedaIntermedioAttiva", false)
        val schedaEspertoAttiva = prefsSchede.getBoolean("schedaEspertoAttiva", false)
        val schedePersonalizzate = prefsSchede.getStringSet("mieSchedeNomi", emptySet()) ?: emptySet()

        if (schedaPrincipianteAttiva) {
            listaSchede.add("Scheda Principiante" to {
                val intent = Intent(context, PrincipianteActivity::class.java)
                intent.putExtra("fromMieSchede", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            })
        }

        if (schedaIntermedioAttiva) {
            listaSchede.add("Scheda Intermedio" to {
                val intent = Intent(context, IntermedioActivity::class.java)
                intent.putExtra("fromMieSchede", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            })
        }

        if (schedaEspertoAttiva) {
            listaSchede.add("Scheda Esperto" to {
                val intent = Intent(context, EspertoActivity::class.java)
                intent.putExtra("fromMieSchede", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            })
        }

        for (nomeScheda in schedePersonalizzate) {
            listaSchede.add(nomeScheda to {
                val intent = Intent(context, SchedaPersonalizzataCreata::class.java)
                intent.putExtra("nomeScheda", nomeScheda)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            })
        }

        _schede.value = listaSchede
    }
}