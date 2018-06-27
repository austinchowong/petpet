package petpet.petpet.pet

import com.google.gson.annotations.SerializedName

class Pet {
    private var instance: Pet? = null

    @SerializedName("id")
    val id : Long = 0

    @SerializedName("breed")
    val breed : String = ""

    @SerializedName("description")
    val description : String = ""

    @SerializedName("image")
    val image : String = ""
}