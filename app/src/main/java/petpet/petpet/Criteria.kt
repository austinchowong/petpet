package petpet.petpet

/**
 * Created by user on 2018-06-06.
 */
public class Criteria{
    var var1: String? = null;
    var op : String? = null;
    var var2 : String? = null;

    fun IsFulfilled() : Boolean
    {
        //need to be able to convert string names of variables or numbers to comparable values
        return true;
    }
}