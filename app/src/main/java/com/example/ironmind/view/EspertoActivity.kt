package com.example.ironmind.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.ironmind.R
import com.example.ironmind.viewmodel.EspertoViewModel

class EspertoActivity : AppCompatActivity() {

    private val viewModel: EspertoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esperto)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_esperto)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Scheda Esperto"
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnInserisci = findViewById<Button>(R.id.btnInserisciSchedaEsperto)
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

        findViewById<CardView>(R.id.Card_Allenamento_A_Esperto).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoEsperto1Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_B_Esperto).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoEsperto2Activity::class.java))
        }

        findViewById<CardView>(R.id.Card_Allenamento_C_Esperto).setOnClickListener {
            startActivity(Intent(this, DettaglioAllenamentoEsperto3Activity::class.java))
        }
    }
}