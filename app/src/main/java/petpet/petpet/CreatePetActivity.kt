package petpet.petpet

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.Pet.Pet
import petpet.petpet.Pet.PetItemAdapter
import petpet.petpet.Pet.PetPreference
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
        val gson = Gson()
        val petList : List<Pet> = gson.fromJson(reader ,object : TypeToken<List<Pet>>() {}.type )
        return  petList.toTypedArray()
    }

    fun choosePet(view : View) {
        showLoading()
        PetPreference(this).setPetPreference(findViewById<CardView>(R.id.pet_item))

        /*
            TODO: loading pet's timeline and events in system
            findViewById<CardView>(R.id.pet_item).tag contains an id for selected breed
         */

        val intent = Intent(this, Home::class.java)
        finish()
        startActivity(intent)
    }

    //hide pet list and display loading image
    private fun showLoading() {
        findViewById<LinearLayoutCompat>(R.id.cnp_pet_list).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.cnp_loading_indicator).visibility = View.VISIBLE
    }
}