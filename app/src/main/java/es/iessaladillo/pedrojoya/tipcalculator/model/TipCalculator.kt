package es.iessaladillo.pedrojoya.tipcalculator.model

import kotlin.math.roundToInt


// TipCalculator class. Its constructor receives bill, percentage and diners

class TipCalculator(private var bill: Float, private var percentage: Float, private var diners: Int) {

    init {
        require(bill >= 0) { "Bill must be positive." }
        require(percentage >= 0) { "Percentage must be positive." }
        require(diners >= 0) { "Diners must be positive." }
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
        val perDiner = calculatePerDiner()
        val result = perDiner.roundToInt().toFloat()
        // We check if perDiner shows two zeros as decimals to prevent rounding up when it shouldn't.
        return if (result < perDiner && !areDecimalsZero(perDiner)) {
            result + 1
        } else {
            result
        }
    }

    // This function checks if a float value has .00 as decimals.
    private fun areDecimalsZero(num: Float): Boolean {
        val numString = String.format("%.2f", num).replace(",", ".")
        val dotPosition = numString.indexOf(".", 0, false)
        val subString = numString.substring(dotPosition + 1, dotPosition + 3)
        return subString == "00"
    }

}