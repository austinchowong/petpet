package petpet.petpet.store

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

class StoreCategory {
    @SerializedName("category")
    var category : StoreType = StoreType.OTHER

    @SerializedName("description")
    var description : String = ""

    @SerializedName("items")
    var items = ArrayList<StoreItem>()
}