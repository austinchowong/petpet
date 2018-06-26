package petpet.petpet

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.Pet
import petpet.petpet.pet.PetItemAdapter
import petpet.petpet.pet.PetPreference
import java.io.BufferedReader
import java.io.InputStreamReader

class CreatePetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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

    private fun loadTimeline(breed: String)
    {
        val reader = BufferedReader(InputStreamReader(this.assets.open(breed + ".json")))
        val gson = Gson()
        val timeline : Timeline = gson.fromJson(reader ,object : TypeToken<Timeline>() {}.type )
        timeline.initTimeline(this)

        //TODO: failed to write -> Caused by: java.io.FileNotFoundException: PetTimeline (Read-only file system)
//        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream("PetTimeline")))
//        val jsonString = gson.toJson(timeline)
//        writer.write(jsonString)
//        writer.close()
    }

    fun choosePet(view : View) {
        PetPreference(this).setPetPreference(findViewById<CardView>(R.id.pet_item))

		//TODO: load pet info
		
        /*
            TODO: loading pet's timeline and events in system
            findViewById<CardView>(R.id.pet_item).tag contains an id for selected breed
         */
		loadTimeline(view.tag.toString())

        val intent = Intent(this, Home::class.java)
        finish()
        startActivity(intent)
    }

}
