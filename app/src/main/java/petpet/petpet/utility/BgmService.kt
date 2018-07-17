package petpet.petpet.utility

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import petpet.petpet.R


/**
 * Created by user on 2018-07-17.
 */
class BgmService : Service() {
    lateinit var mp: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        mp = MediaPlayer.create(this, R.raw.henesys)
        mp.isLooping = true
    }

    override fun onDestroy() {
        mp.stop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mp.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}