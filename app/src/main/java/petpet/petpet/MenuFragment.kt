package petpet.petpet


import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ImageButton
import petpet.petpet.event.FeedFragment
import petpet.petpet.event.PlayFragment
import petpet.petpet.event.WalkFragment
import petpet.petpet.settings.SettingsContainerFragment
import petpet.petpet.store.StoreFragment

class MenuFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.fragment_menu, null)

        // menu_settings listener
        view.findViewById<ImageButton>(R.id.menu_settings).setOnClickListener {
            SettingsContainerFragment().show(fragmentManager, "SettingsContainerFragment")
        }

        // menu_store listener
        view.findViewById<ImageButton>(R.id.menu_store).setOnClickListener{
            StoreFragment().show(fragmentManager,"StoreFragment")
        }

        // menu_feed listener
        view.findViewById<ImageButton>(R.id.menu_feed).setOnClickListener{
            FeedFragment().show(fragmentManager, "FeedFragment")
        }

        // menu_feed listener
        view.findViewById<ImageButton>(R.id.menu_play).setOnClickListener{
            PlayFragment().show(fragmentManager, "PlayFragment")
        }

        val vet_button = view.findViewById<ImageButton>(R.id.menu_vet)
        vet_button.setOnClickListener {
            Vet().VetVisit(view.context)

        }

        val walk_button = view.findViewById<ImageButton>(R.id.menu_walk)
        walk_button.setOnClickListener {
            val dialog = WalkFragment()
            dialog.show(fragmentManager, "WalkFragment")
        }

        builder.setTitle("Menu")
                .setView(view)
                .setPositiveButton("OK", { dialog, whichButton -> })
        return builder.create()
    }

}
