package petpet.petpet

import android.content.Context
import com.google.gson.annotations.SerializedName
import petpet.petpet.pet.PetPreference

/**
 * Created by user on 2018-06-13.
 */
class PetEventEffect {
    //depending on whether an event is completed or not, it would have different effects
    //instanced events are always completed
    @SerializedName("completedEffects")
    var completedEffects = ArrayList<PetEffect>()
    @SerializedName("incompleteEffects")
    var incompleteEffects = ArrayList<PetEffect>()

    //constructor, should let incomplete be empty by default
    constructor(complete: ArrayList<PetEffect>, incomplete:ArrayList<PetEffect> = ArrayList<PetEffect>())
    {
        this.completedEffects = complete
        this.incompleteEffects = incomplete
    }
}

class PetEffect{
    //changes the property of pet with name "field" by some value
    @SerializedName("fieldName")
    var field : String = ""
    @SerializedName("valueChange")
    var valueChange : Int = 0

    //constructor
    constructor(field:String = "", valueChange: Int = 0)
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
            petpreference.changePetHunger(valueChange as Long)
        }
        else if(field == "happiness")
        {
            petpreference.changePetHappiness(valueChange as Long)
        }
        else
        {

        }
    }
}