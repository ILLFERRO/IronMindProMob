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
    private val onClick: (GruppoMuscolare) -> Unit //definisce logica per click pulsante
) : RecyclerView.Adapter<GruppiMuscolariAdapter.ViewHolder>() { //chiusura costruttore che estende RecyclerView.Adapter. Quindi mi dice che io sto definendo un adapter per una RecyclerView. Questo adapter mostra gli oggetti come gruppi muscolari e quando clicco il pulsante si attiva la funzione onButtonClick

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //tiene i riferimenti ai widget del layout di un singolo gruppom muscolare
        val nome: TextView = itemView.findViewById(R.id.nomeGruppo)
        val descrizione: TextView = itemView.findViewById(R.id.descrizioneGruppo)
        val icona: ImageView = itemView.findViewById(R.id.iconaGruppo)

        fun bind(gruppo: GruppoMuscolare) { //collega i dati del gruppo muscolare al layout
            nome.text = gruppo.nome
            descrizione.text = gruppo.descrizione
            icona.setImageResource(gruppo.iconaRes) //imposto l'immagine dell'icona
            itemView.setOnClickListener { onClick(gruppo) } //assegna un comprotamento di click sull'intero elemento della lista itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //infla il layout di ogni item (item_gruppo_muscolare.xml) e restituisce un ViewHolder di quei dati, chiamato quando devo creare una nuova riga per il RecyclerView
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_gruppo_muscolare, parent, false) //crea il layout della riga, cioè lo infla e lo inserisce in un nuovo oggetto ViewHolder
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size //ritorna il numero totale di elementi da contare dentro la lista

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //chiamato ogni volta che una riga deve essere riempita con i dati corretti
        holder.bind(lista[position]) //recupera oggetto gruppo muscolare alla posizione lista[position]
    }
}