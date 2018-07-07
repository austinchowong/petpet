package petpet.petpet.event

import android.content.Context
import android.net.Uri
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import petpet.petpet.R
import petpet.petpet.store.ItemPreferences
import petpet.petpet.store.StoreItem
import java.util.ArrayList

class GeneralAdapter(private val dataSet: ArrayList<StoreItem>, private val context: Context):  BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val name = dataSet[position].name
        val category = dataSet[position].category.name

        //setup a storeItem
        val cardView : CardView = LayoutInflater.from(context).inflate(R.layout.general_item, parent, false) as CardView
        cardView.findViewById<TextView>(R.id.general_item_name).text = name
        cardView.findViewById<TextView>(R.id.general_item_description).text = dataSet[position].description

        //set item image
        val uri = Uri.parse("android.resource://" + R::class.java.`package`.name + dataSet[position].image)
        cardView.findViewById<ImageView>(R.id.general_item_image).setImageURI(uri)

        //set count
        val count: Int = ItemPreferences(context, category).getInt(name)
        cardView.findViewById<TextView>(R.id.general_item_count).text = count.toString()

        //set purchase button
        val button = cardView.findViewById<Button>(R.id.general_item_feed)
        button.isClickable = ItemPreferences(context, name).getInt(name) > 0
//        button.visibility = if (ItemPreferences(context, name).getInt(name) > 0) View.VISIBLE else View.GONE

        button.setOnClickListener {
            //Increment item count
            val result = ItemPreferences(context, category).removeOne(name)

            //Display a Toast to notify user purchase succeed
            if(result >= 0) {
                Toast.makeText(context, dataSet[position].name + " -1", Toast.LENGTH_SHORT).show()
            }

            //TODO:update progress bar

            this.notifyDataSetChanged()
        }

        return cardView
    }

    override fun getItem(position: Int): Any? = dataSet[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dataSet.size

}