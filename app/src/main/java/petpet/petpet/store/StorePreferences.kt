package petpet.petpet.store

import android.content.Context

/*
    StorePreferences class uses to store StoreItems based on StoreCategory.
    Each StoreCategory is saved as a preferences file.
 */
class StorePreferences(context: Context, category : String) {
    private val preferences = context.getSharedPreferences(category, Context.MODE_PRIVATE)

    fun getString(key: String) : String {
        return preferences.getString(key, "")
    }

    fun setString(key: String, value : String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getInt(key: String) : Int {
        return preferences.getInt(key, -1)
    }

    fun setInt(key: String, value : Int) {
        preferences.edit().putInt(key, value).apply()
    }

}