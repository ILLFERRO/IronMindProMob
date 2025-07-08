package com.example.ironmind.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.Activity.CurlConcentratoManubrioActivity
import com.example.ironmind.Activity.CurlManubriPancaInclinataActivity
import com.example.ironmind.Activity.LatMachinePresaInversaActivity
import com.example.ironmind.Activity.RematoreBilanciereActivity
import com.example.ironmind.Activity.StaccoDaTerraBilanciereActivity
import com.example.ironmind.viewmodel.DettaglioAllenamentoEsperto2ViewModel

class DettaglioAllenamentoEsperto2Activity : AppCompatActivity() {

    private val viewModel: DettaglioAllenamentoEsperto2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_allenamento_esperto_2)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_Esperto_2)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Allenamento Esperto 2"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<CardView>(R.id.Card_Esercizio_1_2).setOnClickListener {
            startActivity(Intent(this, StaccoDaTerraBilanciereActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_2_2).setOnClickListener {
            startActivity(Intent(this, LatMachinePresaInversaActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_3_2).setOnClickListener {
            startActivity(Intent(this, RematoreBilanciereActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_4_2).setOnClickListener {
            startActivity(Intent(this, CurlConcentratoManubrioActivity::class.java))
        }

        findViewById<CardView>(R.id.Card_Esercizio_5_2).setOnClickListener {
            startActivity(Intent(this, CurlManubriPancaInclinataActivity::class.java))
        }

        findViewById<Button>(R.id.btnCominciaAllenamento).setOnClickListener {
            val nomeScheda = "Esperto 2"
            val esercizi = viewModel.getEsercizi()
            viewModel.salvaScheda(this, nomeScheda, esercizi)

            val intent = Intent(this, AllenamentoDinamicoUI::class.java)
            intent.putExtra("nomeScheda", nomeScheda)
            startActivity(intent)
        }
    }
}