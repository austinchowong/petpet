package petpet.petpet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import petpet.petpet.settings.SettingsContainerFragment
import petpet.petpet.settings.SettingsFragment

/*
    TODO:
    temp home related function. this file will be replace by Ilene
    home activity is displaying pet's breed and description
 */
class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //display settings fragment
        setSettings()
    }

    fun setSettings()
    {
        findViewById<ImageButton>(R.id.home_settings).setOnClickListener {
            SettingsContainerFragment().show(fragmentManager, "SettingsContainerFragment")
        }
    }

    fun refreshHome() {
        val intent = intent
        startActivity(intent)
        finish()
    }

}
