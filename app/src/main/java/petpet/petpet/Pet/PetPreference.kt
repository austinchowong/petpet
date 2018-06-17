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

    val prefTimelineFileName : String = "PetTimeline"
    private val prefHunger : String = "prefHunger"
    private val prefHappiness : String = "prefHappiness"

    val preference = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    fun hasPet() : Boolean {
        return preference.getBoolean(hasPet, false)
    }

    fun setHasPet(pet : Boolean) {
        preference.edit().putBoolean(hasPet, pet).apply()
    }

    fun setPetPreference(view : View) {
        preference.edit().putString(prefBreed, view.findViewById<TextView>(R.id.pet_item_name).text.toString()).apply()
        preference.edit().putString(prefDescription, view.findViewById<TextView>(R.id.pet_item_description).text.toString()).apply()
        preference.edit().putLong(prefId, view.tag as Long).apply()
        preference.edit().putLong(prefHunger, 50L).apply()
        preference.edit().putLong(prefHappiness, 50L).apply()
        setHasPet(true)
    }

    fun getPetBreed(): String? {
        return preference.getString(prefBreed, "")
    }

    fun getPetDescription() : String {
        return preference.getString(prefDescription, "")
    }

    fun changePetHunger(changeValue: Long)
    {
        var hunger = preference.getLong(prefHunger, 0)
        hunger += changeValue
        preference.edit().putLong(prefHunger, hunger).apply()
    }

    fun changePetHappiness(changeValue : Long)
    {
        var happiness = preference.getLong(prefHappiness, 0)
        happiness += changeValue
        preference.edit().putLong(prefHappiness, happiness).apply()
    }
}