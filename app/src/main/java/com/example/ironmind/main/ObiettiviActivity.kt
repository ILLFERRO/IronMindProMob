package com.example.ironmind.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ObiettiviActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obiettivi)

        val toolbarWeekActivity = findViewById<Toolbar>(R.id.toolbar_obiettivi)
        setSupportActionBar(toolbarWeekActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Obiettivi"
        }

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupTraining)

        // Carica il giorno salvato per selezionare il RadioButton corretto
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDayNumber = prefs.getInt("start_training_day_number", 1)  // default 1
        val dayIdMap: Map<Int, Int> = mapOf(
            1 to R.id.radioOne,
            2 to R.id.radioTwo,
            3 to R.id.radioThree,
            4 to R.id.radioFour,
            5 to R.id.radioFive,
            6 to R.id.radioSix,
            7 to R.id.radioSeven
        )
        val savedRadioId = dayIdMap[savedDayNumber] ?: R.id.radioOne
        radioGroup.check(savedRadioId)

        //Salvo il giorno con il numero associato
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val numberToDay = mapOf(
                R.id.radioOne to Pair(1, "1 giorno a settimana"),
                R.id.radioTwo to Pair(2, "2 giorni a settimana"),
                R.id.radioThree to Pair(3, "3 giorni a settimana"),
                R.id.radioFour to Pair(4, "4 giorni a settimana"),
                R.id.radioFive to Pair(5, "5 giorni a settimana"),
                R.id.radioSix to Pair(6, "6 giorni a settimana"),
                R.id.radioSeven to Pair(7, "7 giorni a settimana")
            )
            val selected = numberToDay[checkedId]
            if (selected != null) {
                prefs.edit()
                    .putInt("start_training_day_number", selected.first)
                    .putString("start_training_day", selected.second)
                    .apply()
                setResult(RESULT_OK)
            }
        }

        toolbarWeekActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}