package com.example.ironmind

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ActivityScenario //per lanciare un'Activity in un test
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView //per cercare e verificare componenti UI
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import com.example.ironmind.main.AllenamentoDinamicoUI
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) //dice a JUnit di eseguire i test con il runner di Android
class AllenamentoDinamicoUITestScheda {

    private val nomeSchedaTest = "nomeScheda"

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>() //prende il context
        val prefs = context.getSharedPreferences("IronMindPrefs", Context.MODE_PRIVATE)

        val esercizi = listOf(
            Esercizio(
                nome = "Panca Piana",
                descrizione = "Esercizio petto",
                setPrevisti = 3,
                ripetizioniPreviste = 10,
                usaPeso = true,
                pesoPredefinito = 40f
            )
        )

        // Serializzo la lista in JSON e la salvo nelle SharedPreferences
        val json = Gson().toJson(esercizi)
        prefs.edit().putString("scheda_$nomeSchedaTest", json).apply()

        // Popolo anche la cache nel SchedaManager così evito problemi di caricamento
        SchedaManager.schedePersonalizzate[nomeSchedaTest] = esercizi.toMutableList()
    }

    @Test
    fun elementiBaseSonoVisibili() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, AllenamentoDinamicoUI::class.java).apply {
            putExtra("nomeScheda", nomeSchedaTest)
        }

        // Lancio l'activity con l'intent che contiene la scheda di test
        ActivityScenario.launch<AllenamentoDinamicoUI>(intent).use {
            // Controllo che il titolo dell'esercizio sia visibile e contenga il nome corretto
            onView(withId(R.id.titoloEsercizio))
                .check(matches(isDisplayed()))
                .check(matches(withText("Panca Piana")))

            // Controllo che il bottone "Aggiungi Set" sia visibile
            onView(withId(R.id.btnAggiungiSet))
                .check(matches(isDisplayed()))

            // Controllo che il timer allenamento sia visibile
            onView(withId(R.id.timerAllenamento))
                .check(matches(isDisplayed()))
        }
    }
}