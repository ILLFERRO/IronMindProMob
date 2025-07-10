package com.example.ironmind.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.PrincipianteViewModel

class PrincipianteActivity : AppCompatActivity() {

    private val viewModel: PrincipianteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principiante)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_principiante)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Principiante"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisciScheda = findViewById<Button>(R.id.btnInserisciSchedaPrincipiante)
        val fromMieSchede = intent.getBooleanExtra("fromMieSchede", false)
        viewModel.inizializza(fromMieSchede)

        viewModel.fromMieSchede.observe(this) { daMieSchede ->
            if (daMieSchede) {
                btnInserisciScheda.text = "Elimina Scheda"
                btnInserisciScheda.setOnClickListener {
                    viewModel.disattivaScheda()
                }
            } else {
                btnInserisciScheda.setOnClickListener {
                    viewModel.attivaScheda()
                }
            }
        }

        viewModel.navigaDashboard.observe(this) { vai ->
            if (vai) {
                val intent = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
                viewModel.resetNavigazione()
            }
        }

        findViewById<CardView>(R.id.Card_Allenamento_A_Principiante).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoPrincipiante1Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_B_Principiante).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoPrincipiante2Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_C_Principiante).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoPrincipiante3Activity::class.java))
        }
    }
}