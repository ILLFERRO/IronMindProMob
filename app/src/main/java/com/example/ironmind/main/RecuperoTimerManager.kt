package com.example.ironmind.main

import android.app.*
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.ironmind.R

object RecuperoTimerManager {

    private const val CHANNEL_ID = "recupero_timer_channel"
    private const val NOTIFICATION_ID = 2025

    fun avviaTimer(
        context: Context,
        durataSecondi: Int,
        onTick: ((tempoRimanente: Int) -> Unit)? = null, //parametro opzionale, accetta un intero e non restituisce niente, eseguito ogni secondo per aggiornare il tempo rimanente
        onFinish: (() -> Unit)? = null //eseguita al termine quando finisce il timer
    ) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val mostraNotifica = prefs.getBoolean("mostra_notifica", false)
        val vibrazioneAttiva = prefs.getBoolean("vibrazione_attiva", false)
        val suoneriaAttiva = prefs.getBoolean("suoneria_attiva", false)

        if (mostraNotifica) {
            creaCanaleNotifiche(context)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Recupero in corso")
                .setContentText("Tempo restante: ${formatTime(durataSecondi)}")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, builder.build())

            object : CountDownTimer(durataSecondi * 1000L, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondiRestanti = (millisUntilFinished / 1000).toInt()
                    builder.setContentText("Tempo restante: ${formatTime(secondiRestanti)}")
                    notificationManager.notify(NOTIFICATION_ID, builder.build())

                    // Chiamata al callback se non nullo
                    onTick?.invoke(secondiRestanti)
                }

                override fun onFinish() {
                    notificationManager.cancel(NOTIFICATION_ID)

                    if (vibrazioneAttiva) {
                        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vib.vibrate(500)
                        }
                    }

                    if (suoneriaAttiva) {
                        val suono = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val ringtone = RingtoneManager.getRingtone(context, suono)
                        ringtone.play()
                    }

                    // Chiamata al callback se non nullo
                    onFinish?.invoke()
                }
            }.start()

        } else {
            // Se non deve mostrare notifica, facciamo solo il timer interno senza notifiche o vibrazione
            object : CountDownTimer(durataSecondi * 1000L, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondiRestanti = (millisUntilFinished / 1000).toInt()
                    onTick?.invoke(secondiRestanti)
                }

                override fun onFinish() {
                    if (vibrazioneAttiva) {
                        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vib.vibrate(500)
                        }
                    }
                    if (suoneriaAttiva) {
                        val suono = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val ringtone = RingtoneManager.getRingtone(context, suono)
                        ringtone.play()
                    }
                    onFinish?.invoke()
                }
            }.start()
        }
    }

    private fun creaCanaleNotifiche(context: Context) { //crea un canale di notifiche per poter inviare notifiche
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //solo da Android 8.0 in poi
            val channel = NotificationChannel(
                CHANNEL_ID, //con il suo id canale
                "Timer di Recupero", //nome "Timer di recupero"
                NotificationManager.IMPORTANCE_LOW //priorità bassa, vibrazione
            )
            channel.description = "Notifiche per il timer del tempo di recupero" //descrizione del canale
            val manager = context.getSystemService(NotificationManager::class.java) //ottengo servizio di sistema che gestisce le notifiche
            manager.createNotificationChannel(channel) //registra il canale nel sistema
        }
    }

    private fun formatTime(secondi: Int): String { //formatta il numero di secondi in tipo min:sec e lo converte in stringa
        val min = secondi / 60 //calcola i minuti interi
        val sec = secondi % 60 //calcola i secondi rimanenti tramite modulo
        return "%02d:%02d".format(min, sec) //ritorna una stringa con due cifre sia per minuto che per secondo
    }
}
