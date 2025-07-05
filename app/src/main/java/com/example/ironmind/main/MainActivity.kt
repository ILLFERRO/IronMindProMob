//Schermata Principale, dove compare il titolo per 2 secondi
package com.example.ironmind.main //è scritto com.example.ironmind.main perchè main è un package (sotto cartella)

import android.content.Intent //Intent serve per passare da una schermata all'altra (da una Activity ad un'altra)
import android.os.Bundle //Bunlde usato per salvare/stabilire lo stato dell'avvio dell'Activity
import android.os.Handler // Usati per ritardare l'esecuzione di codice
import android.os.Looper // Usati per ritardare l'esecuzione di codice
import androidx.appcompat.app.AppCompatActivity // AppCompatActivity è la classe base per attività compatibili con il tema e le funzionalità moderne Android
import com.example.ironmind.auth.DashBoardActivity
import com.example.ironmind.R

class MainActivity : AppCompatActivity() { //Dichiarazione classe MainActivity, estende AppCompatActivity: le Activity ereditano dalla classe androidx.appcompat.app.AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate è il metodo chiamato quando viene creata l'Activity, quindi quando viene inizializzata, eseguita quando il runtime effettua la creazione
        super.onCreate(savedInstanceState) //chiamo il metodo omonimo della classe
        setContentView(R.layout.activity_main) //carica il layout associato, con il layout infltion, cioè specifico quale file utilizzo per il layout della UI tramite metodo setContentView

        Handler(Looper.getMainLooper()).postDelayed({ //Looper.getMainLooper si assicura che il codice venga eseguito nel thread principale
            //postDelayed ritarda l'esecuzione del blocco di 2 secondi, come vediamo sotto con il delayMillis che mi restituisce il delay in millisecondi
            startActivity(Intent(this, DashBoardActivity::class.java)) //startActivity mi avvia la DashBoard
            finish() //chiude la MainActivity per non tornare indietro con il tasto back
        }, 2000) // 2 secondi compare il titolo "Iron Mind"
    }
}