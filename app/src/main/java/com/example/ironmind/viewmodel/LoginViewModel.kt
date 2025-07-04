package com.example.ironmind.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Email e password non possono essere vuoti"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.value = true
                } else {
                    _errorMessage.value = "Login fallito: ${task.exception?.message}"
                }
            }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _errorMessage.value = "Inserisci un'email per il recupero"
            return
        }
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _errorMessage.value = "Email di recupero inviata con successo"
            }
            .addOnFailureListener {
                _errorMessage.value = "Errore durante l'invio: ${it.message}"
            }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}