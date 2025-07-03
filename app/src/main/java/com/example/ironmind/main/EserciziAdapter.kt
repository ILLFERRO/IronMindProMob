package com.example.ironmind.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class EserciziAdapter(
    private val nomeScheda: String,
    private val lista: List<Esercizio>,
    private val onButtonClick: (Esercizio) -> Unit //definisce logica per click pulsante
) : RecyclerView.Adapter<EserciziAdapter.ViewHolder>() { //chiusura costruttore che estende RecyclerView.Adapter. Quindi mi dice che io sto definendo un adapter per una RecyclerView. Questo adapter mostra gli oggetti come esercizi e quando clicco il pulsante si attiva la funzione onButtonClick

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //tiene i riferimenti ai widget del layout di un singolo esercizio
        val nome: TextView = itemView.findViewById(R.id.nomeEsercizio)
        val descrizione: TextView = itemView.findViewById(R.id.descrizioneEsercizio)
        val btnAggiungiRimuovi: Button = itemView.findViewById(R.id.bottoneAzione)

        fun bind(esercizio: Esercizio) { //collega i dati dell'esercizio al layout
            nome.text = esercizio.nome
            descrizione.text = esercizio.descrizione

            // Controlla se l'esercizio è nella scheda specifica
            if (SchedaManager.contiene(nomeScheda, esercizio)) {
                btnAggiungiRimuovi.text = "Rimuovi"
            } else {
                btnAggiungiRimuovi.text = "Aggiungi"
            }

            btnAggiungiRimuovi.setOnClickListener {
                onButtonClick(esercizio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //infla il layout di ogni item (item_esercizio.xml) e restituisce un ViewHolder di quei dati, chiamato quando devo creare una nuova riga per il RecyclerView
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_esercizio, parent, false) //crea il layout della riga, cioè lo infla e lo inserisce in un nuovo oggetto ViewHolder
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size //ritorna il numero totale di elementi da contare dentro la lista

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //chiamato ogni volta che una riga deve essere riempita con i dati corretti
        holder.bind(lista[position]) //recupera oggetto esercizio alla posizione lista[position]
    }
}