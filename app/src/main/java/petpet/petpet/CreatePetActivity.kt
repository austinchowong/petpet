package petpet.petpet

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.os.Build
import android.content.pm.PackageManager
import android.widget.Toast
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

    private fun getWritePermissions()
    {
        val REQUEST = 112

        if (Build.VERSION.SDK_INT >= 23) {
            val PERMISSIONS = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this as Activity, PERMISSIONS, REQUEST)
            } else {
                loadTimeline()
            }
        } else {
            loadTimeline()
        }
    }

    private fun loadTimeline()
    {
        val reader = BufferedReader(InputStreamReader(this.assets.open(selectedBreed + ".json")))
        val gson = Gson()
        val timeline : Timeline = gson.fromJson(reader ,object : TypeToken<Timeline>() {}.type )
        timeline.initTimeline(this)

        val petpreference = PetPreference(this)
        val f = File(Environment.getExternalStorageDirectory().path + petpreference.prefTimelineFileName)
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
            val jsonString = gson.toJson(timeline)
            writer.write(jsonString)
            writer.close()
        }
    }

    override fun onRequestPermissionsResult(requestCode : Int, permissions: Array<String>, grantResults : IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            112 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadTimeline()
                } else {
                    Toast.makeText(this, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun choosePet(view : View) {
        //TODO: load pet info
        PetPreference(this).setPetPreference(findViewById<CardView>(R.id.pet_item))

        selectedBreed = "Welsh Corgi"

        //load store info for selected breed
        StoreHelper().initStoreInfo(this, selectedBreed)

        //TODO: loading pet's timeline and events in system
        getWritePermissions()


        val intent = Intent(this, Home::class.java)
        finish()
        startActivity(intent)
    }
}
