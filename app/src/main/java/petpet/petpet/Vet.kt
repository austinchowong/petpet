package petpet.petpet

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import petpet.petpet.timeline.Timeline
import java.io.*

/**
 * Created by user on 2018-06-30.
 */
class Vet (context: Context) {

    val ctx = context

    fun VetVisit()
    {
        val petpreference = PetPreference(ctx)
        val f = File(ctx.filesDir.path + "/" +petpreference.prefTimelineFileName)
        if(f.exists())
        {
            Log.d("petTimelineEventService", "timeline file found")
            val reader = BufferedReader(FileReader(f))
            val gson = Gson()
            val timeline: Timeline = gson.fromJson(reader, object : TypeToken<Timeline>() {}.type)

            var today = timeline.GetCurrentTimelineDay()
            if(today.eventTracker.containsKey("vet") && today.eventTracker["vet"] == "inprogress")
            {
                //vet event queued, need to resolve
                val petpreference = PetPreference(ctx)

                petpreference.changePetHealth(15)
                petpreference.changePetHappiness(-10)

                today.eventTracker["vet"] = "complete"
                timeline.CheckTimeline(ctx)
                Log.d("petHome", "visited vet")

                Log.d("visited vet", "new pet values: ")
                Log.d("pethappiness", petpreference.getPetHappiness().toString())
                Log.d("pethealth", petpreference.getPetHealth().toString())
                Log.d("pethunger", petpreference.getPetHunger().toString())
            }
            else
            {
                //no need to go to vet, no event started yet
                Log.d("petHome", "dont need to visit vet")
            }

            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
            val jsonString = gson.toJson(timeline);
            writer.write(jsonString);
            writer.close();
        }
    }
}