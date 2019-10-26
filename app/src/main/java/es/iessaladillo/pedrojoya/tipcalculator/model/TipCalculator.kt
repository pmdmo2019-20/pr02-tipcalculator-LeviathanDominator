package es.iessaladillo.pedrojoya.tipcalculator.model

import es.iessaladillo.pedrojoya.tipcalculator.R
import kotlin.math.ceil


// TipCalculator class. Its constructor receives bill, percentage and diners

class TipCalculator(var bill: Float, var percentage: Float, var diners: Int) {

    init {
        require(bill >= 0) { R.string.invalidBill }
        require(percentage >= 0) { R.string.invalidPercentage }
        require(diners >= 0) { R.string.invalidDiners }
    }

    fun calculateTip(): Float {
        return bill * percentage / 100
    }

    fun calculateTotal(): Float {
        return bill + calculateTip()
    }

    fun calculatePerDiner(): Float {
        return calculateTotal() / diners
    }

    fun calculatePerDinerRounded(): Float {
        val perDiner = calculateTotal() / diners
        val perDinerRounded = ceil((perDiner).toDouble()).toFloat()
        return if (isDecimalZero(perDiner) && perDiner != perDinerRounded) {
            // If perDiner ends with .00 and is not equal to perDinerRounded, returns perDinerRounded - 1.
            // This is because the app doesn't show the exact float value, but it can be slightly different.
            // Example: 4.00 and 4.004. It should not round up to 5.00 because the user cannot see that
            // the value is really 4.004, and instead sees 4.00.
            perDinerRounded - 1
        } else {
            perDinerRounded
        }
    }

    // This function checks if a float value has .00 as decimals.
    private fun isDecimalZero(num: Float): Boolean {
        // Number is turned into a string with six decimals to prevent rounding, and the comma is replaced with a dot.
        val numString = String.format("%.6f", num).replace(",", ".")
        val dotPosition = numString.indexOf(".", 0, false)
        val subString = numString.substring(dotPosition + 1, dotPosition + 3)
        return subString == "00"
    }

}