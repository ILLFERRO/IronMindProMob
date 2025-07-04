package com.example.ironmind

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ironmind.auth.DashBoardActivity
import com.example.ironmind.auth.LoginViewModel
import com.example.ironmind.main.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvRegisterLink = findViewById<TextView>(R.id.tvRegisterLink)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            viewModel.login(email, password)
        }

        tvRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordText.setOnClickListener {
            showPasswordResetDialog()
        }

        // Osserva gli stati dal ViewModel
        viewModel.loginResult.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, DashBoardActivity::class.java))
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
    }

    private fun showPasswordResetDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recupera password")

        val input = EditText(this)
        input.hint = "Inserisci la tua email"
        input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        builder.setView(input)

        builder.setPositiveButton("Invia") { _, _ ->
            val email = input.text.toString().trim()
            viewModel.resetPassword(email)
        }

        builder.setNegativeButton("Annulla", null)
        builder.show()
    }
}