package com.example.ironmind.Activity

import android.app.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ironmind.R
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.Adapter.PromemoriaAdapter
import com.example.ironmind.Model.Promemoria
import com.example.ironmind.Utils.AlarmAvviso
import com.example.ironmind.Utils.PromemoriaManager
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PromemoriaActivity : AppCompatActivity() {

    private lateinit var listaPromemoria: MutableList<Promemoria>
    private lateinit var adapter: PromemoriaAdapter
    private var prossimoId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promemoria)

        val toolbarPromemoria = findViewById<Toolbar>(R.id.toolbar_promemoria)
        toolbarPromemoria.title = "Promemoria"
        setSupportActionBar(toolbarPromemoria)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarPromemoria.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        listaPromemoria = PromemoriaManager.carica(this)
        prossimoId = (listaPromemoria.maxOfOrNull { it.id } ?: 0) + 1

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPromemoria)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PromemoriaAdapter(this, listaPromemoria) {
            PromemoriaManager.salva(this, listaPromemoria)
            aggiornaVisibilitaVistaVuota()
        }
        recyclerView.adapter = adapter

        val buttonAdd = findViewById<ImageButton>(R.id.button_add_reminder)
        buttonAdd.setOnClickListener {
            mostraDialogoModifica(null)
        }

        aggiornaVisibilitaVistaVuota()
    }

    fun mostraDialogoModifica(promemoriaEsistente: Promemoria?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_nuovo_promemoria)

        val etNome = dialog.findViewById<EditText>(R.id.etNome)
        val etOra = dialog.findViewById<TextView>(R.id.etOra)
        val spinnerGiorno = dialog.findViewById<Spinner>(R.id.etGiorno)
        val btnSalva = dialog.findViewById<Button>(R.id.btnSalva)

        val giorni = resources.getStringArray(R.array.giorni_settimana)
        val adapterGiorni = ArrayAdapter(this, android.R.layout.simple_spinner_item, giorni)
        adapterGiorni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGiorno.adapter = adapterGiorni

        var oraSelezionata = "08:30"

        if (promemoriaEsistente != null) {
            etNome.setText(promemoriaEsistente.nome)
            oraSelezionata = promemoriaEsistente.ora
            etOra.setText(oraSelezionata)
            val index = giorni.indexOf(promemoriaEsistente.giorno)
            if (index >= 0) spinnerGiorno.setSelection(index)
        } else {
            etOra.setText(oraSelezionata)
        }

        etOra.setOnClickListener {
            val parts = oraSelezionata.split(":")
            val ora = parts[0].toInt()
            val minuto = parts[1].toInt()

            val timePickerDialog = TimePickerDialog(
                this,
                { _, h, m ->
                    oraSelezionata = String.format("%02d:%02d", h, m)
                    etOra.setText(oraSelezionata)
                }, ora, minuto, true
            )
            timePickerDialog.show()
        }

        btnSalva.setOnClickListener {
            val nome = etNome.text.toString()
            val ora = oraSelezionata
            val giorno = spinnerGiorno.selectedItem.toString()

            if (nome.isNotEmpty()) {
                if (promemoriaEsistente == null) {
                    val nuovo = Promemoria(prossimoId++, nome, ora, giorno, true)
                    listaPromemoria.add(nuovo)
                    AlarmAvviso.impostaSveglia(this, nuovo)
                } else {
                    promemoriaEsistente.nome = nome
                    promemoriaEsistente.ora = ora
                    promemoriaEsistente.giorno = giorno
                    AlarmAvviso.impostaSveglia(this, promemoriaEsistente)
                }

                PromemoriaManager.salva(this, listaPromemoria)
                adapter.notifyDataSetChanged()
                aggiornaVisibilitaVistaVuota()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Inserisci un nome valido", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun aggiornaVisibilitaVistaVuota() {
        val viewVuota = findViewById<LinearLayout>(R.id.Viewvuota)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPromemoria)

        if (listaPromemoria.isEmpty()) {
            viewVuota.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            viewVuota.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}