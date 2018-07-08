package petpet.petpet

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import petpet.petpet.event.WalkFragment
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import petpet.petpet.pet.PetPreference
import petpet.petpet.store.StoreCategory
import petpet.petpet.store.StoreHelper
import petpet.petpet.store.StoreType
import petpet.petpet.utility.LanguageUtil
import java.util.*

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var store : Map<StoreType, StoreCategory>
    lateinit var walk_button : Button

    lateinit var timer : Timer
    var isTimerRunning : Boolean = false

    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            updateProgBars()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //load store info to current activities
        val breed = PetPreference(this).getPetBreed().toString()
        store = StoreHelper().loadingStoreInfo(this, breed)

        //init menu button
        findViewById<ImageButton>(R.id.home_menuButton).setOnClickListener{
            MenuFragment().show(fragmentManager, "MenuFragment")
        }

        val petName = PreferenceManager.getDefaultSharedPreferences(this).getString("pet_name","")
        if (petName != "") findViewById<TextView>(R.id.home_pet_name).text = petName

        updateProgBars()
        StartTimer()
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        //update petName
        if(key == "pet_name") {
            findViewById<TextView>(R.id.home_pet_name).text = PreferenceManager.getDefaultSharedPreferences(this).getString("pet_name","")
        }
    }

    override fun onStop() {
        super.onStop()
        StopTimer()
    }

    fun refreshHome() {
        finish()
        startActivity(intent)
    }

    fun updateProgBars()
    {
        //update progress bars
        val petpreference = PetPreference(this)

        var hungerProg : ProgressBar = findViewById(R.id.progressBar1)
        hungerProg.progress = petpreference.getPetHunger().toInt()
        var hungerPercent : TextView = findViewById(R.id.home_progress_value_1)
        hungerPercent.text = petpreference.getPetHunger().toString() + "%"

        var happinessProg : ProgressBar = findViewById(R.id.progressBar2)
        happinessProg.progress = petpreference.getPetHappiness().toInt()
        var happinessPercent : TextView = findViewById(R.id.home_progress_value_2)
        happinessPercent.text = petpreference.getPetHappiness().toString() + "%"

        var healthProg : ProgressBar = findViewById(R.id.progressBar3)
        healthProg.progress = petpreference.getPetHealth().toInt()
        var healthPercent : TextView = findViewById(R.id.home_progress_value_3)
        healthPercent.text = petpreference.getPetHealth().toString() + "%"

        Log.d("home", "update prog bars")
    }

    fun StartTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                mHandler.obtainMessage().sendToTarget()
            }
        }, 0, 10 * 1000)
    }

    fun StopTimer()
    {
        timer.cancel()
    }
}
