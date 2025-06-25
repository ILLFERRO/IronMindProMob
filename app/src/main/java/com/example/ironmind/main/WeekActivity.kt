package com.example.ironmind.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class WeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)

        val toolbarWeekActivity = findViewById<Toolbar>(R.id.toolbar_generali)
        setSupportActionBar(toolbarWeekActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Generali"
        }

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupDays)

        // Carica il giorno salvato per selezionare il RadioButton corretto
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDayNumber = prefs.getInt("start_week_day_number", 1) // default lunedì
        val dayIdMap = mapOf(
            1 to R.id.radioMonday,
            2 to R.id.radioTuesday,
            3 to R.id.radioWednesday,
            4 to R.id.radioThursday,
            5 to R.id.radioFriday,
            6 to R.id.radioSaturday,
            7 to R.id.radioSunday
        )
        val savedRadioId = dayIdMap[savedDayNumber] ?: R.id.radioMonday
        radioGroup.check(savedRadioId)

        //Salvo il giorno con il numero associato
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val numberToDay = mapOf(
                R.id.radioMonday to Pair(1, "Lunedì"),
                R.id.radioTuesday to Pair(2, "Martedì"),
                R.id.radioWednesday to Pair(3, "Mercoledì"),
                R.id.radioThursday to Pair(4, "Giovedì"),
                R.id.radioFriday to Pair(5, "Venerdì"),
                R.id.radioSaturday to Pair(6, "Sabato"),
                R.id.radioSunday to Pair(7, "Domenica")
            )
            val selected = numberToDay[checkedId]
            if (selected != null) {
                prefs.edit()
                    .putInt("start_week_day_number", selected.first)
                    .putString("start_week_day_text", selected.second)
                    .apply()
                setResult(RESULT_OK)
            }
        }
        toolbarWeekActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}