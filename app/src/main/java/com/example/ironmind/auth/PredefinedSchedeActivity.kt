package com.example.ironmind

import com.example.ironmind.main.PrincipianteActivity //importa schermata PrincipianteActivity
import android.os.Bundle //Necessario per gestire i dati ricevuti all’apertura dell’attività
import androidx.appcompat.app.AppCompatActivity //AppCompatActivity classe base da cui estendo tutte le schermate
import androidx.cardview.widget.CardView //Importa il componente grafico CardView
import androidx.appcompat.widget.Toolbar //importa la classe Toolbar della libreria Androidx
import android.content.Intent //per passare da una attività all'altra
import com.example.ironmind.main.EspertoActivity
import com.example.ironmind.main.IntermedioActivity

class PredefinedSchedeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schede_predefinite)

        val toolbarSchedePredefinite = findViewById<Toolbar>(R.id.toolbar_schede_predefinite)
        setSupportActionBar(toolbarSchedePredefinite) //imposta la toolbar come ActionBar personalizzata
        supportActionBar?.apply { //rappresenta la toolbar che ho impostato
            setDisplayHomeAsUpEnabled(true) //mostra la freccia indietro sulla barra
            title = "Schede Predefinite"
        }

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarSchedePredefinite.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() //questo è il nuovo metodo per gestire meglio la freccia indietro, prima si usava solo onBackPressed() che infatti viene barrato (perchè deprecato)
        }
        //quando clicco sulla freccia torno alla schermata precedente

        // Card cliccabili
        findViewById<CardView>(R.id.cardPrincipiante).setOnClickListener {
            val intentPrincipiante = Intent(this, PrincipianteActivity::class.java)
            startActivity(intentPrincipiante)
        }

        findViewById<CardView>(R.id.cardIntermedio).setOnClickListener {
            val intentIntermedio = Intent(this, IntermedioActivity::class.java)
            startActivity(intentIntermedio)
        }

        findViewById<CardView>(R.id.cardEsperto).setOnClickListener {
            val intentEsperto = Intent(this, EspertoActivity::class.java)
            startActivity(intentEsperto)
        }
    }
}