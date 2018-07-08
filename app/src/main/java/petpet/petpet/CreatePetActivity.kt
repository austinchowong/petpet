package petpet.petpet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.Pet
import petpet.petpet.pet.PetItemAdapter
import petpet.petpet.pet.PetPreference
import java.io.*
import java.io.BufferedReader
import java.io.InputStreamReader
import petpet.petpet.timeline.Timeline
import petpet.petpet.store.StoreHelper

class CreatePetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var selectedBreed : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pet)

        //Load available list of pets
        val reader = BufferedReader(InputStreamReader(assets.open("petlist.json")))
        val petList : List<Pet> = Gson().fromJson(reader ,object : TypeToken<List<Pet>>() {}.type )
        val list_pet =  petList.toTypedArray()

        //init RecyclerView
        viewManager = LinearLayoutManager(this)
        viewAdapter = PetItemAdapter(list_pet, this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_create_pet).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun loadTimeline()
    {
        val reader = BufferedReader(InputStreamReader(this.assets.open(selectedBreed + ".json")))
        val gson = Gson()
        val timeline : Timeline = gson.fromJson(reader ,object : TypeToken<Timeline>() {}.type )
        timeline.initTimeline(this)

        val petpreference = PetPreference(this)
        val f = File(filesDir.path + "/" + petpreference.prefTimelineFileName)
        if(!f.exists())
        {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if(f.exists()) {
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
            val jsonString = gson.toJson(timeline)
            writer.write(jsonString)
            writer.close()
        }
        else
        {
            Log.e("Timeline", "failed to create timeline file")
        }
    }

    fun choosePet(view : View) {
        //load pet info
        PetPreference(this).setPetPreference(findViewById<CardView>(R.id.pet_item))

        //loading pet's timeline and events in system
            selectedBreed = PetPreference(this).getPetBreed().toString()
            loadTimeline()

        //load store info for selected breed
        StoreHelper().initStoreInfo(this, selectedBreed)

        val intent = Intent(this, Home::class.java)
        finish()
        startActivity(intent)
    }
}
