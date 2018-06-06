package petpet.petpet
import java.util.*
import kotlin.collections.*;
import kotlin.collections.ArrayList

/**
 * Created by user on 2018-06-06.
 */
class Timeline {
    //time of when the timeline was created
    public var startDate: Long = 0;
    public var timeline = ArrayList<PetEvent>();

    //could either get a string that says which timeline to get from json,
    //or just get the json and convert it to the class
    //or could use the jsonutil and only construct the class from that
    class Timeline()
    {
        //startDate = GregorianCalendar.getInstance().getTime();
    }

    fun RegisterTimeline()
    {
        for(event in timeline)
        {
            //may need to pass in the timer here?
            event.RegisterEvent();
        }
    }
}