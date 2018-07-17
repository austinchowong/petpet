package petpet.petpet

import android.content.Context
import android.content.Intent
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
import petpet.petpet.event.Observer
import petpet.petpet.event.PetAnimator
import petpet.petpet.pet.PetPreference
import petpet.petpet.store.StoreCategory
import petpet.petpet.store.StoreHelper
import petpet.petpet.store.StoreType
import petpet.petpet.utility.BgmService
import petpet.petpet.utility.LanguageUtil
import pl.droidsonroids.gif.GifImageView
import java.util.*

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, Observer {

    lateinit var store : Map<StoreType, StoreCategory>
    lateinit var walk_button : Button

    lateinit var timer : Timer
    var isTimerRunning : Boolean = false
    var animator : PetAnimator = PetAnimator

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

        val petName = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(resources.getString(R.string.PET_NAME_KEY),"")
        if (petName != "") findViewById<TextView>(R.id.home_pet_name).text = petName

        updateProgBars()
        //animator.attachObserver(findViewById(R.id.doganimator))
        animator.attachObserver(this)
        animator.notifyObservers("pixelcorgiidle")
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
        startService(Intent(baseContext, BgmService::class.java))
        StartTimer()
    }

    override fun onPause() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
        stopService(Intent(baseContext, BgmService::class.java))
        StopTimer()
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        //update petName
        if(key == resources.getString(R.string.PET_NAME_KEY)) {
            findViewById<TextView>(R.id.home_pet_name).text = PreferenceManager.getDefaultSharedPreferences(this).getString("pet_name","")
        }
    }

    fun refreshHome() {
        finish()
        startActivity(intent)
    }

    fun updateProgBars()
    {
        //update progress bars
        val petpreference = PetPreference(this)

        val hungerProg : ProgressBar = findViewById(R.id.progressBar1)
        hungerProg.progress = petpreference.getPetHunger().toInt()
        val hungerPercent : TextView = findViewById(R.id.home_progress_value_1)
        hungerPercent.text = petpreference.getPetHunger().toString() + "%"

        val happinessProg : ProgressBar = findViewById(R.id.progressBar2)
        happinessProg.progress = petpreference.getPetHappiness().toInt()
        val happinessPercent : TextView = findViewById(R.id.home_progress_value_2)
        happinessPercent.text = petpreference.getPetHappiness().toString() + "%"

        val healthProg : ProgressBar = findViewById(R.id.progressBar3)
        healthProg.progress = petpreference.getPetHealth().toInt()
        val healthPercent : TextView = findViewById(R.id.home_progress_value_3)
        healthPercent.text = petpreference.getPetHealth().toString() + "%"

        Log.d("home", "update prog bars")
    }

    fun StartTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                mHandler.obtainMessage().sendToTarget()
            }
        }, 0, 1000)
    }

    fun StopTimer()
    {
        timer.cancel()
    }

    override fun update(gif : String){
        var imgFp = findViewById<GifImageView>(R.id.doganimator) //as GifImageView
        val gifID = resources.getIdentifier(gif, "drawable", packageName)
        imgFp.setImageResource(gifID)
    }
}
