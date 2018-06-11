package petpet.petpet

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import petpet.petpet.Pet.Pet
import java.io.BufferedReader
import java.io.InputStreamReader

class CreatePetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pet)
        initialize(this)
    }

    fun initialize(context: Context) {
        val reader = BufferedReader(InputStreamReader(context.getAssets().open("petlist.json")))
        val gson = Gson()
        val petList : List<Pet> = gson.fromJson(reader ,object : TypeToken<List<Pet>>() {}.type )
        for (pet in petList) {
            println(pet.name + ": " + pet.description)
        }
    }
}
