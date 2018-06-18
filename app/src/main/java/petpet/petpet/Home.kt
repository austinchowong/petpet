package petpet.petpet

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import petpet.petpet.Pet.FeedFragment
import petpet.petpet.Pet.PlayFragment
import petpet.petpet.Pet.WalkFragment
import petpet.petpet.Pet.PetPreference

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity() {

    lateinit var feed_button: Button
    lateinit var play_button: Button
    lateinit var walk_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        feed_button = findViewById(R.id.feed_button)
        feed_button.setOnClickListener {
            var dialog = FeedFragment()
            dialog.show(fragmentManager, "FeedFragment")
        }

        play_button = findViewById(R.id.play_button)
        play_button.setOnClickListener {
            var dialog = PlayFragment()
            dialog.show(fragmentManager, "PlayFragment")
        }

        walk_button = findViewById(R.id.walk_button)
        walk_button.setOnClickListener {
            var dialog = WalkFragment()
            dialog.show(fragmentManager, "WalkFragment")
        }


        setPetInfo()


    }

    fun setPetInfo() {
        val preference = PetPreference(this)
        val str = preference.getPetBreed()
        findViewById<TextView>(R.id.pet_breed).text = str
        findViewById<TextView>(R.id.pet_description).text = preference.getPetDescription()
    }
}
