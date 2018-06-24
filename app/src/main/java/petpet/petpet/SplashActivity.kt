package petpet.petpet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import petpet.petpet.pet.PetPreference
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        createNotificationChannel()

        thread(start = true) {
            Thread.sleep(1000)

            if (!PetPreference(this).hasPet()) {
                startActivity(Intent(this, CreatePetActivity::class.java))
            } else {
                startActivity(Intent(this, Home::class.java))
            }
            finish()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create the notification channel with unique id PET_UPDATE_CHANNEL_ID
            val channel = NotificationChannel(R.string.PET_UPDATE_CHANNEL_ID.toString(),
                    "PetUpdates", NotificationManager.IMPORTANCE_HIGH)

            //Submit the notification channel object to notification manager
            val notiMgr: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notiMgr.createNotificationChannel(channel)
        }
    }
}