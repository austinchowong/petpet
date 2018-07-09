package petpet.petpet.timeline

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.R
import petpet.petpet.pet.PetPreference
import petpet.petpet.utility.NotificationUtil
import java.io.*

class TimelineEventService : IntentService("AlarmService") {

    override fun onHandleIntent(p0: Intent?) {
        val petpreference = PetPreference(this)

        //check timeline events to see if any changes occurred
        val f = File(filesDir.path + "/" +petpreference.prefTimelineFileName)
        if(f.exists())
        {
            //reduce pet status over time
            if(petpreference.getPetHunger() < this.resources.getInteger(R.integer.min_hunger_to_lose_health).toLong() || petpreference.getPetHunger() > 100.toLong())
            {
                petpreference.changePetHealth(this.resources.getInteger(R.integer.health_change_per_min).toLong())
            }
            petpreference.changePetHappiness(this.resources.getInteger(R.integer.happiness_change_per_min).toLong())
            petpreference.changePetHunger(this.resources.getInteger(R.integer.hunger_change_per_min).toLong())

            Log.d("petTimelineEventService", "timeline file found")
            val reader = BufferedReader(FileReader(f))
            val gson = Gson()
            val timeline: Timeline = gson.fromJson(reader, object : TypeToken<Timeline>() {}.type)
            timeline.CheckTimeline(this)

            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
            val jsonString = gson.toJson(timeline);
            writer.write(jsonString);
            writer.close();

            Log.d("petAlarm service update", "current pet values: ")
            Log.d("pethappiness", petpreference.getPetHappiness().toString())
            Log.d("pethealth", petpreference.getPetHealth().toString())
            Log.d("pethunger", petpreference.getPetHunger().toString())
            Log.d("petstepsToday", TimelineUtil().getCurrentTimeline(this).GetCurrentTimelineDay().stepsTaken.toString())
        }
        else
        {
            Log.d("petTimelineEventService", "no timeline file found")
        }
    }
}
