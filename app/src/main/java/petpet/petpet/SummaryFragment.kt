package petpet.petpet.event

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.widget.TextView
import petpet.petpet.R
import petpet.petpet.pet.PetPreference

public class SummaryFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater

        val fragmentView = inflater.inflate(R.layout.fragment_summary, null)

        val petpreference = PetPreference(fragmentView.context)

        var nameText = fragmentView.findViewById<TextView>(R.id.pet_name)
        nameText.text = PreferenceManager.getDefaultSharedPreferences(fragmentView.context)
                .getString(resources.getString(R.string.PET_NAME_KEY),"")

        var healthText = fragmentView.findViewById<TextView>(R.id.health_status)
        healthText.text = petpreference.getPetHealth().toString()

        var happinessText = fragmentView.findViewById<TextView>(R.id.happiness_status)
        happinessText.text = petpreference.getPetHappiness().toString()

        var hungerText = fragmentView.findViewById<TextView>(R.id.hunger_status)
        hungerText.text = petpreference.getPetHunger().toString()

        var numWalksText = fragmentView.findViewById<TextView>(R.id.walks_taken)
        numWalksText.text = petpreference.getNumWalks().toString()

        var numStepsText = fragmentView.findViewById<TextView>(R.id.steps_taken)
        numStepsText.text = petpreference.getNumSteps().toString()

        var numVetMissedText = fragmentView.findViewById<TextView>(R.id.vet_missed)
        numVetMissedText.text = petpreference.getNumVaccineShotMissed().toString()

        var moneySpentText = fragmentView.findViewById<TextView>(R.id.money_spent)
        moneySpentText.text = petpreference.getTotalBudget().toString()

        builder.setTitle(resources.getString(R.string.account_summary))
                .setView(fragmentView)
                .setPositiveButton("OK", { dialog, whichButton ->
                    // do something
                })

        return builder.create()
    }
}