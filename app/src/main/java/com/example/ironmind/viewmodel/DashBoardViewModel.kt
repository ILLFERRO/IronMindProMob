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
        val lista   = mutableListOf<Pair<String, () -> Unit>>()

        /* -------------------------------------------------
           1.  Leggi i flag correnti e la StringSet salvata
           ------------------------------------------------- */
        var flagPrincipiante = prefsSchede.getBoolean("schedaPrincipianteAttiva", false)
        var flagIntermedio   = prefsSchede.getBoolean("schedaIntermedioAttiva",  false)
        var flagEsperto      = prefsSchede.getBoolean("schedaEspertoAttiva",     false)

        val personalizzate = prefsSchede.getStringSet("mieSchedeNomi", emptySet()) ?: emptySet()

        /* -------------------------------------------------
           2.  Se nel Set compare ALMENO UNA delle versioni
               1/2/3, riattiva e persisti il flag
           ------------------------------------------------- */
        val editorFix = prefsSchede.edit()

        if (!flagPrincipiante && personalizzate.any { it.startsWith("Principiante ") }) {
            flagPrincipiante = true
            editorFix.putBoolean("schedaPrincipianteAttiva", true)
        }
        if (!flagIntermedio && personalizzate.any { it.startsWith("Intermedio ") }) {
            flagIntermedio = true
            editorFix.putBoolean("schedaIntermedioAttiva", true)
        }
        if (!flagEsperto && personalizzate.any { it.startsWith("Esperto ") }) {
            flagEsperto = true
            editorFix.putBoolean("schedaEspertoAttiva", true)
        }
        editorFix.apply()

        /* -------------------------------------------------
           3.  Aggiungi le card predefinite, se attive
           ------------------------------------------------- */
        if (flagPrincipiante) {
            lista += "Scheda Principiante" to {
                Intent(context, PrincipianteActivity::class.java).apply {
                    putExtra("fromMieSchede", true)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }

        if (flagIntermedio) {
            lista += "Scheda Intermedio" to {
                Intent(context, IntermedioActivity::class.java).apply {
                    putExtra("fromMieSchede", true)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }

        if (flagEsperto) {
            lista += "Scheda Esperto" to {
                Intent(context, EspertoActivity::class.java).apply {
                    putExtra("fromMieSchede", true)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }

        /* -------------------------------------------------
           4.  Aggiungi TUTTE le schede personalizzate reali
           ------------------------------------------------- */
        for (nome in personalizzate) {
            lista += nome to {
                Intent(context, SchedaPersonalizzataCreata::class.java).apply {
                    putExtra("nomeScheda", nome)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }

        /* 5. pubblica la lista finalizzata */
        _schede.value = lista
    }
}