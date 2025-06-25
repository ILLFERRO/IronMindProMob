package com.example.ironmind.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar

class DimensioniUnitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimensioni_unita)

        val toolbarDimensioniUnita = findViewById<Toolbar>(R.id.toolbar_unita_dimensione)
        setSupportActionBar(toolbarDimensioniUnita)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Unita di dimensione"
        }

        val radioGroupDimensione = findViewById<RadioGroup>(R.id.radioGroupDimensione)

        // Carica il giorno salvato per selezionare il RadioButton corretto
        val preferenzeDimensione = getSharedPreferences("settings", MODE_PRIVATE)
        val sceltaDimensinoe = preferenzeDimensione.getInt("choose_dimension", 1)  // default 1
        val dayIdMap: Map<Int, Int> = mapOf(
            1 to R.id.radioDimensioneUno,
            2 to R.id.radioDimensioneDue
        )

        val salvaDimensione = dayIdMap[sceltaDimensinoe] ?: R.id.radioDimensioneUno
        radioGroupDimensione.check(salvaDimensione)

        //Salvo il giorno con il numero associato
        radioGroupDimensione.setOnCheckedChangeListener { group, checkedId ->
            val numberToDimension = mapOf(
                R.id.radioDimensioneUno to Pair(1, "Pollici"),
                R.id.radioDimensioneDue to Pair(2, "Centimetri")
            )

            val selected = numberToDimension[checkedId]
            if (selected != null) {
                preferenzeDimensione.edit()
                    .putInt("choose_dimension", selected.first)
                    .putString("choose_dimension_unit", selected.second)
                    .apply()

                setResult(RESULT_OK)
            }
        }

        toolbarDimensioniUnita.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}