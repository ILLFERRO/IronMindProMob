package com.example.ironmind.main

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import android.widget.Button
import org.json.JSONObject

class BackupRipristinoActivity : AppCompatActivity() {

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

        val btnBackup = findViewById<Button>(R.id.BackupButton)
        btnBackup.setOnClickListener{
            val fileName = "ironmind_backup.json"
            val mimeType = "application/json"

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
                putExtra(Intent.EXTRA_TITLE, fileName)
            }

            startActivityForResult(intent, CREATE_BACKUP_REQUEST_CODE)
        }

        toolbarBackupRipristino.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_BACKUP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val prefs = getSharedPreferences("IronMindPrefs", MODE_PRIVATE)
                val allPrefs = prefs.all
                val json = JSONObject(allPrefs).toString()

                contentResolver.openOutputStream(uri)?.use { output ->
                    output.write(json.toByteArray())
                }
            }
        }
    }
}