package com.example.ironmind.main

import android.view.LayoutInflater //Serve per "gonfiare" layout XML in oggetti View in modo dinamico
import android.view.View //classe base di tutti i componenti grafici di Android.
import android.view.ViewGroup //importa classe ViewGroup che estende View
import android.widget.Button //importa classe Button
import android.widget.TextView //componenti interfaccia
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R //classe generata da Android automaticamente, funge da mappa di tutte le risorse del progetto

class TrainingAdapter(
    private val items: List<String>, //lista di allenamenti (items)
    private val onCompleteClick: (String) -> Unit //lambda function che si attiva quando clicco il pulsante "Completa" passando il nome dell'allenamento
) : RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>() { //adapter usa un ViewHolder chiamato TrainingViewHolder

    // ViewHolder: rappresenta ogni elemento della RecyclerView
    class TrainingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trainingName: TextView = view.findViewById(R.id.tvTrainingName) //mostra il nome dell'allenamento
        val completeBtn: Button = view.findViewById(R.id.btnComplete) //button cliccabile per segnarlo come completato
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder { //chiamato quando la RecyclerView deve creare una nuova riga
        val view = LayoutInflater.from(parent.context) //usa il LayoutInflater per creare una nuova vista a partire da item_training
            .inflate(R.layout.item_training, parent, false)
        return TrainingViewHolder(view) //ritorna un nuovo TrainingViewHolder che rappresenta quella riga
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) { //chiamato per riempire ogni riga con i dati giusti
        val item = items[position]
        holder.trainingName.text = item //prende l'item alla posizione corretta, lo mostra nel TextView
        holder.completeBtn.setOnClickListener { //imposta un listener sul bottone, esegue la funzione onCompleteClick(item) passata al costruttore
            onCompleteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size //dice alla RecyclerView quanti elementi visualizzare
}