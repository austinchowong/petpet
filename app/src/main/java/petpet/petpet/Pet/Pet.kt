package petpet.petpet.Pet

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import petpet.petpet.CreatePetActivity
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader

class Pet {
    private var instance: Pet? = null

    @SerializedName("id")
    val id : Long = 0

    @SerializedName("breed")
    val breed : String = ""

    @SerializedName("description")
    val description : String = ""

    @SerializedName("image")
    val image : String = ""
}