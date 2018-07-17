package petpet.petpet.settings

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.Button
import petpet.petpet.R
import petpet.petpet.event.SummaryFragment


class SettingsContainerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        var fragmentView = inflater.inflate(R.layout.fragment_settings_container, null)
        var summaryButton = fragmentView.findViewById<Button>(R.id.summary_button)
        summaryButton.setOnClickListener{
            openSummary()
        }

        //TODO: there is a bug -> duplicate petpet.petpet.settings.SettingsFragment
        builder.setTitle(R.string.settings)
                .setView(fragmentView)

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

    fun openSummary()
    {
        SummaryFragment().show(fragmentManager, "SummaryFragment")
    }

}
