package com.example.ironmind

import com.example.ironmind.auth.DashBoardActivity //importa schermata DashBoardActivity
import com.example.ironmind.main.RegisterActivity // importa schermata RegisterActivity
import android.content.Intent //naviga da un attività ad un altra
import android.os.Bundle //Necessario per gestire i dati ricevuti all’apertura dell’attività
import android.widget.* //importa tutti i componenti dell'interfaccia utente di base
import androidx.appcompat.app.AlertDialog //mi aiuta a creare il popup per il recupero password
import androidx.appcompat.app.AppCompatActivity //AppCompatActivity classe base da cui estendo tutte le schermate
import com.google.firebase.auth.FirebaseAuth //gestisce l'autenticazione utente: login

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth //riferimento all'oggetto firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Definisco le TextView
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvRegisterLink = findViewById<TextView>(R.id.tvRegisterLink)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        auth = FirebaseAuth.getInstance() //inizializza una istanza di firebase

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim() //inserisco email, .text accede al contenuto scritto dall'utente, .toString converte quel contenuto in una stringa mentre .trim rimuove eventuali spazi prima/dopo
            val password = etPassword.text.toString().trim() //inserisco password e fa la stessa cosa

            //Controlla se uno dei due campi è vuoto
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener //ritorna la funzione di setOnClickListener se mancano così da non uscire dall'onCreate, quindi mi impedisce il login
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { //si tenta di fare login con le credenziali fornite
                if (it.isSuccessful) {
                    startActivity(Intent(this, DashBoardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login fallito: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvRegisterLink.setOnClickListener { //attraverso questa TextView faccio la registrazione se ancora non ho un account
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordText.setOnClickListener {
            showPasswordResetDialog()
        }
        //qui recupero la password attraverso un popup dove mostra l'inserimento dei dati con il metodo showPasswordResetDialog()
    }

    private fun showPasswordResetDialog() {
        //qui creo un AlertDialog con il titolo di "Recupera password"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recupera password")

        val input = EditText(this) //creo dinamicamente un campo di testo (EditText) all'interno del popup
        input.hint = "Inserisci la tua email" //testo di guida
        input.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS //tipo di input:email
        builder.setView(input) //definisco il campo con quell'input
        //creo il campo email da inserire

        builder.setPositiveButton("Invia") { _, _ -> //qui premo il bottone Invia
            val email = input.text.toString().trim()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email) //invia una email di reset
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email di recupero inviata", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Errore: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Inserisci un'email valida", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Annulla", null) //se clicco annulla il dialogo si chiude e non succede niente
        builder.show() //visualizza il dialog sullo schermo
    }
}