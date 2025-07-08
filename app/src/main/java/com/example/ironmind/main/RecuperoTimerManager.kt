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
import android.os.VibratorManager

object RecuperoTimerManager {

    private const val CHANNEL_ID = "recupero_timer_channel"
    private const val NOTIFICATION_ID = 2025

    fun avviaTimer(
        context: Context,
        durataSecondi: Int,
        onTick: ((tempoRimanente: Int) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val mostraNotifica = prefs.getBoolean("mostra_notifica", false)
        val vibrazioneAttiva = prefs.getBoolean("vibrazione_attiva", false)
        val suoneriaAttiva = prefs.getBoolean("suoneria_attiva", false)

        if (mostraNotifica) {
            creaCanaleNotifiche(context)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Recupero: ")
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

                    onTick?.invoke(secondiRestanti)
                }

                override fun onFinish() {
                    notificationManager.cancel(NOTIFICATION_ID)

                    if (vibrazioneAttiva) {
                        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                            vibratorManager.defaultVibrator
                        } else {
                            @Suppress("DEPRECATION")
                            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(500)
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

        } else {
            object : CountDownTimer(durataSecondi * 1000L, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondiRestanti = (millisUntilFinished / 1000).toInt()
                    onTick?.invoke(secondiRestanti)
                }

                override fun onFinish() {
                    if (vibrazioneAttiva) {
                        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                            vibratorManager.defaultVibrator
                        } else {
                            @Suppress("DEPRECATION")
                            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        }

                        val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                        } else {
                            @Suppress("DEPRECATION")
                            null
                        }

                        if (vibrationEffect != null) {
                            vibrator.vibrate(vibrationEffect)
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(500)
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

    private fun creaCanaleNotifiche(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Timer di Recupero",
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = "Notifiche per il timer del tempo di recupero"
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun formatTime(secondi: Int): String {
        val min = secondi / 60
        val sec = secondi % 60
        return "%02d:%02d".format(min, sec)
    }
}
