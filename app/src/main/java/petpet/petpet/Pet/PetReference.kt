package petpet.petpet.Pet

import android.content.Context

class PetReference (context: Context) {
    val preference_name = "PET"
    val has_pet = "hasPet"

    val preference = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE)

    fun hasPet() : Boolean {
        return preference.getBoolean(has_pet, false)
    }

    fun setPet(pet : Boolean) {
        preference.edit().putBoolean(has_pet, pet).apply()
    }
}