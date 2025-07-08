package com.example.ironmind.Activity

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ironmind.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class ProfiloQrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilo_qr_code)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_qr_code)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val btnGenera = findViewById<Button>(R.id.btnGeneraQr)
        val imageQr = findViewById<ImageView>(R.id.imageQrCode)

        btnGenera.setOnClickListener {
            val sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE)
            val nome = sharedPrefs.getString("nome_utente", "Nome")
            val cognome = sharedPrefs.getString("cognome_utente", "Cognome")
            val contenutoQr = "$nome $cognome - IronMind Accesso"

            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.encodeBitmap(contenutoQr, BarcodeFormat.QR_CODE, 400, 400)
            imageQr.setImageBitmap(bitmap)
        }
    }
}
