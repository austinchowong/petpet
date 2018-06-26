package petpet.petpet

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by user on 2018-06-11.
 */

class TimerEventService : IntentService("TimerEventService") {

    override fun onHandleIntent(intent: Intent) {
        // Do the task here
        Log.i("TimerEventService", "Service running")
        //check to see if any events in the timeline are starting, if so, send a notification
        //for active events, send a notification
        //for events that have ended, send a notification, and set the event information
        val petpreference = PetPreference(this)
        val reader = BufferedReader(InputStreamReader(this.assets.open(petpreference.prefTimelineFileName)))
        val gson = Gson()
        val timeline : Timeline = gson.fromJson(reader ,object : TypeToken<Timeline>() {}.type )
        timeline.CheckTimeline(this)
    }
}
