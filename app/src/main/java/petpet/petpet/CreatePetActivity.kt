package petpet.petpet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.Pet
import petpet.petpet.pet.PetItemAdapter
import petpet.petpet.pet.PetPreference
import java.io.*
import java.io.BufferedReader
import java.io.InputStreamReader
import petpet.petpet.timeline.Timeline
import petpet.petpet.timeline.TimelineEventService
import java.util.*

class CreatePetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var selectedBreed : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pet)

        val list_pet: Array<Pet> = initialize(this)
        viewManager = LinearLayoutManager(this)
        viewAdapter = PetItemAdapter(list_pet, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_create_pet).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun initialize(context: Context): Array<Pet> {
        val reader = BufferedReader(InputStreamReader(context.assets.open("petlist.json")))
        val petList : List<Pet> = Gson().fromJson(reader ,object : TypeToken<List<Pet>>() {}.type )
        return  petList.toTypedArray()
    }

    private fun loadTimeline()
    {
        val reader = BufferedReader(InputStreamReader(this.assets.open(selectedBreed + ".json")))
        val gson = Gson()
        val timeline : Timeline = gson.fromJson(reader ,object : TypeToken<Timeline>() {}.type )
        timeline.initTimeline(this)
        scheduleAlarm()

        val petpreference = PetPreference(this)
        val f = File(filesDir.path + "/" + petpreference.prefTimelineFileName)
        if(!f.exists())
        {
            try {
                f.createNewFile()
                Log.d("CreatePet", "created new timeline file at " + f.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if(f.exists()) {
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
            val jsonString = gson.toJson(timeline);
            writer.write(jsonString);
            writer.close();
        }
    }

    fun scheduleAlarm()
    {
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, TimelineEventService::class.java)
        val alarmIntent = PendingIntent.getService(this, 0, intent, 0)

        //repeat alarm every 2 mins
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(), 1000 * 30,  alarmIntent)

        //Log.d("timeline", "alarm started")
    }

    fun choosePet(view : View) {
        PetPreference(this).setPetPreference(findViewById<CardView>(R.id.pet_item))

        //TODO: load pet info

        //  TODO: loading pet's timeline and events in system
            selectedBreed = "Welsh Corgi"
            loadTimeline()
        //  findViewById<CardView>(R.id.pet_item).tag contains an id for selected breed


        val intent = Intent(this, Home::class.java)
        finish()
        startActivity(intent)
    }
}
