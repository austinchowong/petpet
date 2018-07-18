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
        if (gif != "idle" && gif != "walking" ) {
            timer.schedule(timerTask{ notifyObservers("idle")}, 10000)
        }
    }

}