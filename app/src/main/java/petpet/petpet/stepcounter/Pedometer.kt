package petpet.petpet.stepcounter

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.pet.PetPreference
import petpet.petpet.timeline.Timeline
import petpet.petpet.timeline.TimelineUtil
import java.io.*
import android.hardware.SensorManager
import android.widget.TextView
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import petpet.petpet.R
import android.content.Context.SENSOR_SERVICE
import petpet.petpet.event.PetAnimator

object Pedometer{

    var numSteps = 0;
    var isWalking = false;
    var sensorRegistered = false;

    fun StartTracking(context : Context) : Boolean
    {
        if(isWalking)
        {
            Log.d("Pedometer", "already tracking")
            return false
        }

        Log.d("Pedometer", "Start Tracking")

        numSteps = 0
        isWalking = true

        PetAnimator.notifyObservers("walking")
        return true
    }

    fun StopTracking(context: Context) : Boolean
    {
        if(!isWalking)
        {
            Log.d("Pedometer", "didn't start walking, can't stop")
            return false
        }

        Log.d("Pedometer", "Stop Tracking")
        //only walks of a minimum number of steps should be counted as a walk
        if(numSteps > context.resources.getInteger(R.integer.min_step_count))
        {
            PetPreference(context).addWalk()
            PetPreference(context).changePetHealth((numSteps / 15).toLong());
            PetPreference(context).changePetHappiness((numSteps / 10).toLong());
            PetPreference(context).changePetHunger(-(numSteps / 20).toLong());
        }

        isWalking = false
        //add steps to total num steps for today
        val timeline = TimelineUtil().getCurrentTimeline(context)
        var today = timeline.GetCurrentTimelineDay()
        today.stepsTaken += numSteps
        PetPreference(context).addSteps(numSteps.toLong())

        //check if daily walking goal is complete
        if(today.stepsTaken >= context.resources.getInteger(R.integer.daily_step_count))
        {
            Log.d("Pedometer", "daily walk complete at " + today.stepsTaken.toString() + " steps")
            //if event was registered already, tell timeline to check after completing it
            if(today.eventTracker.containsKey("walk") && today.eventTracker["walk"] == "inprogress")
            {
                today.eventTracker["walk"] = "complete"
                timeline.CheckTimeline(context)
            }
            //otherwise just say it's complete and eventually timeline will see it when event starts
            else
            {
                today.eventTracker["walk"] = "complete"
            }
        }

        TimelineUtil().setCurrentTimeline(context, timeline)
        PetAnimator.notifyObservers("idle")
        return true
    }
}