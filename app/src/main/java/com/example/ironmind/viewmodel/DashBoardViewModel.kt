package com.example.ironmind.auth

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ironmind.view.EspertoActivity
import com.example.ironmind.view.IntermedioActivity
import com.example.ironmind.view.PrincipianteActivity
import com.example.ironmind.view.SchedaPersonalizzataCreata

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
        val lista = mutableListOf<Pair<String, () -> Unit>>()

        val schedePersonalizzate =
            prefsSchede.getStringSet("mieSchedeNomi", emptySet()) ?: emptySet()

        /* 1. preleva i flag; se sono già true, va bene */
        var principiante = prefsSchede.getBoolean("schedaPrincipianteAttiva", false)
        var intermedio   = prefsSchede.getBoolean("schedaIntermedioAttiva",  false)
        var esperto      = prefsSchede.getBoolean("schedaEspertoAttiva",     false)

        /* 2.  Fallback: se nel Set c’è il nome della scheda, considera la card “attiva” */
        if (!principiante && "Principiante 1" in schedePersonalizzate) principiante = true
        if (!intermedio   && "Intermedio 1"   in schedePersonalizzate) intermedio   = true
        if (!esperto      && "Esperto 1"      in schedePersonalizzate) esperto      = true

        /* 3.  Card predefinite */
        if (principiante) lista += "Scheda Principiante" to {
            Intent(context, PrincipianteActivity::class.java).apply {
                putExtra("fromMieSchede", true); addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
        if (intermedio)   lista += "Scheda Intermedio" to {
            Intent(context, IntermedioActivity::class.java).apply {
                putExtra("fromMieSchede", true); addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
        if (esperto)      lista += "Scheda Esperto" to {
            Intent(context, EspertoActivity::class.java).apply {
                putExtra("fromMieSchede", true); addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }

        /* 4.  Tutte le vere personalizzate */
        for (nome in schedePersonalizzate) {
            lista += nome to {
                Intent(context, SchedaPersonalizzataCreata::class.java).apply {
                    putExtra("nomeScheda", nome); addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }

        _schede.value = lista
    }
}