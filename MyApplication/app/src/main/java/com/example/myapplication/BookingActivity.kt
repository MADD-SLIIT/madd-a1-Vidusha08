package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityBookingBinding
import java.util.*

class BookingActivity : AppCompatActivity() {

    lateinit var binding: ActivityBookingBinding

    private var adultCount = 0
    private var childrenCount = 0
    private val adultPrice = 2.00
    private val childrenPrice = 1.00
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIcon.setOnClickListener {
            navigateToDetailsActivity()
        }

        // Date picker button
        val datePickerButton: Button = findViewById(R.id.date_picker_actions)
        val selectedDateText: TextView = findViewById(R.id.date_picker_actions)

        val plusAdultsButton: Button = findViewById(R.id.plus_adults)
        val minusAdultsButton: Button = findViewById(R.id.minus_adults)
        val adultCountText: TextView = findViewById(R.id.adult_count)

        val plusChildrenButton: Button = findViewById(R.id.plus_children)
        val minusChildrenButton: Button = findViewById(R.id.minus_children)
        val childrenCountText: TextView = findViewById(R.id.children_count)

        val adultsPriceLabel: TextView = findViewById(R.id.adults_price_label)
        val adultsPriceValue: TextView = findViewById(R.id.adults_price_value)

        val childrenPriceLabel: TextView = findViewById(R.id.children_price_label)
        val childrenPriceValue: TextView = findViewById(R.id.children_price_value)

        val totalPriceValue: TextView = findViewById(R.id.total_price_value)

        // Initial update
        updatePriceDetails(adultsPriceLabel, adultsPriceValue, childrenPriceLabel, childrenPriceValue, totalPriceValue)

        // Set date picker dialog
        datePickerButton.setOnClickListener {
            showDatePickerDialog(selectedDateText)
        }
        // Adults count increase
        plusAdultsButton.setOnClickListener {
            adultCount++
            adultCountText.text = adultCount.toString()
            updatePriceDetails(adultsPriceLabel, adultsPriceValue, childrenPriceLabel, childrenPriceValue, totalPriceValue)
        }

        // Adults count decrease
        minusAdultsButton.setOnClickListener {
            if (adultCount > 0) {
                adultCount--
                adultCountText.text = adultCount.toString()
                updatePriceDetails(adultsPriceLabel, adultsPriceValue, childrenPriceLabel, childrenPriceValue, totalPriceValue)
            }
        }


        // Children count increase
        plusChildrenButton.setOnClickListener {
            childrenCount++
            childrenCountText.text = childrenCount.toString()
            updatePriceDetails(adultsPriceLabel, adultsPriceValue, childrenPriceLabel, childrenPriceValue, totalPriceValue)
        }

        // Children count decrease
        minusChildrenButton.setOnClickListener {
            if (childrenCount > 0) {
                childrenCount--
                childrenCountText.text = childrenCount.toString()
                updatePriceDetails(adultsPriceLabel, adultsPriceValue, childrenPriceLabel, childrenPriceValue, totalPriceValue)
            }
        }

        // Proceed to Payment button
        val proceedToPaymentButton: Button = findViewById(R.id.proceed_to_payment)
        proceedToPaymentButton.setOnClickListener {
            navigateToConfirmActivity(totalPriceValue.text.toString())
        }

    }

    private fun navigateToDetailsActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToConfirmActivity(subtotal: String) {
        val intent = Intent(this, ConfirmActivity::class.java)
        intent.putExtra("adultCount", adultCount)
        intent.putExtra("childrenCount", childrenCount)
        intent.putExtra("reservationDate", selectedDate)
        intent.putExtra("subtotal", subtotal)
        intent.putExtra("bookingFee", "0.25")
        val total = (subtotal.replace("$", "").toDouble() + 0.25).toString() // Adding booking fee
        intent.putExtra("total", "$$total")
        startActivity(intent)
        finish()
    }

    private fun showDatePickerDialog(selectedDateText: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val monthDisplay = selectedMonth + 1 // Month is 0-indexed
            selectedDate = String.format("%02d/%02d/%04d", selectedDay, monthDisplay, selectedYear) // Update selectedDate
            selectedDateText.text = selectedDate // Display selected date in the TextView
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun updatePriceDetails(
        adultsPriceLabel: TextView,
        adultsPriceValue: TextView,
        childrenPriceLabel: TextView,
        childrenPriceValue: TextView,
        totalPriceValue: TextView
    ) {
        val adultTotal = adultCount * adultPrice
        val childrenTotal = childrenCount * childrenPrice
        val totalPrice = adultTotal + childrenTotal

        adultsPriceLabel.text = "$$adultPrice * $adultCount Adults"
        adultsPriceValue.text = "$${String.format("%.2f", adultTotal)}"
        childrenPriceLabel.text = "$$childrenPrice * $childrenCount Children"
        childrenPriceValue.text = "$${String.format("%.2f", childrenTotal)}"
        totalPriceValue.text = "$${String.format("%.2f", totalPrice)}"
    }
}