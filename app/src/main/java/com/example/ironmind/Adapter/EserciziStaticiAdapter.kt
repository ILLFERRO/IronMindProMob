package com.example.ironmind.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.Model.Esercizio
import com.example.ironmind.R

class EserciziStaticiAdapter(private var esercizi: List<Esercizio>) :
    RecyclerView.Adapter<EserciziStaticiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nomeEsercizio)
        val descrizione: TextView = view.findViewById(R.id.descrizioneEsercizio)
        val setCompletati: TextView = view.findViewById(R.id.setCompletati)
        val ripetizioni: TextView = view.findViewById(R.id.ripetizioni)
        val pesoTempo: TextView = view.findViewById(R.id.pesoTempo)
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

        // Formatta ripetizioniPerSet come "2x8 + 1x10"
        holder.ripetizioni.text = formatRipetizioni(esercizio.ripetizioniPerSet)

        // Formatta peso o tempo recupero per set
        val pesoOrRecupero = if (esercizio.usaPeso) {
            formatPeso(esercizio.pesoPerSet)
        } else {
            formatRecupero(esercizio.tempoRecuperoPerSet)
        }
        holder.pesoTempo.text = pesoOrRecupero

        holder.setCompletati.text = "Set completati: ${esercizio.setCompletati}"
    }

    private fun formatRipetizioni(ripetizioni: List<Int>): String {
        if (ripetizioni.isEmpty()) return ""
        val grouped = ripetizioni.groupingBy { it }.eachCount()
        return grouped.entries.joinToString(" + ") { "${it.value}x${it.key}" }
    }

    private fun formatPeso(pesi: List<Float>): String {
        if (pesi.isEmpty()) return ""
        val grouped = pesi.groupingBy { it }.eachCount()
        return grouped.entries.joinToString(" + ") { "${it.value}x${it.key}kg" }
    }

    private fun formatRecupero(recuperi: List<Int>): String {
        if (recuperi.isEmpty()) return ""
        val grouped = recuperi.groupingBy { it }.eachCount()
        return grouped.entries.joinToString(" + ") { "${it.value}x${it.key}s" }
    }

    override fun getItemCount(): Int = esercizi.size
}