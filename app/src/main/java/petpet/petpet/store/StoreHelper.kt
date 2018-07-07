package petpet.petpet.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class StoreHelper {
    fun initStoreInfo(context: Context, breed : String) {
        val reader = BufferedReader(InputStreamReader(context.assets.open(breed + "_store.json")))
        val categories : ArrayList<StoreCategory> = Gson().fromJson(reader ,object : TypeToken<List<StoreCategory>>() {}.type )
        for (category in categories) {
            val storePreferences = ItemPreferences(context, category.category.name)
            for (item in category.items) {
                storePreferences.setInt(item.name, 0)
            }
        }
    }

    fun loadingStoreInfo(context: Context, breed: String) : Map<StoreType, StoreCategory> {
        val result = hashMapOf<StoreType, StoreCategory>()
        val reader = BufferedReader(InputStreamReader(context.assets.open(breed + "_store.json")))
        val categories : ArrayList<StoreCategory> = Gson().fromJson(reader ,object : TypeToken<List<StoreCategory>>() {}.type )

        for (category in categories) {
            result.put(category.category, category)
        }
        return result
    }
}