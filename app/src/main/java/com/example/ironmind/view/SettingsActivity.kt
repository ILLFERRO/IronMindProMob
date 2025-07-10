package com.example.ironmind.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import com.example.ironmind.Activity.MostraNotificaActivity
import com.example.ironmind.Repository.PremiRepository
import com.example.ironmind.Activity.ProfiloEtaDataDiNascitaActivity
import com.example.ironmind.Activity.ProfiloNomeCognomeActivity
import com.example.ironmind.Activity.ProfiloPesoAltezzaActivity
import com.example.ironmind.Activity.ProfiloQrCodeActivity
import com.example.ironmind.Activity.ProfiloSessoActivity
import com.example.ironmind.Activity.PromemoriaActivity
import com.example.ironmind.Activity.SetDefaultActivity
import com.example.ironmind.Utils.SuonaAlTermineActivity
import com.example.ironmind.Activity.TempoDiRiposoActivity
import com.example.ironmind.Utils.VibraAlTermineActivity
import com.example.ironmind.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var startTrainingDayTextView: TextView
    private lateinit var weightIncrementTextView: TextView
    private lateinit var ShowNotificationTextView: TextView
    private lateinit var VibrateOnFinishTextView: TextView
    private lateinit var SoundOnFinishTextView: TextView
    private lateinit var DefaultSetsTextView: TextView
    private lateinit var DefaultTempoDiRiposoTextView: TextView

    private lateinit var startTrainingDayLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Impostazioni"
        }

        startTrainingDayTextView = findViewById(R.id.weeklyGoal)
        weightIncrementTextView = findViewById(R.id.weightIncrement)
        ShowNotificationTextView = findViewById(R.id.showNotification)
        VibrateOnFinishTextView = findViewById(R.id.vibrateOnFinish)
        SoundOnFinishTextView = findViewById(R.id.soundOnFinish)
        DefaultSetsTextView = findViewById(R.id.defaultSets)
        DefaultTempoDiRiposoTextView = findViewById(R.id.defaultRestTime)

        findViewById<TextView>(R.id.nome_cognome).setOnClickListener {
            startActivity(Intent(this, ProfiloNomeCognomeActivity::class.java))
        }

        findViewById<TextView>(R.id.eta_data_di_nascita).setOnClickListener {
            startActivity(Intent(this, ProfiloEtaDataDiNascitaActivity::class.java))
        }

        findViewById<TextView>(R.id.sesso_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloSessoActivity::class.java))
        }

        findViewById<TextView>(R.id.peso_altezza_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloPesoAltezzaActivity::class.java))
        }

        findViewById<TextView>(R.id.qr_code_profilo).setOnClickListener {
            startActivity(Intent(this, ProfiloQrCodeActivity::class.java))
        }

        findViewById<TextView>(R.id.backup_ripristino).setOnClickListener {
            startActivity(Intent(this, BackupRipristinoActivity::class.java))
        }

        startTrainingDayLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.caricaImpostazioni()
            }
        }

        startTrainingDayTextView.setOnClickListener {
            val intentTraining = Intent(this, ObiettiviActivity::class.java)
            startTrainingDayLauncher.launch(intentTraining)
        }

        findViewById<TextView>(R.id.reminders).setOnClickListener {
            startActivity(Intent(this, PromemoriaActivity::class.java))
        }

        weightIncrementTextView.setOnClickListener {
            startActivity(Intent(this, IncrementoPesoActivity::class.java))
        }

        ShowNotificationTextView.setOnClickListener {
            startActivity(Intent(this, MostraNotificaActivity::class.java))
        }

        VibrateOnFinishTextView.setOnClickListener {
            startActivity(Intent(this, VibraAlTermineActivity::class.java))
        }

        SoundOnFinishTextView.setOnClickListener {
            startActivity(Intent(this, SuonaAlTermineActivity::class.java))
        }

        DefaultSetsTextView.setOnClickListener {
            startActivity(Intent(this, SetDefaultActivity::class.java))
        }

        DefaultTempoDiRiposoTextView.setOnClickListener {
            startActivity(Intent(this, TempoDiRiposoActivity::class.java))
        }

        findViewById<Button>(R.id.btn_reset_allenamenti).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Conferma azzeramento")
                .setMessage("Sei sicuro di voler azzerare il conteggio degli allenamenti completati?")
                .setPositiveButton("Sì") { _, _ -> viewModel.resetAllenamentiCompletati() }
                .setNegativeButton("Annulla", null)
                .show()
        }

        findViewById<Button>(R.id.btn_reset_statistiche).setOnClickListener {
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

        findViewById<Button>(R.id.btn_reset_premi).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Conferma azzeramento premi")
                .setMessage("Sei sicuro di voler eliminare tutti i premi sbloccati?")
                .setPositiveButton("Sì") { _, _ ->
                    val prefsPremi = getSharedPreferences("premi_sbloccati", MODE_PRIVATE)
                    prefsPremi.edit().clear().apply()
                    PremiRepository.listaPremi.forEach { it.sbloccato = false }
                    Toast.makeText(this, "Premi resettati", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Annulla", null)
                .show()
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.startTrainingGoal.observe(this) {
            startTrainingDayTextView.text = "Obiettivo settimanale: $it"
        }

        viewModel.pesoIncremento.observe(this) {
            weightIncrementTextView.text = "Incremento di peso: $it kg"
        }

        viewModel.mostraNotifica.observe(this) {
            val stato = if (it) "attivo" else "disattivato"
            ShowNotificationTextView.text = "Mostra Notifica: $stato"
        }

        viewModel.vibrazioneAttiva.observe(this) {
            val stato = if (it) "attivo" else "disattivato"
            VibrateOnFinishTextView.text = "Vibra Al Termine: $stato"
        }

        viewModel.suoneriaAttiva.observe(this) {
            val stato = if (it) "attivo" else "disattivato"
            SoundOnFinishTextView.text = "Suona Al Termine: $stato"
        }

        viewModel.setDefault.observe(this) {
            DefaultSetsTextView.text = "Set Di Default: $it"
        }

        viewModel.tempoRiposo.observe(this) { (minuti, secondi) ->
            DefaultTempoDiRiposoTextView.text = "Tempo di riposo: ${minuti}:${String.format("%02d", secondi)}"
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.caricaImpostazioni()
    }
}