package petpet.petpet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by user on 2018-06-11.
 */

class EventAlarmReceiver : BroadcastReceiver() {

    // Triggered by the Alarm periodically (starts the service to run task)
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, TimerEventService::class.java)
        //additional information? maybe which timeline to look at
        //put extra the information that the service can use to retrieve the timeline that it needs
        //to look at for events
        //i.putExtra("foo", "bar");
        context.startService(i)
    }

    companion object {
        val REQUEST_CODE = 12345
    }
}
