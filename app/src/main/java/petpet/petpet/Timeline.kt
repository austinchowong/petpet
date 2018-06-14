package petpet.petpet
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Context
import android.content.Intent


/**
 * Created by user on 2018-06-06.
 */
class Timeline {
    //time of when the timeline was created
    @SerializedName("startDate")
    public var startDate: Date? = null;
    @SerializedName("timeline")
    public var timeline = ArrayList<PetEvent>();

    fun initTimeline(context:Context)
    {
        if(startDate == null)
        {
            //first time this timeline is being initialized
            //does not have a starting date yet
            this.startDate = GregorianCalendar.getInstance().time
            scheduleAlarm(context)
        }
    }
    
    fun endTimeline(context:Context)
    {
        cancelAlarm(context)
    }

    // Setup a recurring alarm
    //can do when first setting up timeline
    fun scheduleAlarm(context: Context) {
        // Construct an intent that will execute the AlarmReceiver
        val intent = Intent(context.getApplicationContext(), EventAlarmReceiver::class.java)
        // Create a PendingIntent to be triggered when the alarm goes off
        val pIntent = PendingIntent.getBroadcast(context, EventAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Setup periodic alarm every every half hour from this point onwards
        val firstMillis = System.currentTimeMillis() // alarm is set right away
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent)
    }

    //cancels the alarm
    //do when timeline is complete or stopped midway
    fun cancelAlarm(context: Context) {
        val intent = Intent(context.getApplicationContext(), EventAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(context, EventAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pIntent)
    }
}