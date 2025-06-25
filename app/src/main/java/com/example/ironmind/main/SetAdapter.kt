package com.example.ironmind.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class SetAdapter(
    private val listaSet: MutableList<SetAllenamento>,
    private val incrementoPeso: Float // preso dalle impostazioni
) : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNumeroSet: TextView = itemView.findViewById(R.id.txtNumeroSet)
        val txtRipetizioni: TextView = itemView.findViewById(R.id.txtRipetizioni)
        val btnAumentaRip: Button = itemView.findViewById(R.id.btnAumentaRipetizioni)
        val btnDiminuisciRip: Button = itemView.findViewById(R.id.btnDiminuisciRipetizioni)
        val txtPeso: TextView = itemView.findViewById(R.id.txtPeso)
        val btnAumentaPeso: Button = itemView.findViewById(R.id.btnAumentaPeso)
        val btnDiminuisciPeso: Button = itemView.findViewById(R.id.btnDiminuisciPeso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_set_allenamento, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val set = listaSet[position]

        holder.txtNumeroSet.text = "Set ${set.numeroSet}"
        holder.txtRipetizioni.text = "${set.ripetizioni} ripetizioni"
        holder.txtPeso.text = "${set.peso} kg"

        holder.btnAumentaRip.setOnClickListener {
            set.ripetizioni++
            notifyItemChanged(position)
        }

        holder.btnDiminuisciRip.setOnClickListener {
            if (set.ripetizioni > 0) {
                set.ripetizioni--
                notifyItemChanged(position)
            }
        }

        holder.btnAumentaPeso.setOnClickListener {
            set.peso += incrementoPeso
            notifyItemChanged(position)
        }

        holder.btnDiminuisciPeso.setOnClickListener {
            if (set.peso - incrementoPeso >= 0f) {
                set.peso -= incrementoPeso
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = listaSet.size

    fun aggiungiNuovoSet() {
        val ultimoSet = listaSet.lastOrNull()
        val nuovo = SetAllenamento(
            numeroSet = listaSet.size + 1,
            ripetizioni = ultimoSet?.ripetizioni ?: 10,
            peso = ultimoSet?.peso ?: 20f
        )
        listaSet.add(nuovo)
        notifyItemInserted(listaSet.size - 1)
    }

    fun getSetAllenamento(): List<SetAllenamento> = listaSet
}