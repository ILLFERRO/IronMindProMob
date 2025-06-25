package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity

class EspertoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esperto)

        val toolbarEsperto = findViewById<Toolbar>(R.id.toolbar_esperto)
        setSupportActionBar(toolbarEsperto)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Esperto"
        }

        toolbarEsperto.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisciEsperto = findViewById<Button>(R.id.btnInserisciSchedaEsperto)
        val fromMieSchedeEsperto = intent.getBooleanExtra("fromMieSchede", false) //prende una chiave booleana fromMieSchede e restituisce come valore di default false se non trova quella chiave

        if (fromMieSchedeEsperto) {
            // Se aperta da "Le mie schede", mostra pulsante per eliminare
            btnInserisciEsperto.text = "Elimina Scheda"
            //se trova una scheda e ci entro, allora deve comparire un bottone con testo Elimina Scheda
            btnInserisciEsperto.setOnClickListener { //setta un listener, quando clicco il bottone succede qualcosa
                val preferenzeEsperto = getSharedPreferences("IronMindPrefs", MODE_PRIVATE) //si accede allo SharedPreferences (file dove salvo informazioni permanenti)
                //il file si chiama IronMindPrefs ed è in Mode_Private, cioè accedo solo dall'app
                preferenzeEsperto.edit().putBoolean("schedaEspertoAttiva", false).apply() //modifico i dati, e salvo il valore false alla chiave schedaPrincipianteAttiva e faccio apply, cioè salvo le modifiche in modo asincrono (senza bloccare il thread principale)

                val intentEsperto = Intent(this, DashBoardActivity::class.java)
                intentEsperto.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP //si impostano delle flag che influenzano il comportamento dell'Intent nel gestire lo stack delle attività
                //se siamo nel caso di FLAG_ACTIVITY_CLEAR_TOP allora vuol dire che la DashBoard è già aperta, quindi chiudo tutte le attività prima di quella e la metto come la principale, quindi quando torno indietro, elimino tutto ciò che c'è sopra
                //se invece siamo nel caso di FLAG_ACTIVITY_SINGLE_TOP allora vuol dire che la DashBoard è già in cima allo stack e quindi non creo una nuova schermata ma utilizzo quella già esistente, quindi evito duplicati
                startActivity(intentEsperto)
                finish()
            }
        } else {
            // Se aperta da "Schede Predefinite", mostra pulsante per inserire
            btnInserisciEsperto.setOnClickListener {
                val preferenzeSchedaEsperto = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
                preferenzeSchedaEsperto.edit().putBoolean("schedaEspertoAttiva", true).apply()

                val intentSchedaEsperto = Intent(this, DashBoardActivity::class.java)
                intentSchedaEsperto.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intentSchedaEsperto)
                finish()
            }
        }

        val cardAEsperto = findViewById<CardView>(R.id.Card_Allenamento_A_Esperto)
        val cardBEsperto = findViewById<CardView>(R.id.Card_Allenamento_B_Esperto)
        val cardCEsperto = findViewById<CardView>(R.id.Card_Allenamento_C_Esperto)

        cardAEsperto.setOnClickListener {
            val intentAllenamentoEsperto1 = Intent(this, DettaglioAllenamentoEsperto1Activity::class.java)
            startActivity(intentAllenamentoEsperto1)
        }

        cardBEsperto.setOnClickListener {
            val intentAllenamentoEsperto2 = Intent(this, DettaglioAllenamentoEsperto2Activity::class.java)
            startActivity(intentAllenamentoEsperto2)
        }

        cardCEsperto.setOnClickListener {
            val intentAllenamentoEsperto3 = Intent(this, DettaglioAllenamentoEsperto3Activity::class.java)
            startActivity(intentAllenamentoEsperto3)
        }
    }
}