package com.example.ironmind

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario //per lanciare le activity da testare
import androidx.test.core.app.ApplicationProvider //per ottenere il context
import androidx.test.espresso.ViewAction //per eseguire azioni personalizzate sulle view
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4 //per definire i test
import org.hamcrest.Matchers.not //per verificare la UI insieme ad Assertion
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.ironmind.main.AllenamentoDinamicoUI
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import com.google.gson.Gson //per serializzare/deserializzare i dati

@RunWith(AndroidJUnit4::class) //Runner Android per Test strumentati
class AllenamentoDinamicoUITestTimer {

    private val nomeSchedaTest = "nomeScheda"

    @Before //fa eseguire la funzione setup prima di ogni test
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>() //prende il context
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE) //ottiene le sharedPreferences con nome IronMindPrefs

        val esercizi = listOf( //definisce una lista di esercizi
            Esercizio(
                nome = "Panca Piana",
                descrizione = "Esercizio petto",
                setPrevisti = 3,
                ripetizioniPreviste = 10,
                usaPeso = true,
                pesoPredefinito = 40f
            )
        )

        // Salva la scheda in SharedPreferences come JSON
        val json = Gson().toJson(esercizi) //serializza la lista in JSON con Gson
        prefs.edit().putString("scheda_$nomeSchedaTest", json).apply() //salva la scheda con chiave scheda_$nomeSchedaTest

        // Popola cache nel SchedaManager
        SchedaManager.schedePersonalizzate[nomeSchedaTest] = esercizi.toMutableList() //aggiorna la cache interna di SchedaManager con la lista per evitare il problema di caricamento dati
    }

    @Test
    fun testTimerSiAggiorna() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, AllenamentoDinamicoUI::class.java).apply {
            putExtra("nomeScheda", nomeSchedaTest)
        }

        ActivityScenario.launch<AllenamentoDinamicoUI>(intent).use {

            // Verifica che il timer sia visibile
            onView(withId(R.id.timerAllenamento)).check(matches(isDisplayed()))

            // 🔁 Attendi che il ViewModel sia pronto e abbia avviato il timer
            Thread.sleep(1500) // puoi aumentare a 2000 o 2500 se serve

            // Leggi il testo iniziale del timer
            var testoIniziale = ""
            onView(withId(R.id.timerAllenamento)).perform(object : ViewAction {
                override fun getConstraints() = isDisplayed()
                override fun getDescription() = "Leggi testo timer iniziale"
                override fun perform(uiController: androidx.test.espresso.UiController?, view: View?) {
                    val tv = view as? TextView
                    testoIniziale = tv?.text.toString()
                }
            })

            // Aspetta un paio di secondi per lasciare aggiornare il timer
            onView(withId(R.id.timerAllenamento)).check(matches(isDisplayed()))  // si assicura che sia visibile
            Thread.sleep(2000)

            // Verifica che il testo del timer sia cambiato (il timer si è aggiornato)
            onView(withId(R.id.timerAllenamento)).check(matches(not(withText(testoIniziale))))
        }
    }
}