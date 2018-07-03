package petpet.petpet

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.app.Service.START_NOT_STICKY
import android.content.Context
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import petpet.petpet.stepcounter.Pedometer
import petpet.petpet.timeline.TimelineEventService


/**
 * Created by user on 2018-07-02.
 */
class TaskRemovedService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        //check if currently tracking information
        if(Pedometer.isWalking)
        {
            Log.d("taskremoved", "stop tracking pedometer")
            Pedometer.StopTracking(this)
        }

        //reschedule alarm service
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, TimelineEventService::class.java)
        val alarmIntent = PendingIntent.getService(this, 1, intent, 0)

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(), 1000 * 30,  alarmIntent)

        stopSelf()
    }
}
