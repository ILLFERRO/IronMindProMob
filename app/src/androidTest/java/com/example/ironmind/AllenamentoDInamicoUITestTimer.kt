package com.example.ironmind

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.ironmind.main.AllenamentoDinamicoUI
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import com.google.gson.Gson

@RunWith(AndroidJUnit4::class)
class AllenamentoDInamicoUITestTimer {

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

        // Salva la scheda in SharedPreferences come JSON
        val json = Gson().toJson(esercizi)
        prefs.edit().putString("scheda_$nomeSchedaTest", json).apply()

        // Popola cache nel SchedaManager
        SchedaManager.schedePersonalizzate[nomeSchedaTest] = esercizi.toMutableList()
    }

    @Test
    fun testTimerSiAggiorna() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, AllenamentoDinamicoUI::class.java).apply {
            putExtra("nomeScheda", nomeSchedaTest)
        }

        ActivityScenario.launch<AllenamentoDinamicoUI>(intent).use {

            // Verifica che il timer sia visibile
            onView(withId(R.id.timerAllenamento))
                .check(matches(isDisplayed()))

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
            Thread.sleep(2100)

            // Verifica che il testo del timer sia cambiato (il timer si è aggiornato)
            onView(withId(R.id.timerAllenamento)).check(matches(not(withText(testoIniziale))))
        }
    }
}
