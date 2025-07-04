package com.example.ironmind.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.ironmind.PredefinedSchedeActivity
import com.example.ironmind.R
import com.example.ironmind.main.*
import java.text.SimpleDateFormat
import java.util.*

class DashBoardActivity : AppCompatActivity() {

    private val viewModel: DashBoardViewModel by viewModels()

    private lateinit var workoutProgressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val settingsIcon = findViewById<ImageView>(R.id.Immagine_impostazioni)
        val rewardsIcon = findViewById<ImageView>(R.id.Immagine_premi)
        settingsIcon.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        rewardsIcon.setOnClickListener {
            startActivity(Intent(this, PremiDisponibiliCompletiActivity::class.java))
        }

        workoutProgressText = findViewById(R.id.workoutProgressText)
        updateWorkoutProgress()

        val predefinedCard = findViewById<CardView>(R.id.predefinedCardsSection)
        val customCard = findViewById<CardView>(R.id.customCardsSection)

        predefinedCard.setOnClickListener {
            startActivity(Intent(this, PredefinedSchedeActivity::class.java))
        }

        customCard.setOnClickListener {
            val nuovoNomeScheda = "Scheda Temporanea"
            SchedaManager.clearScheda(nuovoNomeScheda)
            val intent = Intent(this, SchedaGruppiMuscolariActivity::class.java)
            intent.putExtra("nomeScheda", nuovoNomeScheda)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.titoloMieSchede).visibility = View.VISIBLE

        setupCalendar()
        mostraLeMieSchede()
    }

    private fun updateWorkoutProgress() {
        viewModel.allenamentiCompletati.observe(this) { completati ->
            val obiettivo = viewModel.obiettivo.value ?: 3
            val testoContatore = "Allenamenti Settimanali Completati: $completati / $obiettivo"
            workoutProgressText.text = testoContatore
        }

        viewModel.obiettivo.observe(this) { obiettivo ->
            val completati = viewModel.allenamentiCompletati.value ?: 0
            val testoContatore = "Allenamenti Settimanali Completati: $completati / $obiettivo"
            workoutProgressText.text = testoContatore
        }

        viewModel.caricaDatiContatore()
    }

    private fun setupCalendar() {
        val calendarContainer = findViewById<LinearLayout>(R.id.calendarContainer)
        calendarContainer.removeAllViews()

        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("d", Locale.getDefault())

        val today = Calendar.getInstance()

        for (i in 0 until 7) {
            val dayView = LayoutInflater.from(this).inflate(R.layout.calendario_giornaliero, calendarContainer, false)

            val dayName = dayView.findViewById<TextView>(R.id.dayNameTextView)
            val dayNumber = dayView.findViewById<TextView>(R.id.dayNumberTextView)

            dayName.text = dayFormat.format(calendar.time).replaceFirstChar { it.uppercase() }
            dayNumber.text = dateFormat.format(calendar.time)

            val isToday = calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)

            val bgDrawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                if (isToday) {
                    setColor(Color.parseColor("#7B1FA2"))
                    setStroke((4 * resources.displayMetrics.density).toInt(), Color.BLACK)
                } else {
                    setColor(Color.parseColor("#DDDDDD"))
                }
            }

            dayNumber.background = bgDrawable

            calendarContainer.addView(dayView)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun mostraLeMieSchede() {
        val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)

        val schedaPrincipianteAttiva = prefs.getBoolean("schedaPrincipianteAttiva", false)
        val schedaIntermedioAttiva = prefs.getBoolean("schedaIntermedioAttiva", false)
        val schedaEspertoAttiva = prefs.getBoolean("schedaEspertoAttiva", false)
        val schedePersonalizzate = prefs.getStringSet("mieSchedeNomi", emptySet()) ?: emptySet()

        val sezioneMieSchede = findViewById<LinearLayout>(R.id.sezioneMieSchede)
        sezioneMieSchede.removeAllViews()

        if (schedaPrincipianteAttiva || schedaIntermedioAttiva || schedaEspertoAttiva || schedePersonalizzate.isNotEmpty()) {
            sezioneMieSchede.visibility = View.VISIBLE

            if (schedaPrincipianteAttiva) {
                val card = creaSchedaCard("Scheda Principiante") {
                    startActivity(Intent(this, PrincipianteActivity::class.java).apply {
                        putExtra("fromMieSchede", true)
                    })
                }
                sezioneMieSchede.addView(card)
            }

            if (schedaIntermedioAttiva) {
                val card = creaSchedaCard("Scheda Intermedio") {
                    startActivity(Intent(this, IntermedioActivity::class.java).apply {
                        putExtra("fromMieSchede", true)
                    })
                }
                sezioneMieSchede.addView(card)
            }

            if (schedaEspertoAttiva) {
                val card = creaSchedaCard("Scheda Esperto") {
                    startActivity(Intent(this, EspertoActivity::class.java).apply {
                        putExtra("fromMieSchede", true)
                    })
                }
                sezioneMieSchede.addView(card)
            }

            for (nomeScheda in schedePersonalizzate) {
                val card = creaSchedaCard(nomeScheda) {
                    startActivity(Intent(this, SchedaPersonalizzataCreata::class.java).apply {
                        putExtra("nomeScheda", nomeScheda)
                    })
                }
                sezioneMieSchede.addView(card)
            }

        } else {
            sezioneMieSchede.visibility = View.GONE
        }
    }

    private fun creaSchedaCard(titolo: String, onClick: () -> Unit): View {
        val card = LayoutInflater.from(this).inflate(R.layout.card_scheda_semplice, null, false)

        // Imposta il titolo
        card.findViewById<TextView>(R.id.cardTitle).text = titolo

        // Imposta click listener
        card.setOnClickListener { onClick() }

        // Forza le dimensioni della card (se non le prende dal layout XML)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            (180 * resources.displayMetrics.density).toInt()  // 180dp in pixel
        )
        layoutParams.setMargins(
            (8 * resources.displayMetrics.density).toInt(),
            (8 * resources.displayMetrics.density).toInt(),
            (8 * resources.displayMetrics.density).toInt(),
            (8 * resources.displayMetrics.density).toInt()
        )
        card.layoutParams = layoutParams

        return card
    }

    override fun onResume() {
        super.onResume()
        mostraLeMieSchede()
        updateWorkoutProgress()
    }
}