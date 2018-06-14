package petpet.petpet

import android.app.IntentService
import android.content.Intent
import android.util.Log

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
    }
}
