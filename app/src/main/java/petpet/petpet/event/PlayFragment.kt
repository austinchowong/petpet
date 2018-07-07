package petpet.petpet.event

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.GridView
import petpet.petpet.Home
import petpet.petpet.R
import petpet.petpet.store.StoreItem
import petpet.petpet.store.StoreType

class PlayFragment: DialogFragment() {
    private val categories: Array<StoreType> = arrayOf(StoreType.TOY)
    private val eventName :String = "Play"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val gridView : GridView = inflater.inflate(R.layout.fragment_store, null) as GridView
        val items =  java.util.ArrayList<StoreItem>()
        categories.forEach { categories -> items += (activity as Home).store.getValue(categories)?.items }
        gridView.adapter = GeneralAdapter(items, eventName, activity)

        builder.setTitle(eventName)
                .setView(gridView)
                .setPositiveButton("OK", { dialog, whichButton -> })

        return builder.create()
    }
}