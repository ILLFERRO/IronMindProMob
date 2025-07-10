package com.example.ironmind.Activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import java.util.*
import android.view.View

class ProfiloEtaDataDiNascitaActivity : AppCompatActivity() {

    private lateinit var editTextEta: EditText
    private lateinit var textViewDataNascita: TextView
    private lateinit var buttonSalva: Button
    private lateinit var buttonModifica: Button

    private var dataNascitaSelezionata: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilo_eta_data_di_nascita)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profilo_eta_data_di_nascita)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Età e Data di Nascita"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        editTextEta = findViewById(R.id.editTextEta)
        textViewDataNascita = findViewById(R.id.textViewDataNascita)
        buttonSalva = findViewById(R.id.buttonSalvaEta)
        buttonModifica = findViewById(R.id.buttonModificaEta)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val etaSalvata = prefs.getString("profilo_eta", "")
        val dataSalvata = prefs.getString("profilo_data_nascita", "")

        // Se i dati sono salvati, mostro i campi e rendo visibile solo il pulsante "Modifica"
        if (etaSalvata!!.isNotBlank() && dataSalvata!!.isNotBlank()) {
            editTextEta.setText(etaSalvata)
            textViewDataNascita.text = dataSalvata
            editTextEta.isEnabled = false
            textViewDataNascita.isEnabled = false
            buttonModifica.visibility = View.VISIBLE
            buttonSalva.visibility = View.GONE
        } else {
            // Nessun dato salvato, permetto subito la modifica
            editTextEta.isEnabled = true
            textViewDataNascita.isEnabled = true
            buttonModifica.visibility = View.GONE
            buttonSalva.visibility = View.VISIBLE
        }

        textViewDataNascita.setOnClickListener {
            if (textViewDataNascita.isEnabled) {
                mostraDatePicker()
            }
        }

        buttonModifica.setOnClickListener {
            editTextEta.isEnabled = true
            textViewDataNascita.isEnabled = true
            buttonModifica.visibility = View.GONE
            buttonSalva.visibility = View.VISIBLE
            Toast.makeText(this, "Ora puoi modificare i dati", Toast.LENGTH_SHORT).show()
        }

        buttonSalva.setOnClickListener {
            val eta = editTextEta.text.toString()
            val data = textViewDataNascita.text.toString()

            if (eta.isBlank() || data == "Seleziona data di nascita" || data.isBlank()) {
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString("profilo_eta", eta)
                .putString("profilo_data_nascita", data)
                .apply()

            Toast.makeText(this, "Dati salvati", Toast.LENGTH_SHORT).show()

            editTextEta.isEnabled = false
            textViewDataNascita.isEnabled = false
            buttonSalva.visibility = View.GONE
            buttonModifica.visibility = View.VISIBLE
        }
    }

    private fun mostraDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                dataNascitaSelezionata = "$dayOfMonth/${month + 1}/$year"
                textViewDataNascita.text = dataNascitaSelezionata
            },
            2000, 0, 1
        )

        datePicker.datePicker.minDate = Calendar.getInstance().apply { set(1950, 0, 1) }.timeInMillis
        datePicker.datePicker.maxDate = Calendar.getInstance().apply { set(2010, 11, 31) }.timeInMillis
        datePicker.show()
    }
}
