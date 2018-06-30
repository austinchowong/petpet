package petpet.petpet.timeline

import android.content.Context
import android.util.Log
import java.util.*
import com.google.gson.annotations.SerializedName
import petpet.petpet.pet.PetPreference
import petpet.petpet.utility.NotificationUtil
import kotlin.collections.ArrayList

/**
 * Created by user on 2018-06-06.
 */
class TimelineEvent {

    //******
    //every time the user does something which has an impact on the pet, we should create an event
    //and store it somewhere, possibly in the timeline, but not necessarily

    //if more fields get added that needs to be serialized, they would need to be added
    //to the jsonUtil class which is currently used to make the conversion
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("id")
    var id: Int = 0

    //need to make relative format
    @SerializedName("startTime")
    var startTime : String = ""
    @SerializedName("endTime")
    var endTime : String = ""

    @SerializedName("completedTime")
    var completedTime : Date? = null
    @SerializedName("isComplete")
    var isComplete = false
    @SerializedName("isActive")
    var isActive = false

    //numerical effects on pet fields
    @SerializedName("consequences")
    var consequences = ArrayList<TimelineEventEffect>()

    //constructor for when events need to be created dynamically
    //would every possible event be already generated and we can just grab pre written events
    //that correspond to actions?
    //instanced events would have no criteria? since they're complete as soon as you create them
    constructor(name:String, description:String, id:Int, startTime:String, endTime:String,
                isInstanced:Boolean, consequences: ArrayList<TimelineEventEffect>)
    {
        this.name = name
        this.description = description
        this.id = id
        this.consequences = consequences
        if(isInstanced)
        {
            //already completed, so time and criteria doesn't matter
            this.isComplete = true
            //this.completedTime = now
        }
        else
        {
            //only need this information if this event is not an user action that has already been completed
            this.isComplete = false
            this.startTime = startTime
            this.endTime = endTime
        }
    }

    fun CheckStatus(context: Context, startDate: Long, today : TimelineDay)
    {
        CheckActive(startDate, context, today)
    }

    //start the event, or skip and mark as complete if its already done
    fun Activate(context: Context, today : TimelineDay)
    {
        var needStartEventNotif = false
        if(today.eventTracker.containsKey(name))
        {
            if(today.eventTracker[name] == "complete") {
                //event was completed before started, skip over, or send a notification saying
                //"you already did this today"
                CompleteEvent(context, today)
            }
            else
            {
                //need to activate event, but the event is most likely half complete
                needStartEventNotif = true
            }
        }
        else
        {
            //hasnt been started yet
            today.eventTracker[name.toString()] = "inprogress"
            needStartEventNotif = true
        }

        if(needStartEventNotif)
        {
            isActive = true
            NotificationUtil().sendNotification(context, name.toString(), description.toString())
        }
    }

    fun Timeout(context: Context, today: TimelineDay)
    {
        today.eventTracker[name.toString()] = "incomplete"

        isComplete = false
        isActive = false
        NotificationUtil().sendNotification(context, name.toString(), "Forgot to do this...")

        //apply consequences
        ApplyConsequences(context)
    }

    fun CompleteEvent(context: Context, today : TimelineDay)
    {
        today.eventTracker[name.toString()] = "complete"

        isComplete = true
        completedTime = GregorianCalendar.getInstance().time
        isActive = false
        NotificationUtil().sendNotification(context, name.toString(), "Complete!!")
    }

    fun CheckActive(initialTime: Long, context: Context, today : TimelineDay) : Boolean
    {
        Log.d("petTimelineEvent", "checkactive event " + name.toString())
        //given the starting date of the timeline, is this event currently active
        if(isActive)
        {
            //check complete
            if(today.eventTracker.containsKey(name) && today.eventTracker[name] == "complete") {
                //event is complete after being active
                CompleteEvent(context, today)
            }
            //check if timed out
            val now = GregorianCalendar.getInstance().time
            val difference = now.getTime() - initialTime

            //currently active, but has passed deadline
            if(difference >= getTimeFromString(endTime))
            {
                Timeout(context, today)
            }
        }
        else if(!isComplete)
        {
            //check to see if we should activate it
            val now = GregorianCalendar.getInstance().time
            val difference = now.getTime() - initialTime
            //Event has started, need to activate it
            if(difference >= getTimeFromString(startTime) && difference <= getTimeFromString(endTime))
            {
                Activate(context, today)
            }
        }
        return isActive
    }

    //returns the time converted to milliseconds for math purposes
    fun getTimeFromString(time:String) : Int
    {
        //format days/hours/minutes relative to starting time

        val times = time.split('/')
        if(times.size < 3) return 0 //invalid time

        val days = times[0].toInt()
        val hours = times[1].toInt()
        val minutes = times[2].toInt()

        //convert to milliseconds
        return ((days * 24 + hours) * 60 + minutes) * 60 * 1000
    }

    fun ApplyConsequences(context:Context)
    {
        //eventually timer will check back and see that this event has reached the end but was not fulfilled
        //apply incomplete effects

        if(consequences == null) return

        for(peteffect in consequences)
        {
            peteffect.ApplyEffect(context)
        }
        var petpreference = PetPreference(context)
        Log.d("failed event", "new pet values: ")
        Log.d("pethappiness", petpreference.getPetHappiness().toString())
        Log.d("pethealth", petpreference.getPetHealth().toString())
        Log.d("pethunger", petpreference.getPetHunger().toString())
    }
}