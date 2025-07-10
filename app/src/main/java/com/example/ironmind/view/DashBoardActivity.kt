package com.example.ironmind.view

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
import com.example.ironmind.R
import com.example.ironmind.viewmodel.DashBoardViewModel
import com.example.ironmind.Activity.*
import com.example.ironmind.Utils.SchedaManager
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
        settingsIcon.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        rewardsIcon.setOnClickListener { startActivity(Intent(this, PremiDisponibiliCompletiActivity::class.java)) }

        workoutProgressText = findViewById(R.id.workoutProgressText)

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
        osservaViewModel()
    }

    private fun osservaViewModel() {
        viewModel.allenamentiCompletati.observe(this) { updateWorkoutProgress() }
        viewModel.obiettivo.observe(this) { updateWorkoutProgress() }
        viewModel.schede.observe(this) { mostraLeMieSchede(it) }
        viewModel.caricaDati()
    }

    private fun updateWorkoutProgress() {
        val completati = viewModel.allenamentiCompletati.value ?: 0
        val obiettivo = viewModel.obiettivo.value ?: 3
        val testoContatore = "Allenamenti Settimanali Completati: $completati / $obiettivo"
        workoutProgressText.text = testoContatore
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

    private fun mostraLeMieSchede(schede: List<Pair<String, () -> Unit>>) {
        val sezioneMieSchede = findViewById<LinearLayout>(R.id.sezioneMieSchede)
        sezioneMieSchede.removeAllViews()

        if (schede.isNotEmpty()) {
            sezioneMieSchede.visibility = View.VISIBLE
            for ((titolo, azione) in schede) {
                val card = creaSchedaCard(titolo, azione)
                sezioneMieSchede.addView(card)
            }
        } else {
            sezioneMieSchede.visibility = View.GONE
        }
    }

    private fun creaSchedaCard(titolo: String, onClick: () -> Unit): View {
        val sezioneMieSchede = findViewById<LinearLayout>(R.id.sezioneMieSchede)
        val card = LayoutInflater.from(this).inflate(R.layout.card_scheda_semplice, sezioneMieSchede, false)
        card.findViewById<TextView>(R.id.cardTitle).text = titolo
        card.setOnClickListener { onClick() }
        return card
    }

    override fun onResume() {
        super.onResume()
        viewModel.caricaDati()
    }
}