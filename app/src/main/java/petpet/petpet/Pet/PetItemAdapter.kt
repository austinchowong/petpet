package petpet.petpet.Pet

import android.content.Context
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import petpet.petpet.R

class PetItemAdapter(private val dataSet: Array<Pet>, private val context: Context) : RecyclerView.Adapter<PetItemAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.tag = dataSet[position].id
        holder.cardView.findViewById<TextView>(R.id.pet_item_name).text =  dataSet[position].breed
        holder.cardView.findViewById<TextView>(R.id.pet_item_description).text =  dataSet[position].description
        val uri = Uri.parse("android.resource://" + R::class.java.`package`.name + "/" + dataSet[position].image)
        holder.cardView.findViewById<ImageView>(R.id.pet_item_img).setImageURI(uri)//setImageResource(R.mipmap.piggy) //setImageURI(uri)
    }

    override fun getItemCount(): Int = dataSet.size
}