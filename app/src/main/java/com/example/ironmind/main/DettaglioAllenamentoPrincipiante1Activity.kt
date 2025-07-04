package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.DettaglioAllenamentoPrincipiante1ViewModel

class DettaglioAllenamentoPrincipiante1Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoPrincipiante1ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_principiante_1)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_principiante_1)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Principiante 1"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_1).setOnClickListener {
            startActivity(Intent(this, ChestPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_1).setOnClickListener {
            startActivity(Intent(this, LatMachineActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_1).setOnClickListener {
            startActivity(Intent(this, LegPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_1).setOnClickListener {
            startActivity(Intent(this, ShoulderPressActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_1).setOnClickListener {
            startActivity(Intent(this, CrunchSulTappetinoActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            viewModel.salvaScheda()
            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", viewModel.nomeScheda)
            startActivity(intent)
        }
    }
}