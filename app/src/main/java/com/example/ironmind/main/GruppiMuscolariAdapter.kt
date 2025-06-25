package com.example.ironmind.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class GruppiMuscolariAdapter(
    private val lista: List<GruppoMuscolare>,
    private val onClick: (GruppoMuscolare) -> Unit
) : RecyclerView.Adapter<GruppiMuscolariAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nomeGruppo)
        val descrizione: TextView = itemView.findViewById(R.id.descrizioneGruppo)
        val icona: ImageView = itemView.findViewById(R.id.iconaGruppo)

        fun bind(gruppo: GruppoMuscolare) {
            nome.text = gruppo.nome
            descrizione.text = gruppo.descrizione
            icona.setImageResource(gruppo.iconaRes)
            itemView.setOnClickListener { onClick(gruppo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_gruppo_muscolare, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
}