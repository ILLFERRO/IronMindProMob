package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity

class IntermedioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermedio)

        val toolbarIntermedio = findViewById<Toolbar>(R.id.toolbar_intermedio)
        setSupportActionBar(toolbarIntermedio)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Intermedio"
        }

        toolbarIntermedio.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisciIntermedio = findViewById<Button>(R.id.btnInserisciSchedaIntermedio)
        val fromMieSchedeIntermedio = intent.getBooleanExtra("fromMieSchede", false) //prende una chiave booleana fromMieSchede e restituisce come valore di default false se non trova quella chiave

        if (fromMieSchedeIntermedio) {
            // Se aperta da "Le mie schede", mostra pulsante per eliminare
            btnInserisciIntermedio.text = "Elimina Scheda"
            //se trova una scheda e ci entro, allora deve comparire un bottone con testo Elimina Scheda
            btnInserisciIntermedio.setOnClickListener { //setta un listener, quando clicco il bottone succede qualcosa
                val preferenzeIntermedio = getSharedPreferences("IronMindPrefs", MODE_PRIVATE) //si accede allo SharedPreferences (file dove salvo informazioni permanenti)
                //il file si chiama IronMindPrefs ed è in Mode_Private, cioè accedo solo dall'app
                preferenzeIntermedio.edit().putBoolean("schedaIntermedioAttiva", false).apply() //modifico i dati, e salvo il valore false alla chiave schedaPrincipianteAttiva e faccio apply, cioè salvo le modifiche in modo asincrono (senza bloccare il thread principale)

                val intentIntermedio = Intent(this, DashBoardActivity::class.java)
                intentIntermedio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP //si impostano delle flag che influenzano il comportamento dell'Intent nel gestire lo stack delle attività
                //se siamo nel caso di FLAG_ACTIVITY_CLEAR_TOP allora vuol dire che la DashBoard è già aperta, quindi chiudo tutte le attività prima di quella e la metto come la principale, quindi quando torno indietro, elimino tutto ciò che c'è sopra
                //se invece siamo nel caso di FLAG_ACTIVITY_SINGLE_TOP allora vuol dire che la DashBoard è già in cima allo stack e quindi non creo una nuova schermata ma utilizzo quella già esistente, quindi evito duplicati
                startActivity(intentIntermedio)
                finish()
            }
        } else {
            // Se aperta da "Schede Predefinite", mostra pulsante per inserire
            btnInserisciIntermedio.setOnClickListener {
                val preferenzeSchedaIntermedio = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
                preferenzeSchedaIntermedio.edit().putBoolean("schedaIntermedioAttiva", true).apply()

                val intentSchedaIntermedio = Intent(this, DashBoardActivity::class.java)
                intentSchedaIntermedio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intentSchedaIntermedio)
                finish()
            }
        }

        val cardAIntermedio = findViewById<CardView>(R.id.Card_Allenamento_A_Intermedio)
        val cardBIntermedio = findViewById<CardView>(R.id.Card_Allenamento_B_Intermedio)
        val cardCIntermedio = findViewById<CardView>(R.id.Card_Allenamento_C_Intermedio)

        cardAIntermedio.setOnClickListener {
            val intentAllenamentoIntermedio1 = Intent(this, DettaglioAllenamentoIntermedio1Activity::class.java)
            startActivity(intentAllenamentoIntermedio1)
        }

        cardBIntermedio.setOnClickListener {
            val intentAllenamentoIntermedio2 = Intent(this, DettaglioAllenamentoIntermedio2Activity::class.java)
            startActivity(intentAllenamentoIntermedio2)
        }

        cardCIntermedio.setOnClickListener {
            val intentAllenamentoIntermedio3 = Intent(this, DettaglioAllenamentoIntermedio3Activity::class.java)
            startActivity(intentAllenamentoIntermedio3)
        }
    }
}