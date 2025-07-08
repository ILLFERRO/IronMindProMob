package com.example.ironmind.main

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class PremiPersonaliActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PremiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premi_personali)

        val toolbarPremiPersonali = findViewById<Toolbar>(R.id.toolbar_premi_personali)
        setSupportActionBar(toolbarPremiPersonali)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Premi Personali"
        }
        toolbarPremiPersonali.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        recyclerView = findViewById(R.id.recyclerViewMieiPremi)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val premiPrefs = getSharedPreferences("premi_sbloccati", MODE_PRIVATE)
        PremiRepository.listaPremi.forEach {
            it.sbloccato = premiPrefs.getBoolean(it.titolo, false)
        }
        val mieiPremi = PremiRepository.listaPremi.filter { it.sbloccato }

        val emptyState = findViewById<LinearLayout>(R.id.emptyStateContainer)
        val headerPremi = findViewById<LinearLayout>(R.id.headerPremi)

        if (mieiPremi.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            headerPremi.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            headerPremi.visibility = View.VISIBLE
        }

        adapter = PremiAdapter(mieiPremi)
        recyclerView.adapter = adapter
    }
}