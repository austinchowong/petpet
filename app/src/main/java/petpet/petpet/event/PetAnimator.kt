package petpet.petpet.event

import java.util.*
import kotlin.concurrent.timerTask

object PetAnimator {

    private var observers = ArrayList<Observer>()

    fun attachObserver(obs : Observer){
        observers.add(obs)
    }

    fun notifyObservers(gif : String) {
        for (o in observers) o.update(gif)
        val timer = Timer()
        if (gif != "pixelcorgiidle" && gif != "pixelcorgiwalking" ) {
            timer.schedule(timerTask{ notifyObservers("pixelcorgiidle")}, 10000)
        }
    }

}