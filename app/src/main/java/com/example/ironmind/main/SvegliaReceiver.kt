package com.example.ironmind.notifiche

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.ironmind.main.PromemoriaActivity
import com.example.ironmind.R

class SvegliaReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val titolo = intent.getStringExtra("titolo") ?: "Promemoria"
        val messaggio = intent.getStringExtra("messaggio") ?: "È il momento del tuo allenamento!"

        val openIntent = Intent(context.applicationContext, PromemoriaActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val canaleId = "promemoria_channel"
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, canaleId)
            .setSmallIcon(R.drawable.icona_notifica)
            .setContentTitle(titolo)
            .setContentText(messaggio)
            .setAutoCancel(true)
            .setSound(sound)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                canaleId,
                "Promemoria Allenamenti",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}