package petpet.petpet.event

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import petpet.petpet.R

class FeedFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setTitle("Feed")
                .setView(inflater.inflate(R.layout.fragment_feed, null))
                .setPositiveButton("OK", { dialog, whichButton ->
                    // do something
                })

        return builder.create()
    }
}