package com.example.ironmind.Adapter

import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.Utils.AlarmAvviso
import com.example.ironmind.Model.Promemoria
import com.example.ironmind.Activity.PromemoriaActivity
import com.example.ironmind.R

class PromemoriaAdapter(
    private val context: Context,
    private val lista: MutableList<Promemoria>,
    private val onListaChanged: () -> Unit
) : RecyclerView.Adapter<PromemoriaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.tvNome)
        val ora: TextView = view.findViewById(R.id.tvOra)
        val giorno: TextView = view.findViewById(R.id.tvGiorno)
        val switch: Switch = view.findViewById(R.id.switchAttivo)
        val btnModifica: ImageButton = view.findViewById(R.id.btnModifica)
        val btnElimina: ImageButton = view.findViewById(R.id.btnElimina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_promemoria, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.nome.text = item.nome
        holder.ora.text = "Ora: ${item.ora}"
        holder.giorno.text = "Giorno: ${item.giorno}"

        holder.switch.setOnCheckedChangeListener(null)
        holder.switch.isChecked = item.attivo
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            item.attivo = isChecked
            if (isChecked) {
                AlarmAvviso.impostaSveglia(context, item)
            } else {
                AlarmAvviso.cancellaSveglia(context, item)
            }
            onListaChanged()
        }

        holder.btnElimina.setOnClickListener {
            AlarmAvviso.cancellaSveglia(context, item)
            lista.removeAt(position)
            notifyItemRemoved(position)
            onListaChanged()
        }

        holder.btnModifica.setOnClickListener {
            if (context is PromemoriaActivity) {
                context.mostraDialogoModifica(item)
            }
        }
    }
}