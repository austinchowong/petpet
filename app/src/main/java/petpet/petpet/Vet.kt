package petpet.petpet

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.event.PetAnimator
import petpet.petpet.pet.PetPreference
import petpet.petpet.timeline.Timeline
import petpet.petpet.timeline.TimelineUtil
import java.io.*

/**
 * Created by user on 2018-06-30.
 */
class Vet () {

    fun VetVisit(context: Context) {
        var timeline = TimelineUtil().getCurrentTimeline(context)

        var today = timeline.GetCurrentTimelineDay()
        if (today.eventTracker.containsKey("vet") && today.eventTracker["vet"] == "inprogress")
        {
            //vet event queued, need to resolve
            val petpreference = PetPreference(context)

            petpreference.changePetHealth(15)
            petpreference.changePetHappiness(-10)

            today.eventTracker["vet"] = "complete"
            timeline.CheckTimeline(context)
            Log.d("petHome", "visited vet")

            Log.d("visited vet", "new pet values: ")
            Log.d("pethappiness", petpreference.getPetHappiness().toString())
            Log.d("pethealth", petpreference.getPetHealth().toString())
            Log.d("pethunger", petpreference.getPetHunger().toString())

            PetAnimator.notifyObservers("pixelcorgivet")
        } else {
            //no need to go to vet, no event started yet
            Log.d("petHome", "dont need to visit vet")
        }

        TimelineUtil().setCurrentTimeline(context, timeline)
    }
}