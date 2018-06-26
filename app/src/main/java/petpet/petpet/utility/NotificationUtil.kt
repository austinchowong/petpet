package petpet.petpet.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import petpet.petpet.Home
import petpet.petpet.R

class NotificationUtil {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(R.string.PET_UPDATE_CHANNEL_ID.toString(),
                    "PetUpdates", NotificationManager.IMPORTANCE_HIGH)

            //Submit the notification channel object to notification manager
            val notiMgr: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notiMgr.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context, contentTitle: CharSequence, contentText: CharSequence) {
        val intent = PendingIntent.getActivity(context, 0,
                Intent(context, Home::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, R.string.PET_UPDATE_CHANNEL_ID.toString())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(intent)
                .setAutoCancel(true)
//                .setStyle(NotificationCompat.BigTextStyle()
//                        .bigText(R.string.notification_message.toString()))  //allow display test that cannot fit one line

        NotificationManagerCompat.from(context).notify(0, notification.build())
    }

}