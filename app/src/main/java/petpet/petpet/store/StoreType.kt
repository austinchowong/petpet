package petpet.petpet.store

import com.google.gson.annotations.SerializedName

enum class StoreType (val type : String) {
    @SerializedName("food")
    FOOD("food"),

    @SerializedName("treat")
    TREAT("treat"),

    @SerializedName("toy")
    TOY("toy"),

    @SerializedName("other")
    OTHER("other")
}