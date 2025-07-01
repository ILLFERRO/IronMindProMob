package com.example.ironmind.main

import android.app.*
import android.content.*
import android.os.Build
import android.widget.Toast
import java.util.*

object AlarmAvviso {
    fun impostaSveglia(context: Context, promemoria: Promemoria) { //programmo una sveglia/allarme per una determinata ora nell'oggetto Promemoria
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager //serve per accedere ai sistemi di allarme Android
        val intent = Intent(context, AllarmeRicevitore::class.java)
        intent.putExtra("nome", promemoria.nome)

        val pendingIntent = PendingIntent.getBroadcast( //il pending intent è un tipo di intent che si esegue in futuro e verrà ricevuto da un getBroadCast
            context, promemoria.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE //ogni promemoria ha un id: promemoria.id. FLAG_UPDATE_CURRENT: se esiste già, lo aggiorna., FLAG_IMMUTABLE, richiesto da Android 12
        )

        val parts = promemoria.ora.split(":") //creo oggetto calendar per impostare il promemoria (orario della sveglia), promemoria.ora è una stringa, se l'orario è già passato, si aggiunge un giorno così suona il giorno dopo alla stessa ora
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            set(Calendar.MINUTE, parts[1].toInt())
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DAY_OF_MONTH, 1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) { //canScheduleExactAlarms() verifica se l’utente ha concesso il permesso per sveglie esatte.
                alarmManager.setExactAndAllowWhileIdle( //setExactAndAllowWhileIdle(...) → imposta una sveglia esatta anche se il telefono è in modalità doze/idle (batteria ottimizzata).
                    AlarmManager.RTC_WAKEUP, //sveglia attiva quando il telefono segna l'orario
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                Toast.makeText(context, "Permesso per allarmi esatti non concesso", Toast.LENGTH_LONG).show()
                // Qui puoi eventualmente mandare l'utente alle impostazioni
            }
        } else {
            // Per Android < 12 non serve permesso esplicito
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancellaSveglia(context: Context, promemoria: Promemoria) {
        val intent = Intent(context, AllarmeRicevitore::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, promemoria.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}