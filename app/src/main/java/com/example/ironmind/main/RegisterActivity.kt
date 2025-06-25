package com.example.ironmind.main

import com.example.ironmind.LoginActivity //importo la schermata LoginActivity
import android.content.Intent //permette di navigare da una schermata all'altra
import android.os.Bundle //Bunlde usato per salvare/stabilire lo stato dell'avvio dell'Activity
import android.widget.Button //importa classe Button
import android.widget.EditText //importa classe EditText (campo input testuale)
import android.widget.Toast //importa classe Toast (mostrare popup e messagi brevi)
import androidx.appcompat.app.AppCompatActivity //AppCompatActivity classe base da cui estendo tutte le schermate
import com.example.ironmind.R //classe generata da Android automaticamente, funge da mappa di tutte le risorse del progetto
import com.google.firebase.auth.FirebaseAuth //gestisce l'autenticazione utente: login

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.buttonRegister)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { //tento di registrare l'utente, attraverso il metodo createUserWithEmailAndPassword con le credenziali fornite (email & password), restituisce oggetto Task<AuthResult>
                // il metodo addOnCompleteListener imposta un listener che si attiva quando la registrazione è completata
                if (it.isSuccessful) { //it è il risultato (Task<AuthResult>)
                    Toast.makeText(this, "Registrazione riuscita", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Registrazione fallita: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}