package petpet.petpet.timeline
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.annotations.SerializedName
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log


/**
 * Created by user on 2018-06-06.
 */
class Timeline {
    //time of when the timeline was created
    @SerializedName("startDate")
    public var startDate: Long = 0;

    //current and future events
    @SerializedName("timeline")
    public var timeline = ArrayList<TimelineDay>();

    //length of timeline
    @SerializedName("duration")
    public var duration : Int = 0;

    fun CheckTimeline(context: Context) {
        //only check current day's events/info
        //timeline will only contain walking and vaccination events
        val today = GetCurrentDay()
        if(today >= timeline.size) return

        for(event in timeline[today].dayEvents)
        {
            event.CheckStatus(context, startDate, timeline[today])
        }
    }
    //return the current day relative to the startDate
    fun GetCurrentDay() : Int
    {
        val start = Date(startDate)
        val now = GregorianCalendar.getInstance().time
        var today = now.date - start.day

        //set to last day if out of range
        if(today >= timeline.size)
            today = timeline.size - 1
        return today
    }

    fun GetCurrentTimelineDay(): TimelineDay
    {
        return timeline[GetCurrentDay()]
    }

    fun GetStepsTakenToday() : Long
    {
        return timeline[GetCurrentDay()].stepsTaken
    }

    fun UpdateEventTrackerToday(eventname : String, status : String)
    {
        timeline[GetCurrentDay()].eventTracker[eventname] = status
    }

    fun initTimeline(context:Context)
    {
        if(startDate == 0L)
        {
            //first time this timeline is being initialized
            //does not have a starting date yet
            this.startDate = GregorianCalendar.getInstance().time.getTime();
            //scheduleAlarm(context)
            //schedule alarm in createpetactivity instead, not sure why it works there but not here
        }
    }

    fun endTimeline(context:Context)
    {
        cancelAlarm(context)
    }

    // Setup a recurring alarm
    //can do when first setting up timeline
    fun scheduleAlarm(context: Context) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context.applicationContext, TimelineEventService::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        // set the alarm to start at 22:00
        val calendar : Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 22)

        //repeat alarm every 2 mins
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 1000 * 60 * 2,  alarmIntent)

        Log.d("timeline", "alarm started")
    }

    //cancels the alarm
    //do when timeline is complete or stopped midway
    fun cancelAlarm(context: Context) {
        val intent = Intent(context.getApplicationContext(), TimelineEventService::class.java)
        val pIntent = PendingIntent.getBroadcast(context, 0,
                intent, 0)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pIntent)
    }
}