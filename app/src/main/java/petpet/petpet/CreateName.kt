package petpet.petpet
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText

class CreateName : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_name)
    }


    // This function is for confirm button. It requires the EditText can not be empty.
    // If the EditText is empty, an alertDialog will notice user to enter a name.
    // Otherwise, the name will be stored in default share preference file and jump to home page
    fun confirmName(view : View) {
        val name = findViewById<EditText>(R.id.create_name_text).text.toString()
        if (name.isEmpty()) {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage(resources.getString(R.string.empty_name_msg))
                    .setPositiveButton("OK", { dialog, which ->  })
                    .show()
        } else {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            sharedPreferences.edit()
                    .putString(resources.getString(R.string.PET_NAME_KEY), name)
                    .apply()
            val intent = Intent(this, Home::class.java)
            finish()
            startActivity(intent)
        }
    }
}