package com.example.ironmind.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import com.example.ironmind.Activity.ObiettiviViewModel

class ObiettiviActivity : AppCompatActivity() {

    private val viewModel: ObiettiviViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obiettivi)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_obiettivi)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Obiettivi"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupTraining)

        val numeroToId = mapOf(
            1 to R.id.radioOne,
            2 to R.id.radioTwo,
            3 to R.id.radioThree,
            4 to R.id.radioFour,
            5 to R.id.radioFive,
            6 to R.id.radioSix,
            7 to R.id.radioSeven
        )

        val idToTesto = mapOf(
            R.id.radioOne to Pair(1, "1 giorno a settimana"),
            R.id.radioTwo to Pair(2, "2 giorni a settimana"),
            R.id.radioThree to Pair(3, "3 giorni a settimana"),
            R.id.radioFour to Pair(4, "4 giorni a settimana"),
            R.id.radioFive to Pair(5, "5 giorni a settimana"),
            R.id.radioSix to Pair(6, "6 giorni a settimana"),
            R.id.radioSeven to Pair(7, "7 giorni a settimana")
        )

        viewModel.giornoInizio.observe(this) { numero ->
            val id = numeroToId[numero] ?: R.id.radioOne
            radioGroup.check(id)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = idToTesto[checkedId]
            if (selected != null) {
                viewModel.salvaGiorno(selected.first, selected.second)
                setResult(RESULT_OK)
            }
        }

        viewModel.caricaGiornoSalvato()
    }
}