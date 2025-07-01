package com.example.ironmind.auth

import com.example.ironmind.PredefinedSchedeActivity //importa schermata PredefinedSchedeActivity
import com.example.ironmind.main.PrincipianteActivity //importa schermata PrincipianteActivity
import com.example.ironmind.main.SettingsActivity //importa schermata SettingsActivity
import com.example.ironmind.R //classe generata da Android automaticamente, funge da mappa di tutte le risorse del progetto
import android.graphics.Color //permette di usare i colori tramite codici
import android.graphics.drawable.GradientDrawable //serve per creare sfondi personalizzati (tipo per i cerchi del calendario)
import android.os.Bundle //Necessario per gestire i dati ricevuti all’apertura dell’attività
import android.view.LayoutInflater //Serve per "gonfiare" layout XML in oggetti View in modo dinamico
import android.view.View //Classe base per tutti i componenti visivi Android
import android.widget.ImageView //componenti interfaccia
import android.widget.LinearLayout //componenti interfaccia
import android.widget.TextView //componenti interfaccia
import android.content.Intent //Serve per navigare da un’attività all’altra
import androidx.appcompat.app.AppCompatActivity //AppCompatActivity classe base da cui estendo tutte le schermate
import androidx.cardview.widget.CardView // Importa il componente grafico CardView
import com.example.ironmind.main.EspertoActivity
import com.example.ironmind.main.IntermedioActivity
import com.example.ironmind.main.PremiDisponibiliCompletiActivity
import com.example.ironmind.main.SchedaGruppiMuscolariActivity
import com.example.ironmind.main.SchedaManager
import com.example.ironmind.main.SchedaPersonalizzataCreata
import java.text.SimpleDateFormat //Serve a formattare date
import java.util.* //Serve a formattare date. Calcola i giorni della settimana e controlla il giorno attuale

class DashBoardActivity : AppCompatActivity() {

    private var obiettivoAllenamenti = 3

    private lateinit var workoutProgressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        // Icone
        val settingsIcon = findViewById<ImageView>(R.id.Immagine_impostazioni) //definiamo le immagini delle icone con il metodo findViewById nel layout corrispondente
        val rewardsIcon = findViewById<ImageView>(R.id.Immagine_premi) //definiamo le immagini delle icone con il metodo findViewById nel layout corrispondente
        settingsIcon.setOnClickListener {
            val intentSettings = Intent(this, SettingsActivity::class.java) //apre la schermata delle impostazioni
            startActivity(intentSettings)
        }
        rewardsIcon.setOnClickListener {
            val intentAwards = Intent(this, PremiDisponibiliCompletiActivity::class.java) //apre la schermata delle impostazioni
            startActivity(intentAwards)
        }

        // Contatore (testo completo con bandierina)
        workoutProgressText = findViewById(R.id.workoutProgressText) //inizializzo il testo del contatore degli allenamenti
        updateWorkoutProgress()

        // Card: schede: definisco le card
        val predefinedCard = findViewById<CardView>(R.id.predefinedCardsSection)
        val customCard = findViewById<CardView>(R.id.customCardsSection)

        predefinedCard.setOnClickListener {
            val intent = Intent(this, PredefinedSchedeActivity::class.java) //mi apre la schermata PredefinedSchedeActivity al cliccare della card rispettiva
            startActivity(intent)
        }

        customCard.setOnClickListener {
            // Pulisce gli esercizi precedenti
            val nuovoNomeScheda = "Scheda Temporanea"
            SchedaManager.clearScheda(nuovoNomeScheda)

            // Avvia la schermata per creare una nuova scheda
            val intent = Intent(this, SchedaGruppiMuscolariActivity::class.java)
            intent.putExtra("nomeScheda", nuovoNomeScheda)
            startActivity(intent)
        }

        //Le mie schede
        val titolo = findViewById<TextView>(R.id.titoloMieSchede)
        //val divider = findViewById<View>(R.id.dividerLine)
        //val sezioneMieSchede = findViewById<LinearLayout>(R.id.sezioneMieSchede)
        titolo.visibility = View.VISIBLE //mostra questa View come visibile

        // setto il Calendario
        setupCalendar()

        // Mostra la sezione "Le mie schede"
        mostraLeMieSchede()
    }

    // Funzione per aggiornare il testo del contatore allenamenti
    private fun updateWorkoutProgress() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val completati = prefs.getInt("allenamenti_completati", 0) //legge quanti allenamenti settimanali sono stati completati tramite la chiave allenamenti_completati e se non ne trova, mette 0
        val obiettivo = prefs.getInt("obiettivo_settimanale", 3) //legge l'obiettivo settimanale tramite la chiave obiettivo_settimanale, se non lo trova, mette 3 di default

        val testoContatore = "Allenamenti Settimanali Completati: $completati / $obiettivo"
        findViewById<TextView>(R.id.workoutProgressText).text = testoContatore //mette la scritta qui sopra in base ai risultati ottenuti
    }

    private fun setupCalendar() {
        val calendarContainer = findViewById<LinearLayout>(R.id.calendarContainer)
        calendarContainer.removeAllViews() //rimuove tutte le View figlie dal ViewGroup

        val calendar = Calendar.getInstance() //Prende un calendario usando la zona di tempo di defualt e la locale. Il calendario ritornato è basato sul tempo corrente nella zona di tempo di default con il formato di default locale.
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) //setta il primo giorno del calendario a lunedì

        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault()) // abbreviazione giorni
        val dateFormat = SimpleDateFormat("d", Locale.getDefault())   // numero giorno

        val today = Calendar.getInstance()

        for (i in 0 until 7) {
            val dayView = LayoutInflater.from(this).inflate(R.layout.calendario_giornaliero, calendarContainer, false) //serve a costruire dinamicamente i 7 giorni della settimana nel layout della schermata
            //prende un oggetto layoutinflater dal context this che sarebbe la schermata, R.layout.calendario_giornaliero è il layout Xml che definisce un solo giorno, calendarContainer è la ViewGroup (padre) LinearLayout dove vengono inseriti i 7 giorni, false vuol dire che lo aggiungo in seguito tramite calendarContainer.addView(dayView).

            val dayName = dayView.findViewById<TextView>(R.id.dayNameTextView)
            val dayNumber = dayView.findViewById<TextView>(R.id.dayNumberTextView)

            dayName.text = dayFormat.format(calendar.time).replaceFirstChar { it.uppercase() }
            dayNumber.text = dateFormat.format(calendar.time)

            val isToday = calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)

            val bgDrawable = GradientDrawable().apply { //crea una forma personalizzabile dinamica giorno per giorno. Con apply posso configurare l'oggetto subito dopo averlo creato
                shape = GradientDrawable.OVAL //imposto la forma a ovale
                if (isToday) {
                    setColor(Color.parseColor("#7B1FA2")) // colore viola
                    val borderWidthInPx = (4 * resources.displayMetrics.density).toInt() //aggiunge un bordo nero spesso 4dp, uso displayMetrics per convertire da dp a pixel reali
                    setStroke(borderWidthInPx, Color.BLACK)
                } else {
                    setColor(Color.parseColor("#DDDDDD")) // grigio pallido
                }
            }

            dayNumber.background = bgDrawable //applico la drawable al giorno considerato

            calendarContainer.addView(dayView)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun mostraLeMieSchede() {
        val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)

        val schedaPrincipianteAttiva = prefs.getBoolean("schedaPrincipianteAttiva", false) //legge un valore booleano con chiave "schedaPrincipianteAttiva" e torna false se la chiave non esiste
        val schedaIntermedioAttiva = prefs.getBoolean("schedaIntermedioAttiva", false)
        val schedaEspertoAttiva = prefs.getBoolean("schedaEspertoAttiva", false)
        val schedePersonalizzate = prefs.getStringSet("mieSchedeNomi", emptySet()) ?: emptySet() //legge un Set<String> associato alla chiave "mieSchedeNomi", se la chiave non esiste restituisce un Set vuoto

        val sezioneMieSchede = findViewById<LinearLayout>(R.id.sezioneMieSchede)
        sezioneMieSchede.removeAllViews()

        // Mostra sezione solo se almeno una scheda è presente
        if (schedaPrincipianteAttiva || schedaIntermedioAttiva || schedaEspertoAttiva || schedePersonalizzate.isNotEmpty()) {
            sezioneMieSchede.visibility = View.VISIBLE

            if (schedaPrincipianteAttiva) { //controllo se la scheda Princpiante è attiva
                val card = LayoutInflater.from(this)
                    .inflate(R.layout.card_scheda_semplice, sezioneMieSchede, false) //crea dinamicamente la CardView a partire dal layout card_scheda_semplice, dove sezioneMieSchede è il mio contenitore
                val titolo = card.findViewById<TextView>(R.id.cardTitle) //cambio il titolo per ottenere il nuovo titolo: Scheda Principiante
                titolo.text = "Scheda Principiante"

                card.setOnClickListener {
                    val intent = Intent(this, PrincipianteActivity::class.java)
                    intent.putExtra("fromMieSchede", true) //passo anche un valore extra per capire da dove l'ho aperta, in questo caso passa true se l'ho aperta da fromMieSchede
                    startActivity(intent)
                }

                sezioneMieSchede.addView(card) //aggiunge la CardView al layout padre (sezioneLeMieSchede)
            }

            if (schedaIntermedioAttiva) {
                val card = LayoutInflater.from(this)
                    .inflate(R.layout.card_scheda_semplice, sezioneMieSchede, false)
                val titolo = card.findViewById<TextView>(R.id.cardTitle)
                titolo.text = "Scheda Intermedio"

                card.setOnClickListener {
                    val intent = Intent(this, IntermedioActivity::class.java)
                    intent.putExtra("fromMieSchede", true)
                    startActivity(intent)
                }

                sezioneMieSchede.addView(card)
            }

            if (schedaEspertoAttiva) {
                val card = LayoutInflater.from(this)
                    .inflate(R.layout.card_scheda_semplice, sezioneMieSchede, false)
                val titolo = card.findViewById<TextView>(R.id.cardTitle)
                titolo.text = "Scheda Esperto"

                card.setOnClickListener {
                    val intent = Intent(this, EspertoActivity::class.java)
                    intent.putExtra("fromMieSchede", true)
                    startActivity(intent)
                }

                sezioneMieSchede.addView(card)
            }

            // Schede personalizzate dinamiche
            for (nomeScheda in schedePersonalizzate) {
                val card = LayoutInflater.from(this)
                    .inflate(R.layout.card_scheda_semplice, sezioneMieSchede, false)
                val titolo = card.findViewById<TextView>(R.id.cardTitle)
                titolo.text = nomeScheda

                // Quando clicchi sulla card, apri la schermata con i dettagli della scheda personalizzata
                card.setOnClickListener {
                    val intent = Intent(this, SchedaPersonalizzataCreata::class.java)
                    intent.putExtra("nomeScheda", nomeScheda)
                    startActivity(intent)
                }

                sezioneMieSchede.addView(card)
            }

        } else {
            sezioneMieSchede.visibility = View.GONE //se non c'è nessuna CardView attiva, la sezione non è più visibile, cioè è vuota
        }
    }

    override fun onResume() {
        super.onResume()
        mostraLeMieSchede() // aggiorna ogni volta che si torna alla DashBoard
        val preferenze = getSharedPreferences("settings", MODE_PRIVATE)
        obiettivoAllenamenti = preferenze.getInt("start_training_day_number", 5)  // 5 è default
        updateWorkoutProgress()
    }
}