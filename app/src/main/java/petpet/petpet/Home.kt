package petpet.petpet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import petpet.petpet.pet.PetPreference
import petpet.petpet.settings.SettingsContainerFragment
import petpet.petpet.store.StoreCategory
import petpet.petpet.store.StoreHelper
import petpet.petpet.store.StoreType

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity() {

    lateinit var store : Map<StoreType, StoreCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //init settings fragment
        findViewById<ImageButton>(R.id.home_settings).setOnClickListener {
            SettingsContainerFragment().show(fragmentManager, "SettingsContainerFragment")
        }

        //load store info to current activities
        val breed = PetPreference(this).getPetBreed().toString()
        store = StoreHelper().loadingStoreInfo(this, breed)
    }

    fun refreshHome() {
        startActivity(intent)
        finish()
    }

}
