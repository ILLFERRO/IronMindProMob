package com.example.ironmind.main

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ironmind.R

class MostraNotificaActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_NOTIFICHE = 1001
        private const val CHANNEL_ID = "recupero_channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostra_notifica)

        val toolbarMostraNotifica = findViewById<Toolbar>(R.id.toolbar_mostra_notifica)
        setSupportActionBar(toolbarMostraNotifica)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Mostra Notifica"
        }

        toolbarMostraNotifica.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSi = findViewById<Button>(R.id.btn_mostra)
        val btnNo = findViewById<Button>(R.id.btn_non_mostra)
        val preferenzeNotifica = getSharedPreferences("settings", MODE_PRIVATE)

        btnSi.setOnClickListener {
            // Se serve, chiedi permesso
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_NOTIFICHE
                )
            } else {
                // Salva preferenza e crea canale subito
                preferenzeNotifica.edit().putBoolean("mostra_notifica", true).apply()
                creaCanaleNotifiche()
                onBackPressedDispatcher.onBackPressed()
            }
        }

        btnNo.setOnClickListener {
            preferenzeNotifica.edit().putBoolean("mostra_notifica", false).apply()
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Ricevi esito del permesso richiesto
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_NOTIFICHE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSharedPreferences("settings", MODE_PRIVATE)
                    .edit().putBoolean("mostra_notifica", true).apply()
                creaCanaleNotifiche()
            }
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Crea il canale per la notifica
    private fun creaCanaleNotifiche() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nome = "Canale Recupero"
            val descrizione = "Notifiche per il timer di recupero"
            val importanza = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, nome, importanza).apply {
                description = descrizione
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}