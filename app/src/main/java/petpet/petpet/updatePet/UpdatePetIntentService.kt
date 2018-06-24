package petpet.petpet.updatePet

import android.app.IntentService
import android.content.Intent

/*
 * Use to handle update pet task requests in a service on a separate handler thread.
 */
class UpdatePetIntentService  : IntentService("AlarmService") {
        override fun onHandleIntent(p0: Intent?) {
            UpdatePetTask().updatePet(this)
        }
    }