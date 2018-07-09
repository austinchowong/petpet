package petpet.petpet.event

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import petpet.petpet.R
import petpet.petpet.stepcounter.Pedometer
import android.hardware.SensorManager
import android.view.View
import petpet.petpet.stepcounter.StepListener
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorEvent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import petpet.petpet.stepcounter.StepDetector

public class WalkFragment: DialogFragment(), SensorEventListener, StepListener {

    lateinit var simpleStepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var accel: Sensor
    lateinit var stepCountDisplay : TextView

    /**
     * Problems :
     * -fragment ui doesnt refresh dynamically
     *
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater

        val fragmentView = inflater.inflate(R.layout.fragment_walk, null)

        updateView(fragmentView)

        builder.setTitle(resources.getString(R.string.walk))
                .setView(fragmentView)
                .setPositiveButton("OK", { dialog, whichButton ->
                    // do something
                })

        return builder.create()
    }

    fun updateView(view : View)
    {
        var stopButton : Button = view.findViewById(R.id.Stop)
        var startButton : Button = view.findViewById(R.id.Start)

        sensorManager = view.context.getSystemService(SENSOR_SERVICE) as SensorManager
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        simpleStepDetector = StepDetector()
        simpleStepDetector.registerListener(this)

        setVisibility(view)

        startButton.setOnClickListener{
            TryStartTracking()
            updateView(view)
        }

        stopButton.setOnClickListener{
            TryStopTracking()
            updateView(view)
        }
    }

    fun setVisibility(view: View) {
        var displayText = view.findViewById<TextView>(R.id.displayText)
        stepCountDisplay = view.findViewById<TextView>(R.id.step_count_display)
        var stopButton: Button = view.findViewById(R.id.Stop)
        var startButton: Button = view.findViewById(R.id.Start)
        if (Pedometer.isWalking) {
            displayText.text = "Finish your walk?"
            stepCountDisplay.visibility = View.VISIBLE
            stopButton.isEnabled = true
            stopButton.visibility = View.VISIBLE
            startButton.isEnabled = false
            startButton.visibility = View.INVISIBLE
        } else {
            displayText.text = "Take your dog out for a walk?"
            stepCountDisplay.visibility = View.INVISIBLE
            startButton.isEnabled = true
            startButton.visibility = View.VISIBLE
            stopButton.isEnabled = false
            stopButton.visibility = View.INVISIBLE
        }
        stepCountDisplay.text = Pedometer.numSteps.toString()
    }

    fun TryStartTracking()
    {
        Log.d("walkfrag", "press start")
        if(stepCountDisplay != null && Pedometer.StartTracking(stepCountDisplay.context) && !Pedometer.sensorRegistered)
        {
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST)
            Pedometer.sensorRegistered = true
        }
    }

    fun TryStopTracking()
    {
        Log.d("walkfrag", "press stop")
        if(stepCountDisplay != null && Pedometer.StopTracking(stepCountDisplay.context) && Pedometer.sensorRegistered)
        {
            sensorManager.unregisterListener(this)
        }
    }

    override fun step(timeNs: Long) {
        if(Pedometer.isWalking) {
            Pedometer.numSteps++
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector?.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }
}