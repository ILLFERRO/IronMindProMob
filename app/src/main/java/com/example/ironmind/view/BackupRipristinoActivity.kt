package com.example.ironmind.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.ironmind.R
import com.example.ironmind.viewmodel.BackupRipristinoViewModel

class BackupRipristinoActivity : AppCompatActivity() {

    private lateinit var createBackupLauncher: ActivityResultLauncher<Intent>
    private lateinit var restoreBackupLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: BackupRipristinoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup_ripristino)

        viewModel = ViewModelProvider(this)[BackupRipristinoViewModel::class.java]

        val toolbarBackupRipristino = findViewById<Toolbar>(R.id.toolbar_backup_ripristino)
        setSupportActionBar(toolbarBackupRipristino)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Backup & Ripristino"
        }

        // --- Launcher per creazione backup
        createBackupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    salvaBackup(uri)
                }
            }
        }

        // --- Launcher per selezione file backup da ripristinare
        restoreBackupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    ripristinaBackup(uri)
                }
            }
        }

        val btnBackup = findViewById<Button>(R.id.BackupButton)
        btnBackup.setOnClickListener {
            val fileName = "ironmind_backup.json"
            val mimeType = "application/json"

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
                putExtra(Intent.EXTRA_TITLE, fileName)
            }

            createBackupLauncher.launch(intent)
        }

        val btnRipristina = findViewById<Button>(R.id.RipristinoButton)
        btnRipristina.setOnClickListener {
            val mimeType = "application/json"

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
            }

            restoreBackupLauncher.launch(intent)
        }

        toolbarBackupRipristino.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun salvaBackup(uri: Uri) {
        viewModel.salvaBackupSuUri(uri, contentResolver) { success ->
            runOnUiThread {
                Toast.makeText(
                    this,
                    if (success) "Backup completato!" else "Errore durante il salvataggio",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun ripristinaBackup(uri: Uri) {
        viewModel.ripristinaBackupDaUri(uri, contentResolver) { success ->
            runOnUiThread {
                Toast.makeText(
                    this,
                    if (success) "Ripristino completato!" else "Errore durante il ripristino",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}