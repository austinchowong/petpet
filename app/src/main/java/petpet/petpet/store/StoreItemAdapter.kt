package petpet.petpet.store

import android.content.Context
import android.net.Uri
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import petpet.petpet.R
import java.util.ArrayList

class StoreItemAdapter(private val dataSet: ArrayList<StoreItem>, private val context: Context):  BaseAdapter() {

    override fun getCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItem(position: Int): Any? = dataSet[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //setup a storeItem
        val cardView :CardView = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false) as CardView
        cardView.findViewById<TextView>(R.id.store_item_name).text = dataSet[position].name
        cardView.findViewById<TextView>(R.id.store_item_description).text = dataSet[position].description

        //set item image
        val uri = Uri.parse("android.resource://" + R::class.java.`package`.name + dataSet[position].image)
        cardView.findViewById<ImageView>(R.id.store_item_image).setImageURI(uri)

        //set price
        val price: String = "$" + dataSet[position].price.toString()
        cardView.findViewById<TextView>(R.id.store_item_price).text = price

        //set purchase button
        cardView.findViewById<Button>(R.id.store_item_buy).setOnClickListener {
            //Increment item count
            StorePreferences(context, dataSet[position].category.name).addOne(dataSet[position].name)

            //TODO:: add the cost to user account

            //Display a Toast to notify user purchase succeed
            Toast.makeText(context, dataSet[position].name + " +1", Toast.LENGTH_SHORT).show()
        }

        return cardView
    }
}