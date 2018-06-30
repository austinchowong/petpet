package petpet.petpet.timeline

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import petpet.petpet.utility.NotificationUtil
import java.io.*

class TimelineEventService : IntentService("AlarmService") {

    override fun onHandleIntent(p0: Intent?) {

        //val notif = NotificationUtil()
        //notif.sendNotification(this, "petpet testing", "timelineevent service")

        //check to see if any events in the timeline are starting, if so, send a notification
        //for active events, send a notification
        //for events that have ended, send a notification, and set the event information
        val petpreference = PetPreference(this)
        val f = File(filesDir.path + "/" +petpreference.prefTimelineFileName)
        if(f.exists())
        {
            Log.d("petTimelineEventService", "timeline file found")
            val reader = BufferedReader(FileReader(f))
            val gson = Gson()
            val timeline: Timeline = gson.fromJson(reader, object : TypeToken<Timeline>() {}.type)
            timeline.CheckTimeline(this)

            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
            val jsonString = gson.toJson(timeline);
            writer.write(jsonString);
            writer.close();
        }
        else
        {
            Log.d("petTimelineEventService", "no timeline file found")
        }

        Log.d("petAlarm service update", "current pet values: ")
        Log.d("pethappiness", petpreference.getPetHappiness().toString())
        Log.d("pethealth", petpreference.getPetHealth().toString())
        Log.d("pethunger", petpreference.getPetHunger().toString())
    }
}
