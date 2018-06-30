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
            Vet(this).VetVisit()
        }
    }

    fun setPetInfo() {
        val preference = PetPreference(this)
        val str = preference.getPetBreed()
//        findViewById<TextView>(R.id.pet_description).text = preference.getPetDescription()
    }
}
