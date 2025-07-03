    package com.example.ironmind.main
    
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.ironmind.R
    
    class PremiAdapter(
        private val premi: List<Premio>,
        private val listener: PremioClickListener? = null // definisco logica del pulsante
    ) : RecyclerView.Adapter<PremiAdapter.PremioViewHolder>() { //chiusura costruttore che estende RecyclerView.Adapter. Quindi mi dice che io sto definendo un adapter per una RecyclerView. Questo adapter mostra gli oggetti come premi e quando clicco il pulsante si attiva la funzione onButtonClick
    
        inner class PremioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //tiene i riferimenti ai widget dei layout di un singolo premio
            fun bind(premio: Premio) { //collego i dati al layout
                val titolo = itemView.findViewById<TextView>(R.id.titoloPremio)
                val descrizione = itemView.findViewById<TextView>(R.id.descrizionePremio)
                val icona = itemView.findViewById<ImageView>(R.id.iconaPremio)
    
                titolo.text = premio.titolo
                descrizione.text = premio.descrizione
                icona.setImageResource(premio.iconaRes)
    
                itemView.setOnClickListener {
                    listener?.onPremioClicked(premio) // eseguito solo se il listener esiste
                }
            }
        }
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremioViewHolder {//infla il layout di ogni item (item_premio.xml) e restituisce un ViewHolder di quei dati, chiamato quando devo creare una nuova riga per il RecyclerView
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_premio, parent, false) //crea il layout della riga, cioè lo infla e lo inserisce in un nuovo oggetto ViewHolder
            return PremioViewHolder(view)
        }
    
        override fun onBindViewHolder(holder: PremioViewHolder, position: Int) { //chiamato ogni volta che una riga deve essere riempita con i dati corretti
            holder.bind(premi[position]) //recupera oggetto premio alla posizione lista[position]
        }
    
        override fun getItemCount() = premi.size //conta quanti elementi ci sono
    }