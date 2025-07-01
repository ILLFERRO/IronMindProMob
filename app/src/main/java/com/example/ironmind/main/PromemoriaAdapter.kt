package com.example.ironmind.main

import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R

class PromemoriaAdapter(
    private val context: Context,
    private val lista: MutableList<Promemoria>,
    private val onListaChanged: () -> Unit  // imposto logica di click del pulsante
) : RecyclerView.Adapter<PromemoriaAdapter.ViewHolder>() { //chiudo il costruttore che estende RecyclerView.Adapter, cioè sto creando un adapter per una RecyclerView. Questo adapter mostra gli oggetti come promemoria

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { //tiene i riferimenti ai widget del layout di un singolo esercizio
        val nome: TextView = view.findViewById(R.id.tvNome)
        val ora: TextView = view.findViewById(R.id.tvOra)
        val giorno: TextView = view.findViewById(R.id.tvGiorno)
        val switch: Switch = view.findViewById(R.id.switchAttivo)
        val btnModifica: ImageButton = view.findViewById(R.id.btnModifica)
        val btnElimina: ImageButton = view.findViewById(R.id.btnElimina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //infla il layout di ogni item (item_promemoria.xml) e restituisce un ViewHolder di quei dati, chiamato quando devo creare una nuova riga per il RecyclerView
        val view = LayoutInflater.from(context).inflate(R.layout.item_promemoria, parent, false) //crea il layout della riga, cioè lo infla e lo inserisce in un nuovo oggetto ViewHolder
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size //ritorna il numero totale di elementi da contare dentro la lista

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //chiamato ogni volta che una riga deve essere riempita con i dati corretti
        val item = lista[position] //recupera oggetto promemoria alla posizione lista[position]

        //imposto i dati nel layout, imposta i valori testuali nei TextView dell'item visualizzato (nome del promemoria, ora, giorno).
        holder.nome.text = item.nome
        holder.ora.text = "Ora: ${item.ora}"
        holder.giorno.text = "Giorno: ${item.giorno}"

        //Gestione dello switch (attivo/disattivo)
        holder.switch.setOnCheckedChangeListener(null) // evita ri-trigger durante il recycling, disattiva momentaneamente il listener dello switch per evitare problemi quando la RecyclerView riutilizza le view.
        holder.switch.isChecked = item.attivo
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            item.attivo = isChecked
            if (isChecked) {
                AlarmAvviso.impostaSveglia(context, item)
            } else {
                AlarmAvviso.cancellaSveglia(context, item)
            }
            onListaChanged()  // notifica che la lista è cambiata
        }

        //Gestione del pulsante Elimina
        holder.btnElimina.setOnClickListener {
            AlarmAvviso.cancellaSveglia(context, item) //annulla sveglia
            lista.removeAt(position) //rimuove il promemoria dalla lista alla posizione position, aggiorna RecyclerView
            notifyItemRemoved(position) //notifica il cambiamento
            onListaChanged()  // notifica che la lista è cambiata
        }

        //Gestione del pulsante Modifica
        holder.btnModifica.setOnClickListener {
            if (context is PromemoriaActivity) { //Controlla se il context è l’attività PromemoriaActivity.
                context.mostraDialogoModifica(item) //Se sì, chiama una funzione per mostrare un dialogo di modifica per il promemoria selezionato.
            }
        }
    }
}