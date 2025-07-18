    package com.example.ironmind.Adapter
    
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.ironmind.Model.Premio
    import com.example.ironmind.Listener.PremioClickListener
    import com.example.ironmind.R
    
    class PremiAdapter(
        private val premi: List<Premio>,
        private val listener: PremioClickListener? = null
    ) : RecyclerView.Adapter<PremiAdapter.PremioViewHolder>() {
    
        inner class PremioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(premio: Premio) {
                val titolo = itemView.findViewById<TextView>(R.id.titoloPremio)
                val descrizione = itemView.findViewById<TextView>(R.id.descrizionePremio)
                val icona = itemView.findViewById<ImageView>(R.id.iconaPremio)
    
                titolo.text = premio.titolo
                descrizione.text = premio.descrizione
                icona.setImageResource(premio.iconaRes)
    
                itemView.setOnClickListener {
                    listener?.onPremioClicked(premio)
                }
            }
        }
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremioViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_premio, parent, false)
            return PremioViewHolder(view)
        }
    
        override fun onBindViewHolder(holder: PremioViewHolder, position: Int) {
            holder.bind(premi[position])
        }
    
        override fun getItemCount() = premi.size
    }