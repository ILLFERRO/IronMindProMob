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
import com.example.ironmind.view.AllenamentoDinamicoUI
import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import com.google.gson.Gson

@RunWith(AndroidJUnit4::class)
class AllenamentoDinamicoUITestTimer {

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
    fun testTimerSiAggiorna() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, AllenamentoDinamicoUI::class.java).apply {
            putExtra("nomeScheda", nomeSchedaTest)
        }

        ActivityScenario.launch<AllenamentoDinamicoUI>(intent).use {

            onView(withId(R.id.timerAllenamento)).check(matches(isDisplayed()))

            Thread.sleep(1500)

            var testoIniziale = ""
            onView(withId(R.id.timerAllenamento)).perform(object : ViewAction {
                override fun getConstraints() = isDisplayed()
                override fun getDescription() = "Leggi testo timer iniziale"
                override fun perform(uiController: androidx.test.espresso.UiController?, view: View?) {
                    val tv = view as? TextView
                    testoIniziale = tv?.text.toString()
                }
            })

            onView(withId(R.id.timerAllenamento)).check(matches(isDisplayed()))
            Thread.sleep(2000)

            onView(withId(R.id.timerAllenamento)).check(matches(not(withText(testoIniziale))))
        }
    }
}