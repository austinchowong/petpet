package petpet.petpet.timeline

import android.content.Context
import com.google.gson.annotations.SerializedName
import petpet.petpet.pet.PetPreference

class TimelineEventEffect {
    //changes the property of pet with name "field" by some value
    @SerializedName("fieldName")
    var field : String = ""
    @SerializedName("valueChange")
    var valueChange : Long = 0

    //constructor
    constructor(field:String = "", valueChange: Long = 0)
    {
        this.field = field
        this.valueChange = valueChange
    }

    fun ApplyEffect(context:Context)
    {
        //change the values through petpreference
        val petpreference = PetPreference(context)
        if(field == "hunger")
        {
            petpreference.changePetHunger(valueChange)
        }
        else if(field == "happiness")
        {
            petpreference.changePetHappiness(valueChange)
        }
        else if(field == "health")
        {
            petpreference.changePetHealth(valueChange)
        }
    }
}