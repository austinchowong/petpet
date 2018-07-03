package petpet.petpet.settings

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import petpet.petpet.R


class SettingsContainerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        //TODO: there is a bug -> duplicate petpet.petpet.settings.SettingsFragment
        builder.setTitle(R.string.settings)
                .setView(inflater.inflate(R.layout.fragment_settings_container, null))

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentById(R.id.setting_container))
                    .commit()
        } catch (e: IllegalStateException) {
            //TODO: when perform language changing, fragment->setting_container will be destroyed twice
        }
    }

}
