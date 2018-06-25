package petpet.petpet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import petpet.petpet.Pet.PetPreference

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setPetInfo()
    }

    fun setPetInfo() {
        val preference = PetPreference(this)
        val str = preference.getPetBreed()
        findViewById<TextView>(R.id.pet_breed).text = str
        findViewById<TextView>(R.id.pet_description).text = preference.getPetDescription()
    }
}
