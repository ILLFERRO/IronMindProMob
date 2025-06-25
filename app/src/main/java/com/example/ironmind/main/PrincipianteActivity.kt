package com.example.ironmind.main

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity

class PrincipianteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principiante)

        val toolbarPrincipiante = findViewById<Toolbar>(R.id.toolbar_principiante)
        setSupportActionBar(toolbarPrincipiante)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Principiante"
        }

        toolbarPrincipiante.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisciPrincipiante = findViewById<Button>(R.id.btnInserisciSchedaPrincipiante)
        val fromMieSchedePrincipiante = intent.getBooleanExtra("fromMieSchede", false) //prende una chiave booleana fromMieSchede e restituisce come valore di default false se non trova quella chiave

        if (fromMieSchedePrincipiante) {
            // Se aperta da "Le mie schede", mostra pulsante per eliminare
            btnInserisciPrincipiante.text = "Elimina Scheda"
            //se trova una scheda e ci entro, allora deve comparire un bottone con testo Elimina Scheda
            btnInserisciPrincipiante.setOnClickListener { //setta un listener, quando clicco il bottone succede qualcosa
                val preferenzePrincipiante = getSharedPreferences("IronMindPrefs", MODE_PRIVATE) //si accede allo SharedPreferences (file dove salvo informazioni permanenti)
                //il file si chiama IronMindPrefs ed è in Mode_Private, cioè accedo solo dall'app
                preferenzePrincipiante.edit().putBoolean("schedaPrincipianteAttiva", false).apply() //modifico i dati, e salvo il valore false alla chiave schedaPrincipianteAttiva e faccio apply, cioè salvo le modifiche in modo asincrono (senza bloccare il thread principale)

                val intentPrincipiante = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP //si impostano delle flag che influenzano il comportamento dell'Intent nel gestire lo stack delle attività
                //se siamo nel caso di FLAG_ACTIVITY_CLEAR_TOP allora vuol dire che la DashBoard è già aperta, quindi chiudo tutte le attività prima di quella e la metto come la principale, quindi quando torno indietro, elimino tutto ciò che c'è sopra
                //se invece siamo nel caso di FLAG_ACTIVITY_SINGLE_TOP allora vuol dire che la DashBoard è già in cima allo stack e quindi non creo una nuova schermata ma utilizzo quella già esistente, quindi evito duplicati
                startActivity(intentPrincipiante)
                finish()
            }
        } else {
            // Se aperta da "Schede Predefinite", mostra pulsante per inserire
            btnInserisciPrincipiante.setOnClickListener {
                val preferenzeSchedaPrincipiante = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
                preferenzeSchedaPrincipiante.edit().putBoolean("schedaPrincipianteAttiva", true).apply()

                val intentSchedaPrincipiante = Intent(this, DashBoardActivity::class.java)
                intentSchedaPrincipiante.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intentSchedaPrincipiante)
                finish()
            }
        }

        val cardAPrincipiante = findViewById<CardView>(R.id.Card_Allenamento_A_Principiante)
        val cardBPrincipiante = findViewById<CardView>(R.id.Card_Allenamento_B_Principiante)
        val cardCPrincipiante = findViewById<CardView>(R.id.Card_Allenamento_C_Principiante)

        cardAPrincipiante.setOnClickListener {
            val intentAllenamentoPrincipiante1 = Intent(this, DettaglioAllenamentoPrincipiante1Activity::class.java)
            startActivity(intentAllenamentoPrincipiante1)
        }

        cardBPrincipiante.setOnClickListener {
            val intentAllenamentoPrincipiante2 = Intent(this, DettaglioAllenamentoPrincipiante2Activity::class.java)
            startActivity(intentAllenamentoPrincipiante2)
        }

        cardCPrincipiante.setOnClickListener {
            val intentAllenamentoPrincipiante3 = Intent(this, DettaglioAllenamentoPrincipiante3Activity::class.java)
            startActivity(intentAllenamentoPrincipiante3)
        }
    }
}