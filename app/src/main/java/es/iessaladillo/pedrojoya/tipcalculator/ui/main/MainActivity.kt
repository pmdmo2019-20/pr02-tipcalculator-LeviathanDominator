package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import es.iessaladillo.pedrojoya.tipcalculator.R
import es.iessaladillo.pedrojoya.tipcalculator.model.TipCalculator
import kotlin.CharSequence as CharSequence1

class MainActivity : AppCompatActivity() {

    private var txtBill: TextView? = null
    private var txtPercentage: TextView? = null
    private var txtTip: TextView? = null
    private var txtTotal: TextView? = null
    private var txtDiners: TextView? = null
    private var txtPerDiner: TextView? = null
    private var txtPerDinerRounded: TextView? = null
    private var btnResetTip: Button? = null
    private var btnResetDiners: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setFocusListeners()
    }

    private fun setupViews() {
        txtBill = ActivityCompat.requireViewById(this, R.id.txtBill)
        txtBill!!.addTextChangedListener { validateBill() }
        txtPercentage = ActivityCompat.requireViewById(this, R.id.txtPercentage)
        txtPercentage!!.addTextChangedListener { validatePercentage() }
        txtTip = ActivityCompat.requireViewById(this, R.id.txtTip)
        txtTotal = ActivityCompat.requireViewById(this, R.id.txtTotal)
        txtDiners = ActivityCompat.requireViewById(this, R.id.txtDiners)
        txtDiners!!.addTextChangedListener { validateDiners() }
        txtPerDiner = ActivityCompat.requireViewById(this, R.id.txtPerDiner)
        txtPerDinerRounded = ActivityCompat.requireViewById(this, R.id.txtPerDinerRounded)
        btnResetTip = ActivityCompat.requireViewById(this, R.id.btnResetTip)
        btnResetTip!!.setOnClickListener { resetTipButtonListener() }
        btnResetDiners = ActivityCompat.requireViewById(this, R.id.btnResetDiners)
        btnResetDiners!!.setOnClickListener { resetDinersButtonListener() }
    }

    private fun setFocusListeners() {
        txtBill!!.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            formatText(
                txtBill!!,
                hasFocus
            )
        }
        txtPercentage!!.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            formatText(
                txtPercentage!!,
                hasFocus
            )
        }
        txtDiners!!.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            removeZeros(
                txtDiners!!,
                hasFocus
            )
        }
    }

    private fun removeZeros(textView: TextView, hasFocus: Boolean) {
        if (!hasFocus) {
            textView.text = textView.text.toString().toInt().toString()
        }
    }

    private fun formatText(textView: TextView, hasFocus: Boolean) {
        if (!hasFocus) {
            textView.text = formatToTwoDigits(textView.text.toString().toFloat())
        }
    }

    private fun validateBill() {
        val bill = txtBill!!.text.toString()
        if (bill.isEmpty() || bill == getText(R.string.dot)) {
            txtBill!!.setText(R.string.txtDefault)
            resetFocus(txtBill!!)
        } else {
            formatToTwoDigits(bill.toFloat())
            updateData()
        }
    }

    private fun validatePercentage() {
        val percentage = txtPercentage!!.text.toString()
        if (percentage.isEmpty() || percentage == getText(R.string.dot)) {
            txtPercentage!!.setText(R.string.txtPercentageDefault)
            resetFocus(txtPercentage!!)
        } else {
            if (percentage.toFloat() > this.resources.getInteger(R.integer.oneHundred)) {
                txtPercentage!!.text = getString(R.string.oneHundred)
            }
            formatToTwoDigits(percentage.toFloat())
            updateData()
        }
    }

    private fun validateDiners() {
        val diners = txtDiners!!.text.toString()
        if (diners.isEmpty() || diners == getString(R.string.zero)) {
            txtDiners!!.setText(R.string.txtDinersDefault)
            Toast.makeText(this, getString(R.string.txtDinersInvalid), Toast.LENGTH_SHORT).show()
            resetFocus(txtDiners!!)
        } else {
            if (diners.contains(getString(R.string.dot))) {
                txtDiners!!.text = diners.replace(getString(R.string.dot), "", true)
            }
            updateData()
        }
    }

    private fun resetFocus(textView: TextView) {
        textView.clearFocus()
        textView.requestFocus()
    }

    private fun updateData() {
        val tipCalculator =
            TipCalculator(txtToFloat(txtBill), txtToFloat(txtPercentage), txtToInt(txtDiners))
        txtTip?.text = formatToTwoDigits(tipCalculator.calculateTip())
        txtTotal?.text = formatToTwoDigits(tipCalculator.calculateTotal())
        txtPerDiner?.text = formatToTwoDigits(tipCalculator.calculatePerDiner())
        txtPerDinerRounded?.text = formatToTwoDigits(tipCalculator.calculatePerDinerRounded())
    }

    private fun formatToTwoDigits(float: Float): CharSequence1? {
        val formatted: String = getString(R.string.floatFormat).format(float)
        return formatted.replace(getString(R.string.comma), getString(R.string.dot), true)
    }

    private fun txtToInt(textView: TextView?): Int {
        return textView!!.text.toString().toInt()
    }

    private fun txtToFloat(textView: TextView?): Float {
        return textView!!.text.toString().toFloat()
    }

    private fun resetDinersButtonListener() {
        txtDiners?.setText(R.string.txtDinersDefault)
        resetFocus(txtDiners!!)
    }

    private fun resetTipButtonListener() {
        txtBill?.setText(R.string.txtDefault)
        txtPercentage?.setText(R.string.txtPercentageDefault)
        resetFocus(txtBill!!)
    }

}
