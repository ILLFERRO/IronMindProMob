package com.example.ironmind

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import com.example.ironmind.view.AllenamentoDinamicoUI
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AllenamentoDinamicoUITestScheda {

    private val nomeSchedaTest = "nomeScheda"

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
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

        val json = Gson().toJson(esercizi)
        prefs.edit().putString("scheda_$nomeSchedaTest", json).apply()

        SchedaManager.schedePersonalizzate[nomeSchedaTest] = esercizi.toMutableList()
    }

    @Test
    fun elementiBaseSonoVisibili() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, AllenamentoDinamicoUI::class.java).apply {
            putExtra("nomeScheda", nomeSchedaTest)
        }

        ActivityScenario.launch<AllenamentoDinamicoUI>(intent).use {
            onView(withId(R.id.titoloEsercizio))
                .check(matches(isDisplayed()))
                .check(matches(withText("Panca Piana")))

            onView(withId(R.id.btnAggiungiSet))
                .check(matches(isDisplayed()))

            onView(withId(R.id.timerAllenamento))
                .check(matches(isDisplayed()))
        }
    }
}