package com.example.ironmind.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.Adapter.PremiAdapter
import com.example.ironmind.R
import com.example.ironmind.Repository.PremiRepository
import com.google.android.material.button.MaterialButton

class PremiDisponibiliCompletiActivity : AppCompatActivity() {

    private lateinit var premiRecyclerView: RecyclerView
    private lateinit var premiAdapter: PremiAdapter
    private lateinit var btnVaiAiMieiPremi: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premi_disponibili_completi)

        val toolbarPremiDisponibili = findViewById<Toolbar>(R.id.toolbar_premi_disponibili)
        setSupportActionBar(toolbarPremiDisponibili)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Premi Disponibili"
        }
        toolbarPremiDisponibili.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        premiRecyclerView = findViewById(R.id.recyclerViewPremi)
        premiRecyclerView.layoutManager = LinearLayoutManager(this)

        premiAdapter = PremiAdapter(PremiRepository.listaPremi)
        premiRecyclerView.adapter = premiAdapter

        btnVaiAiMieiPremi = findViewById(R.id.btnVaiAiMieiPremi)
        btnVaiAiMieiPremi.setOnClickListener {
            startActivity(Intent(this, PremiPersonaliActivity::class.java))
        }
    }
}
