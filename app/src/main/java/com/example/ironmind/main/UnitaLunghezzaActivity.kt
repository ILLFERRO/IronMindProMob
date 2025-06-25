package com.example.ironmind.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar

class UnitaLunghezzaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unita_lunghezza)

        val toolbarUnitaLunghezza = findViewById<Toolbar>(R.id.toolbar_unita_lunghezza)
        setSupportActionBar(toolbarUnitaLunghezza)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Unita di lunghezza"
        }

        val radioGroupLunghezza = findViewById<RadioGroup>(R.id.radioGroupLunghezza)

        // Carica il giorno salvato per selezionare il RadioButton corretto
        val preferenzeLunghezza = getSharedPreferences("settings", MODE_PRIVATE)
        val sceltaLunghezza = preferenzeLunghezza.getInt("choose_lenght", 1)  // default 1
        val dayIdMap: Map<Int, Int> = mapOf(
            1 to R.id.radioLunghezzaUno,
            2 to R.id.radioLunghezzaDue
        )

        val salvaLunghezza = dayIdMap[sceltaLunghezza] ?: R.id.radioLunghezzaUno
        radioGroupLunghezza.check(salvaLunghezza)

        //Salvo il giorno con il numero associato
        radioGroupLunghezza.setOnCheckedChangeListener { group, checkedId ->
            val numberToLenght = mapOf(
                R.id.radioLunghezzaUno to Pair(1, "Miglia"),
                R.id.radioLunghezzaDue to Pair(2, "Chilometri")
            )

            val selected = numberToLenght[checkedId]
            if (selected != null) {
                preferenzeLunghezza.edit()
                    .putInt("choose_lenght", selected.first)
                    .putString("choose_length_unit", selected.second)
                    .apply()

                setResult(RESULT_OK)
            }
        }

        toolbarUnitaLunghezza.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}