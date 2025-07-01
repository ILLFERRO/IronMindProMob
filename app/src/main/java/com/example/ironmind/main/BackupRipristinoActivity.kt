package com.example.ironmind.main

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.json.JSONObject
import android.widget.Toast

class BackupRipristinoActivity : AppCompatActivity() {

    private lateinit var createBackupLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val CREATE_BACKUP_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup_ripristino)

        val toolbarBackupRipristino = findViewById<Toolbar>(R.id.toolbar_backup_ripristino)
        setSupportActionBar(toolbarBackupRipristino)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Backup & Ripristino"
        }

        //inizializzo il launcher
        createBackupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> //registro il launcher con contratto StartActivityForResult che permette di lanciare un activity e ottenre il risultato
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    // Chiama la tua funzione di salvataggio, tipo:
                    salvaBackupSuUri(uri)
                }
            }
        }

        val btnBackup = findViewById<Button>(R.id.BackupButton)
        btnBackup.setOnClickListener {
            val fileName = "ironmind_backup.json" //definisco nome del file
            val mimeType = "application/json" //definisco il tipo MIME del file da creare

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply { //intent dove chiedo di creare un nuovo documento, lo apro nel file picker di Android e lo apro con il nome di prima
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
                putExtra(Intent.EXTRA_TITLE, fileName)
            }

            createBackupLauncher.launch(intent)
        }

        toolbarBackupRipristino.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun salvaBackupSuUri(uri: Uri) {
        val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
        val allPrefs = prefs.all //prendo tutte le coppie chiave-valore salvate nelle SharedPreferences
        val json = JSONObject(allPrefs).toString() //converte tutte queste coppie in una stringa JSON

        contentResolver.openOutputStream(uri)?.use { output -> //apre un flusso di scrittura sul file indicato dall'URI, use è un blocco che mi garantisce che alla fine il flusso si chiude automaticamente
            output.write(json.toByteArray()) //se uri è valido apre stream di output sul file selezionato dall'utente, scrivo la stringa JSON nel file, la stringa viene trasformata in un array di byte e scritta nel file
            output.flush() //faccio flush per assicurarmi che i dati vengano scritti
            Toast.makeText(this, "Backup completato!", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Errore durante il salvataggio", Toast.LENGTH_SHORT).show()
        }
    }
}