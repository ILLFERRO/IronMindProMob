package com.example.ironmind.main

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R

class ProfiloNomeCognomeActivity : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextCognome: EditText
    private lateinit var buttonSalva: Button
    private lateinit var buttonModifica: Button
    private lateinit var textViewTitolo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilo_nome_cognome)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profilo_nome)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Nome e Cognome"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        editTextNome = findViewById(R.id.editNome)
        editTextCognome = findViewById(R.id.editCognome)
        buttonSalva = findViewById(R.id.btnSalvaDati)
        buttonModifica = findViewById(R.id.btnModificaDati)
        textViewTitolo = findViewById(R.id.titoloSchermata)

        val prefs = getSharedPreferences("profilo", Context.MODE_PRIVATE)
        val nomeSalvato = prefs.getString("nome", "") ?: ""
        val cognomeSalvato = prefs.getString("cognome", "") ?: ""

        textViewTitolo.text = "Inserisci Nome e Cognome"

        if (nomeSalvato.isNotEmpty() || cognomeSalvato.isNotEmpty()) {
            editTextNome.setText(nomeSalvato)
            editTextCognome.setText(cognomeSalvato)
            buttonModifica.visibility = Button.VISIBLE
        } else {
            buttonModifica.visibility = Button.GONE
        }

        buttonSalva.setOnClickListener {
            val nome = editTextNome.text.toString().trim()
            val cognome = editTextCognome.text.toString().trim()
            if (nome.isNotEmpty() && cognome.isNotEmpty()) {
                prefs.edit().putString("nome", nome).putString("cognome", cognome).apply()
                buttonModifica.visibility = Button.VISIBLE
                textViewTitolo.text = "Dati salvati con successo"
            } else {
                textViewTitolo.text = "Inserisci entrambi i campi"
            }
        }

        buttonModifica.setOnClickListener {
            editTextNome.isEnabled = true
            editTextCognome.isEnabled = true
            textViewTitolo.text = "Modifica Nome e Cognome"
        }
    }
}