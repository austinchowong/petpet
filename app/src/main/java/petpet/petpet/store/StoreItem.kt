package petpet.petpet.store

import com.google.gson.annotations.SerializedName

class StoreItem {
    @SerializedName("name")
    var name : String = ""

    @SerializedName("description")
    var description : String = ""

    @SerializedName("image")
    var image : String = ""

    @SerializedName("price")
    var price : Int = 0

    @SerializedName("happiness")
    var happiness : Int = 0

    @SerializedName("hunger")
    var hunger : Int = 0

    @SerializedName("health")
    var health : Int = 0

    @SerializedName("category")
    var category : StoreType = StoreType.OTHER
}