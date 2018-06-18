package petpet.petpet.Pet

import android.content.Context
import android.view.View
import android.widget.TextView
import petpet.petpet.R

/*
    use to store pet related data locally
 */
class PetPreference (context: Context) {
    private val prefFileName : String = "PET"
    private val hasPet = "hasPet"
    private val prefBreed: String = "prefBreed"
    private val prefDescription: String = "prefDescription"
    private val prefId: String = "prefId"

    val preference = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)


    fun hasPet() : Boolean {
        return preference.getBoolean(hasPet, false)
    }

    fun setHasPet(pet : Boolean) {
        preference.edit().putBoolean(hasPet, pet).apply()
    }

    fun setPetPreference(view : View) {
        preference.edit()
                .putString(prefBreed, view.findViewById<TextView>(R.id.pet_item_name).text.toString())
                .putString(prefDescription, view.findViewById<TextView>(R.id.pet_item_description).text.toString())
                .putLong(prefId, view.tag as Long)
                .apply()
        setHasPet(true)
    }

    fun getPetBreed(): String? {
        return preference.getString(prefBreed, "")
    }

    fun getPetDescription() : String {
        return preference.getString(prefDescription, "")
    }
}