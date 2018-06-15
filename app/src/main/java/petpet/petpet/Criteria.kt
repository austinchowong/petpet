package petpet.petpet
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.*
import kotlin.reflect.KVisibility


/**
 * Created by user on 2018-06-06.
 */
class Criteria{
    @SerializedName("var1")
    var var1: String? = null;
    @SerializedName("op")
    var op : String? = null;
    @SerializedName("var2")
    var var2 : String? = null;

    @SerializedName("fulfilled")
    var fulfilled : Boolean = false

    //do this later
    fun IsFulfilled() : Boolean
    {
        //use reflection to get field values
        var bool1 : Boolean;
        var bool2 : Boolean;
        var int1 : Int;
        var int2 : Int;
        //use switch case for op
        //check if var is a value rather than a field name
        if(var1 == "true")
        {

        }

        //compare vars using op.
        return true;
    }

    fun GetValue(varName : String)
    {
        //check to see if the string is a hardcoded value
        if(varName == "true")
        {
            //return true;
        }

        //reflection
        //pet is the pet class where the values are stored
        /*
        pet::class.memberProperties.foreach{
            if(it.visibility == KVisibility.PUBLIC)
            {
                println(it.getter.call(pet))
                //check if the name is the same as varName, and return value if true
                if(it.name == varName)
                {
                    return it.getter.call(pet)
                }
            }
        }
        */
    }
}