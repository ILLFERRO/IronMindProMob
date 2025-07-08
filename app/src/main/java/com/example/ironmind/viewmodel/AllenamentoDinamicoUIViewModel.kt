package com.example.ironmind.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.Repository.PremiRepository
import com.example.ironmind.Utils.SchedaManager
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields

class AllenamentoDinamicoUIViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val statsPrefs: SharedPreferences = context.getSharedPreferences("allenamento_stats", Context.MODE_PRIVATE)

    val tempoAllenamento = MutableLiveData<Long>()
    val scheda = MutableLiveData<MutableList<Esercizio>>(mutableListOf())
    val esercizioCorrenteIndex = MutableLiveData(0)
    val tempoRecuperoSec = MutableLiveData(60)
    val setIniziali = MutableLiveData(3)
    val pesoIncremento = MutableLiveData(2.5f)
    val nomeScheda = MutableLiveData<String>()
    val eserciziCompletati = MutableLiveData<MutableList<Esercizio>>(mutableListOf())

    private var startTime: Long = 0
    private var timerJob: Job? = null

    fun avviaTimer() {
        if (startTime == 0L) startTime = System.currentTimeMillis()
        timerJob = viewModelScope.launch {
            while (isActive) {
                val elapsed = (System.currentTimeMillis() - startTime) / 1000
                tempoAllenamento.postValue(elapsed)
                delay(1000)
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun inizializzaScheda(nomeSchedaInput: String) {
        val nome = when {
            prefs.getString("scheda_salvata_nome", "").orEmpty().isNotEmpty() -> prefs.getString("scheda_salvata_nome", "").orEmpty()
            nomeSchedaInput.isNotEmpty() -> nomeSchedaInput
            else -> "Scheda Temporanea"
        }

        nomeScheda.value = nome
        val lista = SchedaManager.getScheda(nome, context).toMutableList()
        scheda.value = lista

        val min = prefs.getInt("riposo_min", 0)
        val sec = prefs.getInt("riposo_sec", 60)
        tempoRecuperoSec.value = min * 60 + sec

        setIniziali.value = prefs.getString("Set_Default", "3")?.toIntOrNull() ?: 3
        pesoIncremento.value = prefs.getString("Incremento_Peso", "2.5")?.toFloatOrNull() ?: 2.5f
    }

    fun aggiornaRecupero(min: Int, sec: Int) {
        tempoRecuperoSec.value = min * 60 + sec
    }

    fun salvaSchedaCorrente() {
        val nome = nomeScheda.value ?: return
        val lista = scheda.value ?: return

        SchedaManager.schedePersonalizzate[nome] = lista
        SchedaManager.salvaScheda(nome, context)

        val elapsedSeconds = ((System.currentTimeMillis() - startTime) / 1000)
        val totaleSet = lista.sumOf { it.setCompletati }

        statsPrefs.edit()
            .putLong("durata_totale_sec", elapsedSeconds)
            .putLong("durata_$nome", elapsedSeconds)
            .putInt("numero_totale_set", totaleSet)
            .apply()
    }

    fun aggiornaStatisticheFinali() {
        val lista = scheda.value ?: return
        val nome = nomeScheda.value ?: return

        val allenamenti = statsPrefs.getInt("allenamenti_totali", 0) + 1
        val serie = statsPrefs.getInt("serie_totali", 0) + lista.sumOf { it.setCompletati }
        val ripetizioni = statsPrefs.getInt("ripetizioni_totali", 0) + lista.sumOf { it.ripetizioniPerSet.sum() }
        val pesoTotale = statsPrefs.getFloat("peso_totale", 0f) +
                lista.sumOf { it.pesoPerSet.sumOf { peso -> peso.toDouble() } }.toFloat()

        val oggi = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val allenamentoDates = statsPrefs.getStringSet("allenamento_date", emptySet())?.toMutableSet() ?: mutableSetOf()
        allenamentoDates.add(oggi.format(formatter))

        statsPrefs.edit().apply {
            putInt("allenamenti_totali", allenamenti)
            putInt("serie_totali", serie)
            putInt("ripetizioni_totali", ripetizioni)
            putFloat("peso_totale", pesoTotale)
            putStringSet("allenamento_date", allenamentoDates)

            putString("ultima_data_$nome", oggi.format(formatter))
            apply()
        }
    }

    fun aggiungiEsercizioCompletato(esercizio: Esercizio) {
        val lista = eserciziCompletati.value ?: mutableListOf()
        if (!lista.contains(esercizio)) {
            lista.add(esercizio)
            eserciziCompletati.value = lista
        }
    }

    fun prossimoEsercizio() {
        val index = esercizioCorrenteIndex.value ?: 0
        scheda.value?.getOrNull(index)?.let {
            aggiungiEsercizioCompletato(it)
        }

        if (index < (scheda.value?.size ?: 0) - 1) {
            esercizioCorrenteIndex.value = index + 1
        }
    }

    fun terminaAllenamento() {
        val index = esercizioCorrenteIndex.value ?: return
        scheda.value?.getOrNull(index)?.let {
            aggiungiEsercizioCompletato(it)
        }
    }

    fun getEsercizioCorrente(): Esercizio? {
        return scheda.value?.getOrNull(esercizioCorrenteIndex.value ?: 0)
    }

    fun getEserciziCompletati(): List<Esercizio> {
        val index = esercizioCorrenteIndex.value ?: -1
        return if (index >= 0) {
            scheda.value?.subList(0, index + 1) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun isUltimoEsercizio(): Boolean {
        val size = scheda.value?.size ?: 0
        return (esercizioCorrenteIndex.value ?: 0) >= size - 1
    }

    fun getSetIniziali(): Int {
        return setIniziali.value ?: 3
    }

    fun getPesoIncremento(): Float {
        return pesoIncremento.value ?: 2.5f
    }

    fun aggiornaSetEsercizio(indexSet: Int, peso: Float, ripetizioni: Int) {
        val esercizio = getEsercizioCorrente() ?: return
        esercizio.pesoPerSet = esercizio.pesoPerSet.toMutableList().apply {
            this[indexSet] = peso
        }
        esercizio.ripetizioniPerSet = esercizio.ripetizioniPerSet.toMutableList().apply {
            this[indexSet] = ripetizioni
        }
        salvaSchedaCorrente()
    }

    fun aggiungiSet(peso: Float, ripetizioni: Int, tempoRecupero: Int) {
        val esercizio = getEsercizioCorrente() ?: return
        esercizio.pesoPerSet = esercizio.pesoPerSet + peso
        esercizio.ripetizioniPerSet = esercizio.ripetizioniPerSet + ripetizioni
        esercizio.tempoRecuperoPerSet = esercizio.tempoRecuperoPerSet + tempoRecupero
        esercizio.setCompletati = esercizio.ripetizioniPerSet.size
        salvaSchedaCorrente()
    }

    fun eliminaSet(indexSet: Int) {
        val esercizio = getEsercizioCorrente() ?: return

        if (indexSet in esercizio.pesoPerSet.indices &&
            indexSet in esercizio.ripetizioniPerSet.indices &&
            indexSet in esercizio.tempoRecuperoPerSet.indices
        ) {
            esercizio.pesoPerSet = esercizio.pesoPerSet.toMutableList().apply {
                removeAt(indexSet)
            }
            esercizio.ripetizioniPerSet = esercizio.ripetizioniPerSet.toMutableList().apply {
                removeAt(indexSet)
            }
            esercizio.tempoRecuperoPerSet = esercizio.tempoRecuperoPerSet.toMutableList().apply {
                removeAt(indexSet)
            }

            esercizio.setCompletati = esercizio.ripetizioniPerSet.size
            salvaSchedaCorrente()
        }
    }

    fun aggiornaEControllaPremi() {
        val lista = scheda.value ?: return
        val nome = nomeScheda.value ?: return

        val editor = statsPrefs.edit()

        val serie = statsPrefs.getInt("serie_totali", 0) + lista.sumOf { it.setCompletati ?: 0 }
        val ripetizioni = statsPrefs.getInt("ripetizioni_totali", 0) +
                lista.sumOf { it.ripetizioniPerSet?.sum() ?: 0 }

        val pesoTotale = statsPrefs.getFloat("peso_totale", 0f) +
                lista.sumOf { it.pesoPerSet?.sumOf { p -> p?.toDouble() ?: 0.0 } ?: 0.0 }.toFloat()

        val allenamenti = statsPrefs.getInt("allenamenti_totali", 0) + 1
        editor.putInt("allenamenti_totali", allenamenti)
        editor.putInt("serie_totali", serie)
        editor.putInt("ripetizioni_totali", ripetizioni)
        editor.putFloat("peso_totale", pesoTotale)

        val oggi = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val allenamentoDates = statsPrefs.getStringSet("allenamento_date", emptySet())?.toMutableSet() ?: mutableSetOf()
        allenamentoDates.add(oggi.format(formatter))
        editor.putStringSet("allenamento_date", allenamentoDates.map { it.toString() }.toSet())

        val currentWeek = oggi.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val currentYear = oggi.year
        val allenamentiSettimana = allenamentoDates.count {
            try {
                val date = LocalDate.parse(it, formatter)
                val week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
                val year = date.year
                week == currentWeek && year == currentYear
            } catch (e: Exception) {
                false
            }
        }

        val settimaneCon3Allenamenti = statsPrefs.getStringSet("settimane_attive", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (allenamentiSettimana >= 3) {
            settimaneCon3Allenamenti.add("$currentYear-$currentWeek")
            editor.putStringSet("settimane_attive", settimaneCon3Allenamenti.map { it.toString() }.toSet())
        }

        val settimaneAttiveCount = settimaneCon3Allenamenti.size
        val costanza = settimaneAttiveCount >= 4

        val schedeCompletate = statsPrefs.getStringSet("schede_completate", emptySet())?.toMutableSet() ?: mutableSetOf()
        schedeCompletate.add(nome)
        editor.putStringSet("schede_completate", schedeCompletate)

        val principianteCompleto = listOf("Principiante 1", "Principiante 2", "Principiante 3").all { it in schedeCompletate }
        val intermedioCompleto = listOf("Intermedio 1", "Intermedio 2", "Intermedio 3").all { it in schedeCompletate }
        val espertoCompleto = listOf("Esperto 1", "Esperto 2", "Esperto 3").all { it in schedeCompletate }

        editor.apply()

        val premiPrefs = context.getSharedPreferences("premi_sbloccati", Context.MODE_PRIVATE)
        val premiEditor = premiPrefs.edit()

        PremiRepository.listaPremi.forEach { premio ->
            if (!premio.sbloccato) {
                when (premio.titolo) {
                    "Primo Allenamento" -> if (allenamenti >= 1) premio.sbloccato = true
                    "Palestrato" -> if (allenamenti >= 100) premio.sbloccato = true
                    "Serie su Serie" -> if (serie >= 1000) premio.sbloccato = true
                    "Ripeti" -> if (ripetizioni >= 1000) premio.sbloccato = true
                    "Sollevatore" -> if (pesoTotale >= 1000f) premio.sbloccato = true
                    "Settimana Attiva" -> if (settimaneCon3Allenamenti.contains("$currentYear-$currentWeek")) premio.sbloccato = true
                    "Costanza" -> if (costanza) premio.sbloccato = true
                    "Principiante!" -> if (principianteCompleto) premio.sbloccato = true
                    "Intermedio!" -> if (intermedioCompleto) premio.sbloccato = true
                    "Esperto!" -> if (espertoCompleto) premio.sbloccato = true
                }

                if (premio.sbloccato && premio.titolo != null) {
                    premiEditor.putBoolean(premio.titolo, true)
                }
            }
        }

        premiEditor.apply()
    }
}