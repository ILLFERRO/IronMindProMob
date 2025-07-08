package com.example.ironmind.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class DettaglioPremioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_premio)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_dettaglio_premio)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Dettaglio Premio"
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val titolo = intent.getStringExtra("premio_titolo") ?: "Titolo non disponibile"
        val descrizione = intent.getStringExtra("premio_descrizione") ?: "Descrizione non disponibile"
        val iconaRes = intent.getIntExtra("premio_icona", R.drawable.ic_launcher_foreground)

        val imgIcona = findViewById<ImageView>(R.id.iconaPremioDettaglio)
        val tvTitolo = findViewById<TextView>(R.id.titoloPremioDettaglio)
        val tvDescrizione = findViewById<TextView>(R.id.descrizionePremioDettaglio)

        imgIcona.setImageResource(iconaRes)
        tvTitolo.text = titolo
        tvDescrizione.text = descrizione
    }
}