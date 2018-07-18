package petpet.petpet.pet

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import petpet.petpet.Home
import petpet.petpet.R

/*
    use to store pet related data locally
 */
class PetPreference (val context: Context) {
    private val prefFileName : String = "PET"
    private val hasPet = "hasPet"
    private val prefBreed: String = "prefBreed"
    private val prefDescription: String = "prefDescription"
    private val prefId: String = "prefId"
    private val prefBreedTag = "prefBreedTag"

    val prefTimelineFileName : String = "PetTimeline.json"
    private val prefHunger : String = "prefHunger"
    private val prefHappiness : String = "prefHappiness"
    private val prefHealth : String = "prefHealth"

    ///pet status fields
    private val numMissedVaccineShots : String = "numMissedVaccineShots"
    private val numTimesWalked : String = "numTimesWalked"
    private val numTotalStepsTaken : String = "numStepsTaken"
    private val totalBudget : String = "totalBudget"

    val preference = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    fun hasPet() : Boolean {
        return preference.getBoolean(hasPet, false)
    }

    fun setHasPet(pet : Boolean) {
        preference.edit().putBoolean(hasPet, pet).apply()
    }

    fun setPetPreference(view : View) {
        var breed = view.findViewById<TextView>(R.id.pet_item_name).text.toString()
        var breedTag = ""
        if(breed.contains("Corgi"))
        {
            breedTag = "pixelcorgi"
        }
        else if(breed.contains("Husky"))
        {
            breedTag = "husky"
        }
        preference.edit()
                .putString(prefBreed, view.findViewById<TextView>(R.id.pet_item_name).text.toString())
                .putString(prefDescription, view.findViewById<TextView>(R.id.pet_item_description).text.toString())
                .putString(prefBreedTag, breedTag)
                .putLong(prefId, view.tag as Long)
                .putLong(prefHunger, 45L)
                .putLong(prefHappiness, 45L)
                .putLong(prefHealth, 45L)
                .apply()
        setHasPet(true)
    }

    fun getPetBreed(): String? {
        return preference.getString(prefBreed, "")
    }

    fun getPetDescription() : String {
        return preference.getString(prefDescription, "")
    }

    fun getPetBreedTag() : String {
        return preference.getString(prefBreedTag, "")
    }

    fun changePetHunger(changeValue: Long)
    {
        var hunger = preference.getLong(prefHunger, 0)
        hunger += changeValue
        preference.edit().putLong(prefHunger, hunger).apply()
        Log.d("change pet hunger", hunger.toString())
    }

    fun getPetHunger() : Long
    {
        return preference.getLong(prefHunger, 0)
    }

    fun changePetHappiness(changeValue : Long)
    {
        var happiness = preference.getLong(prefHappiness, 0)
        happiness += changeValue
        preference.edit().putLong(prefHappiness, happiness).apply()
        Log.d("change pet happiness", happiness.toString())
    }

    fun getPetHappiness() : Long
    {
        return preference.getLong(prefHappiness, 0)
    }

    fun changePetHealth(changeValue: Long)
    {
        var health = preference.getLong(prefHealth, 0)
        health += changeValue
        preference.edit().putLong(prefHealth, health).apply()
        Log.d("change pet health", health.toString())
    }

    fun getPetHealth() : Long
    {
        return preference.getLong(prefHealth, 0)
    }

    fun addMissedVaccineShot()
    {
        var numShots = preference.getLong(numMissedVaccineShots, 0)
        numShots++
        preference.edit().putLong(numMissedVaccineShots, numShots).apply()
    }

    fun getNumVaccineShotMissed() : Long
    {
        return preference.getLong(numMissedVaccineShots, 0)
    }

    fun addWalk()
    {
        var numWalks = preference.getLong(numTimesWalked, 0)
        numWalks++
        preference.edit().putLong(numTimesWalked, numWalks).apply()
    }

    fun getNumWalks() : Long
    {
        return preference.getLong(numTimesWalked, 0)
    }

    fun addSteps(changeValue: Long)
    {
        var numSteps = preference.getLong(numTotalStepsTaken, 0)
        numSteps += changeValue
        preference.edit().putLong(numTotalStepsTaken, numSteps).apply()
    }

    fun getNumSteps() : Long
    {
        return preference.getLong(numTotalStepsTaken, 0)
    }

    fun addToBudget(amount : Long)
    {
        var budget = preference.getLong(totalBudget, 0)
        budget += amount
        preference.edit().putLong(totalBudget, budget).apply()
    }

    fun getTotalBudget() : Long
    {
        return preference.getLong(totalBudget, 0)
    }

    fun getPetPreferenceName() :String {
        return prefFileName
    }
}