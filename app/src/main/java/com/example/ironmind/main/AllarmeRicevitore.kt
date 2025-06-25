package com.example.ironmind.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val nome = intent.getStringExtra("nome") ?: "Promemoria"
        Toast.makeText(context, "Sveglia: $nome", Toast.LENGTH_LONG).show()
    }
}