package petpet.petpet.store

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.GridView
import petpet.petpet.Home
import petpet.petpet.R
import java.util.*


class StoreFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val gridView : GridView = inflater.inflate(R.layout.fragment_store, null) as GridView
        val storeItems  = ArrayList<StoreItem>()
        (activity as Home).store.values.forEach { category -> storeItems += category.items }
        gridView.adapter = StoreItemAdapter(storeItems, activity)

        builder.setTitle("Store")
                .setView(gridView)
                .setPositiveButton("OK", { dialog, whichButton -> })

        return builder.create()    }
}
