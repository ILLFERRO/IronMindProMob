package com.example.ironmind.Utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.ironmind.Activity.PromemoriaActivity
import com.example.ironmind.R

class AllarmeRicevitore : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val nomePromemoria = intent.getStringExtra("nome") ?: "Promemoria"
        Toast.makeText(context, "Promemoria ricevuto!", Toast.LENGTH_SHORT).show()
        Log.d("AllarmeRicevitore", "Ricevuto intent per promemoria")

        val openIntent = Intent(context.applicationContext, PromemoriaActivity::class.java)
        openIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "promemoria_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Promemoria",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canale notifiche promemoria"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icona_notifica)
            .setContentTitle("Promemoria")
            .setContentText("È ora di: $nomePromemoria")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}