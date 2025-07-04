package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.auth.DashBoardActivity

class IntermedioActivity : AppCompatActivity() {

    private val viewModel: IntermedioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermedio)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_intermedio)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Intermedio"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisci = findViewById<Button>(R.id.btnInserisciSchedaIntermedio)
        val fromMieSchede = intent.getBooleanExtra("fromMieSchede", false)

        if (fromMieSchede) {
            btnInserisci.text = "Elimina Scheda"
            btnInserisci.setOnClickListener {
                viewModel.eliminaScheda()
            }
        } else {
            btnInserisci.setOnClickListener {
                viewModel.inserisciScheda()
            }
        }

        viewModel.navigaADashboard.observe(this) { vai ->
            if (vai) {
                val intent = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
                viewModel.resetNavigazione()
            }
        }

        findViewById<CardView>(R.id.Card_Allenamento_A_Intermedio).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoIntermedio1Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_B_Intermedio).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoIntermedio2Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_C_Intermedio).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoIntermedio3Activity::class.java))
        }
    }
}