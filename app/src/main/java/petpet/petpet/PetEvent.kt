package petpet.petpet

import android.text.format.Time
import java.util.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

/**
 * Created by user on 2018-06-06.
 */
class PetEvent {

    //******
    //every time the user does something which has an impact on the pet, we should create an event
    //and store it somewhere, possibly in the timeline, but not necessarily

    //if more fields get added that needs to be serialized, they would need to be added
    //to the jsonutil class which is currently used to make the conversion
    @SerializedName("name")
    var name: String? = null;
    @SerializedName("description")
    var description: String? = null;
    @SerializedName("id")
    var id: Int = 0;

    //need to make relative format
    @SerializedName("startTime")
    var startTime : Date? = null;
    @SerializedName("endTime")
    var endTime : Date? = null;

    @SerializedName("completedTime")
    var completedTime : Date? = null;
    @SerializedName("isComplete")
    var isComplete = false;
    @SerializedName("isActive")
    var isActive = false;
    @SerializedName("isInstanced")
    var isInstanced = false;    //used to determine whether this event was part of the original
                                // scripted timeline or generated through an user action
    @SerializedName("criteriaList")
    var criteriaList = ArrayList<Criteria>();

    //should have some fields that correspond to the results of completing/not completing this event

    //numerical effects on pet fields
    @SerializedName("effects")
    var effects : PetEventEffect? = null;

    //constructor for when events need to be created dynamically
    //would every possible event be already generated and we can just grab pre written events
    //that correspond to actions?
    //instanced events would have no criteria? since they're complete as soon as you create them
    constructor(name:String, description:String, id:Int, startTime:Date, endTime:Date, isInstanced:Boolean,
                criteriaList: ArrayList<Criteria>, effects:PetEventEffect)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.effects = effects;
        if(isInstanced)
        {
            //already completed, so time and criteria doesn't matter
            this.isInstanced = true;
            this.isComplete = true;
            //this.completedTime = now;
        }
        else
        {
            //only need this information if this event is not an user action that has already been completed
            this.isInstanced = false;
            this.isComplete = false;
            this.startTime = startTime;
            this.endTime = endTime;
            this.criteriaList = criteriaList;
        }
    }

    //returns whether this event is complete or not by checking
    //the list of criteria that it has to fulfill
    fun IsComplete() : Boolean
    {
        if(isComplete) return isComplete;

        for(criteria in criteriaList)
        {
            if(!criteria.IsFulfilled())
            {
                return false;
            }
        }
        isComplete = true;
        completedTime = GregorianCalendar.getInstance().time;
        return true;
    }

    fun CheckActive(initialTime: Date) : Boolean
    {
        //given the starting date of the timeline, is this event currently active
        if(isActive)
        {
            val now = GregorianCalendar.getInstance().time;
            var difference = now.getTime() - initialTime.getTime();
            //startTime and endTime should be relative times from the start
            if(difference >= endTime!!.getTime())
            {
                isActive = false;
            }
        }
        else
        {
            //check to see if we should activate it
            val now = GregorianCalendar.getInstance().time;
            var difference = now.getTime() - initialTime.getTime();
            //startTime and endTime should be relative times from the start
            if(difference >= startTime!!.getTime() && difference <= endTime!!.getTime())
            {
                isActive = true;
            }
        }
        return isActive
    }

    //should be able to register the completed event in order to have values be modified according
    //to the results specified in the event, regardless of whether finished or not I guess.
    //since could have positive or negative effects

    fun EventComplete()
    {
        //the event was compelted by the user, I guess the event should specify what its
        //effects are after being complete
        //apply complete effects
        if(effects == null) return;

        for(peteffect in effects!!.completedEffects)
        {
            peteffect.ApplyEffect(pet);
        }
    }

    fun EventIncomplete()
    {
        //eventually timer will check back and see that this event has reached the end but was not fulfilled
        //something should happen here, not sure what though

        if(effects == null) return;

        for(peteffect in effects!!.incompleteEffects)
        {
            peteffect.ApplyEffect(pet);
        }
    }
}