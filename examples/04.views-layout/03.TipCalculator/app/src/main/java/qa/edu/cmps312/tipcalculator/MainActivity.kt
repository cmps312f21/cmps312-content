package qa.edu.cmps312.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

/**
 * Activity that displays a tip calculator.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup a click listener on the calculate button to calculate the tip
        calculateBtn.setOnClickListener { calculateTip() }

        // Setup a checked change listener on the calculate roundUpSwitch to calculate the tip
        roundUpSwitch.setOnCheckedChangeListener { _, _ ->  calculateTip() }

        // Setup a checked change listener on the calculate tipOptionsRg to calculate the tip
        tipOptionsRg.setOnCheckedChangeListener { _, _ ->  calculateTip()  }

        //Afer edit cost is done recalculate the tip
        costOfServiceEt.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                    calculateTip()
                    true
                }
            }
            false
        }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        costOfServiceEt.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /**
     * Calculates the tip based on the user input.
     */
    private fun calculateTip() {
        // Get the decimal value from the cost of service EditText field
        val cost = costOfServiceEt.text.toString().toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Get the tip percentage based on which radio button is selected
        val tipPercentage = when (tipOptionsRg.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.15
            else -> 0.10
        }

        // Calculate the tip
        var tip = tipPercentage * cost

        // If the switch for rounding up the tip toggled on (isChecked is true), then round up the
        // tip. Otherwise do not change the tip value.
        if (roundUpSwitch.isChecked) {
            // Take the ceiling of the current tip, which rounds up to the next integer.
            tip = kotlin.math.ceil(tip)
        }

        // Display the formatted tip value onscreen
        displayTip(tip)
    }

    /**
     * Format the tip amount according to the local currency and display it onscreen.
     * Example would be "Tip Amount: $10.00".
     */
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        tipTv.text = getString(R.string.tip_amount, formattedTip)
    }

    /**
     * Key listener for hiding the keyboard when the "Enter" button is tapped.
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}