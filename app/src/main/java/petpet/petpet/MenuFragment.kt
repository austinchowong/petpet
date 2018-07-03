package petpet.petpet


import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ImageButton
import petpet.petpet.settings.SettingsContainerFragment

class MenuFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.fragment_menu, null)

        //setup menu_settings listener
        view.findViewById<ImageButton>(R.id.menu_settings).setOnClickListener {
            SettingsContainerFragment().show(fragmentManager, "SettingsContainerFragment")
        }

        builder.setTitle("Menu")
                .setView(view)
                .setPositiveButton("OK", { dialog, whichButton -> })
        return builder.create()
    }

}
