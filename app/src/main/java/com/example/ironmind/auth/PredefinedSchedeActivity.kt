package com.example.ironmind

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.main.PrincipianteActivity
import com.example.ironmind.main.IntermedioActivity
import com.example.ironmind.main.EspertoActivity
import com.example.ironmind.viewmodel.PredefinedSchedeViewModel

class PredefinedSchedeActivity : AppCompatActivity() {

    private val viewModel: PredefinedSchedeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schede_predefinite)

        val toolbarSchedePredefinite = findViewById<Toolbar>(R.id.toolbar_schede_predefinite)
        setSupportActionBar(toolbarSchedePredefinite)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Schede Predefinite"
        }

        toolbarSchedePredefinite.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.schede.observe(this) { lista ->
            // Questo codice è statico, ma ora è collegato a LiveData
            if ("Principiante" in lista) {
                findViewById<CardView>(R.id.cardPrincipiante).setOnClickListener {
                    startActivity(Intent(this, PrincipianteActivity::class.java))
                }
            }

            if ("Intermedio" in lista) {
                findViewById<CardView>(R.id.cardIntermedio).setOnClickListener {
                    startActivity(Intent(this, IntermedioActivity::class.java))
                }
            }

            if ("Esperto" in lista) {
                findViewById<CardView>(R.id.cardEsperto).setOnClickListener {
                    startActivity(Intent(this, EspertoActivity::class.java))
                }
            }
        }
    }
}