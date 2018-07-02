package petpet.petpet.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import petpet.petpet.Home
import petpet.petpet.R
import petpet.petpet.utility.LanguageUtil

class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load the preference from resource
        addPreferencesFromResource(R.xml.pref_general)
        LanguageUtil().updateSettingsBaseOnSystem(activity)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        //update language
        if(key == resources.getString(R.string.KEY_SETTING_LANGUAGE)) {
            val language = PreferenceManager.getDefaultSharedPreferences(activity)
                    .getString(resources.getString(R.string.KEY_SETTING_LANGUAGE), null)
            LanguageUtil().setLanguage(language, activity)
            (activity as Home).refreshHome()
        }
    }

}