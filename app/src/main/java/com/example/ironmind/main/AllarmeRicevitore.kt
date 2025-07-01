package com.example.ironmind.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AllarmeRicevitore : BroadcastReceiver() { //BroadcastReceiver personalizzato. Serve per gestire un evento di sistema. In questo caso l'attivazione di una sveglia. Quindi creo una classe che ascolta eventi broadcast
    override fun onReceive(context: Context, intent: Intent) { //funzione eseguita automaticamente quando viene ricevuto l'evento
        val nome = intent.getStringExtra("nome") ?: "Promemoria" //qui estraggo il valore del nome del promemoria dalla chiave "nome", se non trova niente lascia "Promemoria"
        Toast.makeText(context, "Sveglia: $nome", Toast.LENGTH_LONG).show()
    }
}