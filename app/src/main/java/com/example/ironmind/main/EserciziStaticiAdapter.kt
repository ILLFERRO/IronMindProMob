package com.example.ironmind.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class EserciziStaticiAdapter(private var esercizi: List<Esercizio>) :
    RecyclerView.Adapter<EserciziStaticiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nomeEsercizio)
        val descrizione: TextView = view.findViewById(R.id.descrizioneEsercizio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_esercizio_statico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val esercizio = esercizi[position]
        holder.nome.text = esercizio.nome
        holder.descrizione.text = esercizio.descrizione
    }

    override fun getItemCount(): Int = esercizi.size

    fun updateList(newList: List<Esercizio>) {
        esercizi = newList
        notifyDataSetChanged()
    }
}