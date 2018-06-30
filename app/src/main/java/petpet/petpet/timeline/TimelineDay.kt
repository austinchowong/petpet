package petpet.petpet.timeline

import com.google.gson.annotations.SerializedName

/**
 * Created by user on 2018-06-29.
 */
class TimelineDay {
    @SerializedName("dayEvents")
    var dayEvents = ArrayList<TimelineEvent>()

    @SerializedName("eventTracker")
    var eventTracker = mutableMapOf<String,String>();
}