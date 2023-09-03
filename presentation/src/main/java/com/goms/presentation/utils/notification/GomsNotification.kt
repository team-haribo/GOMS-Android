package com.goms.presentation.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.goms.presentation.R
import com.goms.presentation.view.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class GomsNotification: FirebaseMessagingService() {
    companion object {
        private const val CHANNEL_NAME = "GOMS"
        private const val CHANNEL_DESCRIPTION = "GOMS 알림"
        private const val CHANNEL_ID = "goms_channel_id"
        private const val GROUP_NAME = "com.goms.presentation"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("TAG", "onMessageReceived: called")
        createNotificationChannel()
        sendNotification(message.notification?.title, message.notification?.body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken new token: $token")

        val deviceTokenSF = getSharedPreferences("deviceToken", MODE_PRIVATE)
        deviceTokenSF.edit().putString("device", token).apply()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = CHANNEL_DESCRIPTION

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }

    private fun sendNotification(title: String?, body: String?) {
        val messageId = System.currentTimeMillis().toInt()
        val intent = Intent(this, SplashActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, messageId, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setGroup(GROUP_NAME)
            .setGroupSummary(true)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messageId, notificationBuilder.build())
    }
}