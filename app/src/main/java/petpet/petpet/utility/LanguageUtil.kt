package petpet.petpet.utility

import android.content.Context
import android.preference.PreferenceManager
import petpet.petpet.R
import java.util.*

@Suppress("DEPRECATION")
class LanguageUtil {
    val SET_LANGUAGE = "set_language"

    fun setLanguageBaseOnSettings(context: Context){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (sharedPreferences.getBoolean(SET_LANGUAGE, false)) {
            val language = sharedPreferences.getString(context.resources.getString(R.string.KEY_SETTING_LANGUAGE), "")
            setLanguage(language, context)
        }
    }

    //when the language set and the system language is not supported by the app
    fun updateSettingsBaseOnSystem(context: Context){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if(sharedPreferences.getBoolean(SET_LANGUAGE, false)) return
        if(!context.resources.getStringArray(R.array.language_entryValues).contains(Locale.getDefault().language)) return

        //update app settings's info
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(R.string.KEY_SETTING_LANGUAGE.toString(), "")
                .putBoolean(SET_LANGUAGE, true).apply()

    }

    fun setLanguage(language: String, context: Context) {
        if (language.isEmpty()) return
        val config = context.resources.configuration
        config.setLocale(Locale(language))
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}