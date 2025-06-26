package com.example.ironmind.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import android.widget.Button
import android.app.AlertDialog
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_START_WEEK = 1001
        const val REQUEST_CODE_WEEKLY_GOAL = 1002
        const val REQUEST_CODE_WEIGHT_UNIT = 1003
        const val REQUEST_CODE_LENGHT_UNIT = 1004
        const val REQUEST_CODE_DIMENSION_UNIT = 1005
    }

    private lateinit var startWeekTextView: TextView
    private lateinit var startTrainingDayTextView: TextView
    private lateinit var startWeightTextView: TextView
    private lateinit var startLenghtTextView: TextView
    private lateinit var startDimensionTextView: TextView
    private lateinit var weightIncrementTextView: TextView
    private lateinit var KeepScreenActiveTextView: TextView
    private lateinit var ShowNotificationTextView: TextView
    private lateinit var VibrateOnFinishTextView: TextView
    private lateinit var SoundOnFinishTextView: TextView
    private lateinit var DefaultSetsTextView: TextView
    private lateinit var DefaultTempoDiRiposoTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Impostazioni"
        }

        //Profilo Nome & Cognome
        findViewById<TextView>(R.id.nome_cognome).setOnClickListener {
            startActivity(Intent(this, ProfiloNomeCognomeActivity::class.java))
        }

        //Profilo Età & Data Di Nascita
        findViewById<TextView>(R.id.eta_data_di_nascita).setOnClickListener {
            startActivity(Intent(this, ProfiloEtaDataDiNascitaActivity::class.java))
        }

        //Profilo Sesso
        findViewById<TextView>(R.id.sesso_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloSessoActivity::class.java))
        }

        //Profilo Peso & Altezza
        findViewById<TextView>(R.id.peso_altezza_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloPesoAltezzaActivity::class.java))
        }

        //profilo QrCode
        findViewById<TextView>(R.id.qr_code_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloQrCodeActivity::class.java))
        }

        //Backup & Ripristino
        findViewById<TextView>(R.id.backup_ripristino).setOnClickListener {
            startActivity(Intent(this, BackupRipristinoActivity::class.java))
        }

        // Primo giorno della settimana
        startWeekTextView = findViewById(R.id.startWeek)
        updateStartWeekText()
        startWeekTextView.setOnClickListener {
            val intentWeek = Intent(this, WeekActivity::class.java)
            startActivityForResult(intentWeek, REQUEST_CODE_START_WEEK)
        }

        // Obiettivo settimanale
        startTrainingDayTextView = findViewById(R.id.weeklyGoal)
        updateStartTrainingText()
        startTrainingDayTextView.setOnClickListener {
            val intentTraining = Intent(this, ObiettiviActivity::class.java)
            startActivityForResult(intentTraining, REQUEST_CODE_WEEKLY_GOAL)
        }

        // Unità di peso
        startWeightTextView = findViewById(R.id.weightUnit)
        updateStartWeight()
        startWeightTextView.setOnClickListener {
            val intentWeight = Intent(this, UnitaPesoActivity::class.java)
            startActivityForResult(intentWeight, REQUEST_CODE_WEIGHT_UNIT)
        }

        // Promemoria (solo startActivity, non serve risultato)
        findViewById<TextView>(R.id.reminders).setOnClickListener {
            startActivity(Intent(this, PromemoriaActivity::class.java))
        }

        //Unita di Lunghezza
        startLenghtTextView = findViewById(R.id.lengthUnit)
        updateStartLenght()
        startLenghtTextView.setOnClickListener {
            val intentLenght = Intent(this, UnitaLunghezzaActivity::class.java)
            startActivityForResult(intentLenght, REQUEST_CODE_LENGHT_UNIT)
        }

        //Unita di Dimensione
        startDimensionTextView = findViewById(R.id.sizeUnit)
        updateStartDimension()
        startDimensionTextView.setOnClickListener {
            val intentDimension = Intent(this, DimensioniUnitaActivity::class.java)
            startActivityForResult(intentDimension, REQUEST_CODE_DIMENSION_UNIT)
        }

        //Incremento di peso
        weightIncrementTextView = findViewById<TextView>(R.id.weightIncrement)
        updateIncrementWeight()
        weightIncrementTextView.setOnClickListener{
            val intentIncrementWeight = Intent(this, IncrementoPesoActivity::class.java)
            startActivity(intentIncrementWeight)
        }

        //Tenere Schermo Acceso
        KeepScreenActiveTextView = findViewById<TextView>(R.id.keepScreenOn)
        updateSchermoAccesoSetting()
        KeepScreenActiveTextView.setOnClickListener{
            val intentSchermoAcceso = Intent(this, SchermoAccesoActivity::class.java)
            startActivity(intentSchermoAcceso)
        }

        //Mostra Notifica
        ShowNotificationTextView = findViewById<TextView>(R.id.showNotification)
        updateMostraNotifica()
        ShowNotificationTextView.setOnClickListener{
            val intentMostraNotifica = Intent(this, MostraNotificaActivity::class.java)
            startActivity(intentMostraNotifica)
        }

        //Vibra Al Termine
        VibrateOnFinishTextView = findViewById<TextView>(R.id.vibrateOnFinish)
        updateVibrazionAttiva()
        VibrateOnFinishTextView.setOnClickListener{
            val intentVibrazione = Intent(this, VibraAlTermineActivity::class.java)
            startActivity(intentVibrazione)
        }

        //Suona Al Termine
        SoundOnFinishTextView = findViewById<TextView>(R.id.soundOnFinish)
        updateSuoneriaAttiva()
        SoundOnFinishTextView.setOnClickListener{
            val intentSuoneria = Intent(this, SuonaAlTermineActivity::class.java)
            startActivity(intentSuoneria)
        }

        //Set Di Default
        DefaultSetsTextView = findViewById<TextView>(R.id.defaultSets)
        updateSetDefault()
        DefaultSetsTextView.setOnClickListener{
            val intentSetDefault = Intent(this, SetDefaultActivity::class.java)
            startActivity(intentSetDefault)
        }

        DefaultTempoDiRiposoTextView = findViewById(R.id.defaultRestTime)
        updateTempoDiRiposo()
        DefaultTempoDiRiposoTextView.setOnClickListener {
            val intentTempoDiRiposo = Intent(this, TempoDiRiposoActivity::class.java)
            startActivity(intentTempoDiRiposo)
        }

        val btnResetAllenamenti = findViewById<Button>(R.id.btn_reset_allenamenti)
        btnResetAllenamenti.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Conferma azzeramento")
                .setMessage("Sei sicuro di voler azzerare il conteggio degli allenamenti completati?")
                .setPositiveButton("Sì") { _, _ ->
                    val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                    prefs.edit().putInt("allenamenti_completati", 0).apply()
                }
                .setNegativeButton("Annulla", null)
                .show()
        }

        val btnResetStatistiche = findViewById<Button>(R.id.btn_reset_statistiche)
        btnResetStatistiche.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Conferma ripristino")
                .setMessage("Vuoi davvero cancellare tutte le statistiche degli allenamenti?\nL'azione è irreversibile.")
                .setPositiveButton("Sì") { _, _ ->
                    val prefsStats = getSharedPreferences("allenamento_stats", MODE_PRIVATE)
                    prefsStats.edit().clear().apply()
                }
                .setNegativeButton("Annulla", null)
                .show()
        }

        val btnResetPremi = findViewById<Button>(R.id.btn_reset_premi)
        btnResetPremi.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Conferma azzeramento premi")
                .setMessage("Sei sicuro di voler eliminare tutti i premi sbloccati?")
                .setPositiveButton("Sì") { _, _ ->
                    val prefsPremi = getSharedPreferences("premi_sbloccati", MODE_PRIVATE)
                    prefsPremi.edit().clear().apply()

                    // Aggiorno lista in memoria per reflect UI
                    PremiRepository.listaPremi.forEach { it.sbloccato = false }
                    Toast.makeText(this, "Premi resettati", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Annulla", null)
                .show()
        }


        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_START_WEEK -> updateStartWeekText()
                REQUEST_CODE_WEEKLY_GOAL -> updateStartTrainingText()
                REQUEST_CODE_WEIGHT_UNIT -> updateStartWeight()
                REQUEST_CODE_LENGHT_UNIT -> updateStartLenght()
                REQUEST_CODE_DIMENSION_UNIT -> updateStartDimension()
            }
        }
    }

    private fun updateStartWeekText() {
        val preferenzeWeek = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDayName = preferenzeWeek.getString("start_week_day_text", "Lunedì")
        startWeekTextView.text = "Primo giorno della settimana: $savedDayName"
    }

    private fun updateStartTrainingText() {
        val preferenzeTraining = getSharedPreferences("settings", MODE_PRIVATE)
        val savedGoal = preferenzeTraining.getString("start_training_day", "1 giorno a settimana")
        startTrainingDayTextView.text = "Obiettivo settimanale: $savedGoal"
    }

    private fun updateStartWeight() {
        val preferenzePeso = getSharedPreferences("settings", MODE_PRIVATE)
        val savedWeight = preferenzePeso.getString("choose_weight_number", "Libbre")
        startWeightTextView.text = "Unità di peso: $savedWeight"
    }

    private fun updateStartLenght() {
        val preferenzeLunghezza = getSharedPreferences("settings", MODE_PRIVATE)
        val savedLenght = preferenzeLunghezza.getString("choose_length_unit", "Miglia")
        startLenghtTextView.text = "Unità di lunghezza: $savedLenght"
    }

    private fun updateStartDimension() {
        val preferenzeDimensione = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDimension = preferenzeDimensione.getString("choose_dimension_unit", "Pollici")
        startDimensionTextView.text = "Unità di dimensione: $savedDimension"
    }

    private fun updateIncrementWeight(){
        val preferenzeIncrementoPeso = getSharedPreferences("settings", MODE_PRIVATE)
        val salvaIncremento = preferenzeIncrementoPeso.getString("peso_incremento", "2.5")
        weightIncrementTextView.text = "Incremento di peso: $salvaIncremento kg"
    }

    private fun updateSchermoAccesoSetting() {
        val preferenzeSchermoAcceso = getSharedPreferences("settings", MODE_PRIVATE)
        val schermoAccesoStato = preferenzeSchermoAcceso.getBoolean("schermo_acceso_attivo", false)

        val stato = if (schermoAccesoStato) "attivo" else "disattivato"
        KeepScreenActiveTextView.text = "Tenere schermo acceso: $stato"
    }

    private fun updateMostraNotifica() {
        val preferenzeMostraNotifica = getSharedPreferences("settings", MODE_PRIVATE)
        val mostraNotificaStato = preferenzeMostraNotifica.getBoolean("mostra_notifica", false)

        val stato = if (mostraNotificaStato) "attivo" else "disattivato"
        ShowNotificationTextView.text = "Mostra Notifica: $stato"
    }

    private fun updateVibrazionAttiva() {
        val preferenzeVibrazione = getSharedPreferences("settings", MODE_PRIVATE)
        val VibrazioneAttivaStato = preferenzeVibrazione.getBoolean("vibrazione_attiva", false)

        val stato = if (VibrazioneAttivaStato) "attivo" else "disattivato"
        VibrateOnFinishTextView.text = "Vibra Al Termine: $stato"
    }

    private fun updateSuoneriaAttiva() {
        val preferenzeSuoneria = getSharedPreferences("settings", MODE_PRIVATE)
        val SuoneriaAttivaStato = preferenzeSuoneria.getBoolean("suoneria_attiva", false)

        val stato = if (SuoneriaAttivaStato) "attivo" else "disattivato"
        SoundOnFinishTextView.text = "Suona Al Termine: $stato"
    }

    private fun updateSetDefault(){
        val preferenzeSetDefault = getSharedPreferences("settings", MODE_PRIVATE)
        val salvaSet = preferenzeSetDefault.getString("Set_Default", "3")
        DefaultSetsTextView.text = "Set Di Default: $salvaSet"
    }

    private fun updateTempoDiRiposo() {
        val preferenzeTempoDiRiposo = getSharedPreferences("settings", MODE_PRIVATE)
        val minuti = preferenzeTempoDiRiposo.getInt("riposo_min", 0)
        val secondi = preferenzeTempoDiRiposo.getInt("riposo_sec", 30)
        DefaultTempoDiRiposoTextView.text = "Tempo di riposo: ${minuti}:${String.format("%02d", secondi)}"
    }

    override fun onResume() {
        super.onResume()
        updateStartWeekText()
        updateStartTrainingText()
        updateStartWeight()
        updateStartLenght()
        updateStartDimension()
        updateIncrementWeight()
        updateSchermoAccesoSetting()
        updateMostraNotifica()
        updateVibrazionAttiva()
        updateSuoneriaAttiva()
        updateSetDefault()
        updateTempoDiRiposo()
    }
}