package petpet.petpet

import petpet.petpet.util.Criteria
import java.util.*

/**
 * Created by user on 2018-06-06.
 */
public class PetEvent {

    //if more fields get added that needs to be serialized, they would need to be added
    //to the jsonutil class which is currently used to make the conversion
    var name: String? = null;
    var description: String? = null;
    var startTime : Long = 0;
    var endTime : Long = 0;
    var completedTime : Long = 0;
    var isComplete = false;

    var criteriaList = ArrayList<Criteria>();
    //register the event in global timer
    fun RegisterEvent()
    {

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
        completedTime = GregorianCalendar.getInstance().time.time;
        return true;
    }
}