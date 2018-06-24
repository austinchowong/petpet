package petpet.petpet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import petpet.petpet.event.FeedFragment
import petpet.petpet.event.PlayFragment
import petpet.petpet.event.WalkFragment
import petpet.petpet.pet.PetPreference
import petpet.petpet.updatePet.UpdatePetIntentService
import java.util.*


class Home : AppCompatActivity() {

    lateinit var feed_button: Button
    lateinit var play_button: Button
    lateinit var walk_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        feed_button = findViewById(R.id.feed_button)
        feed_button.setOnClickListener {
            val dialog = FeedFragment()
            dialog.show(fragmentManager, "FeedFragment")
        }

        play_button = findViewById(R.id.play_button)
        play_button.setOnClickListener {
            val dialog = PlayFragment()
            dialog.show(fragmentManager, "PlayFragment")
        }

        walk_button = findViewById(R.id.walk_button)
        walk_button.setOnClickListener {
            val dialog = WalkFragment()
            dialog.show(fragmentManager, "WalkFragment")
        }


        //setAlarmMgr()

    }


    /*
        Set up alarm to run background service for future update
        TODO: set alarm and background service
     */
    private fun setAlarmMgr() {
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar : Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        val intent = Intent(applicationContext, UpdatePetIntentService::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 1000 * 60 * 5,  alarmIntent)
    }
}
