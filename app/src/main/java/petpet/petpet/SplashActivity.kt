package petpet.petpet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import petpet.petpet.pet.PetPreference
import petpet.petpet.timeline.TimelineEventService
import petpet.petpet.utility.NotificationUtil
import kotlin.concurrent.thread
import android.app.ActivityManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //set notification channel
        NotificationUtil().createNotificationChannel(this)

        //setup onTaskRemovedService
        startService(Intent(baseContext, TaskRemovedService::class.java))
        if(!isServiceRunning(TimelineEventService::class.java)) {
            scheduleEventAlarm()
        }
    }

    override fun onStart() {
        super.onStart()
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

    fun scheduleEventAlarm()
    {
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, TimelineEventService::class.java)
        val alarmIntent = PendingIntent.getService(this, 1, intent, 0)

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(), 1000 * 30,  alarmIntent)
    }

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}