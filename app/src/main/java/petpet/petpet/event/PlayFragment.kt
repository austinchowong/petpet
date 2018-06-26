package petpet.petpet.event

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import petpet.petpet.R

public class PlayFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater

        builder.setTitle("Play")
                .setView(inflater.inflate(R.layout.fragment_play, null))
                .setPositiveButton("OK", { dialog, whichButton ->
                    // do something
                })

        return builder.create()
    }
}