package petpet.petpet.timeline

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import java.io.*
import java.sql.Time

/**
 * Created by user on 2018-06-30.
 */
class TimelineUtil {
    fun getCurrentTimeline(context:Context) : Timeline
    {
        val petpreference = PetPreference(context)
        val f = File(context.filesDir.path + "/" +petpreference.prefTimelineFileName)
        if(f.exists()) {
            val reader = BufferedReader(FileReader(f))
            val gson = Gson()
            val timeline: Timeline = gson.fromJson(reader, object : TypeToken<Timeline>() {}.type)
            return timeline
        }
        else return Timeline()
    }

    fun setCurrentTimeline(context: Context, timeline : Timeline)
    {
        val petpreference = PetPreference(context)
        val f = File(context.filesDir.path + "/" +petpreference.prefTimelineFileName)
        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(f)))
        val jsonString = Gson().toJson(timeline);
        writer.write(jsonString);
        writer.close();
    }
}