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
    private val onButtonClick: (Esercizio) -> Unit
) : RecyclerView.Adapter<EserciziAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nomeEsercizio)
        val descrizione: TextView = itemView.findViewById(R.id.descrizioneEsercizio)
        val btnAggiungiRimuovi: Button = itemView.findViewById(R.id.bottoneAzione)

        fun bind(esercizio: Esercizio) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_esercizio, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
}