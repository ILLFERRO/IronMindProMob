package com.example.ironmind.main

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
            // Puoi usare anche un ID utente, email o token unico se vuoi
            val sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE)
            val nome = sharedPrefs.getString("nome_utente", "Nome")
            val cognome = sharedPrefs.getString("cognome_utente", "Cognome")
            val contenutoQr = "$nome $cognome - IronMind Accesso"

            val encoder = BarcodeEncoder() //creo oggetto di tipo BarcodeEncoder da libreria zxing per generare QrCode
            val bitmap: Bitmap = encoder.encodeBitmap(contenutoQr, BarcodeFormat.QR_CODE, 400, 400) //genera immagine bitmap di tipo QrCode con il contenuto che gli ho dato, formato QrCode e quelle dimensioni
            imageQr.setImageBitmap(bitmap) //setto poi l'immagine a quella bitmap
        }
    }
}
