package com.example.ironmind.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar

class UnitaPesoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unita_peso)

        val toolbarUnitaPeso = findViewById<Toolbar>(R.id.toolbar_unita_peso)
        setSupportActionBar(toolbarUnitaPeso)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Unita di peso"
        }

        val radioGroupPeso = findViewById<RadioGroup>(R.id.radioGroupPeso)

        // Carica il giorno salvato per selezionare il RadioButton corretto
        val preferenzePeso = getSharedPreferences("settings", MODE_PRIVATE)
        val sceltaPeso = preferenzePeso.getInt("choose_weight", 1)  // default 1
        val dayIdMap: Map<Int, Int> = mapOf(
            1 to R.id.radioPesoUno,
            2 to R.id.radioPesoDue
        )

        val salvaPesi = dayIdMap[sceltaPeso] ?: R.id.radioPesoUno
        radioGroupPeso.check(salvaPesi)

        //Salvo il giorno con il numero associato
        radioGroupPeso.setOnCheckedChangeListener { group, checkedId ->
            val numberToWeight = mapOf(
                R.id.radioPesoUno to Pair(1, "Libbre"),
                R.id.radioPesoDue to Pair(2, "Chilogrammi")
            )

            val selected = numberToWeight[checkedId]
            if (selected != null) {
                preferenzePeso.edit()
                    .putInt("choose_weight", selected.first)
                    .putString("choose_weight_number", selected.second)
                    .apply()

                setResult(RESULT_OK)
            }
        }

        toolbarUnitaPeso.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}