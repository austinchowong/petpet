package petpet.petpet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import petpet.petpet.timeline.Timeline
import java.io.*

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity() {

    lateinit var vet_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setPetInfo()

        vet_button = findViewById(R.id.vet_button)
        vet_button.setOnClickListener {
            VetVisit()
        }
    }

    fun VetVisit()
    {
        val petpreference = PetPreference(this)
        val f = File(filesDir.path + "/" +petpreference.prefTimelineFileName)
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
                val petpreference = PetPreference(this)

                petpreference.changePetHealth(15)
                petpreference.changePetHappiness(-10)

                today.eventTracker["vet"] = "complete"
                timeline.CheckTimeline(this)
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

    fun setPetInfo() {
        val preference = PetPreference(this)
        val str = preference.getPetBreed()
//        findViewById<TextView>(R.id.pet_description).text = preference.getPetDescription()
    }
}
