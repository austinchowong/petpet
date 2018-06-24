package petpet.petpet.updatePet

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import petpet.petpet.R
import android.app.PendingIntent
import android.content.Intent
import petpet.petpet.Home


class UpdatePetTask {
    fun updatePet(context: Context) {
        //TODO:: perform actions for update the pet


        //send out a notification
        sendNotification(context)

    }

    private fun sendNotification(context: Context) {

        val intent = PendingIntent.getActivity(context, 0,
                Intent(context, Home::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, R.string.PET_UPDATE_CHANNEL_ID.toString())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("petpet")
                .setContentText("given some thing here")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(intent)
                .setAutoCancel(true)
//                .setStyle(NotificationCompat.BigTextStyle()
//                        .bigText(R.string.notification_message.toString()))  //allow display test that cannot fit one line

        NotificationManagerCompat.from(context).notify(0, notification.build())
    }

}

