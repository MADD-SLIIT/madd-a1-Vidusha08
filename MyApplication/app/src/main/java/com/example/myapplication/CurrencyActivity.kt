package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.myapplication.databinding.ActivityCurrencyBinding

class CurrencyActivity : AppCompatActivity() {

    lateinit var binding: ActivityCurrencyBinding

    // Exchange rates
    private val exchangeRates = mapOf(
        Pair("USD", mapOf("USD" to 1.0, "EUR" to 1.10, "LKR" to 290.0)),
        Pair("EUR", mapOf("USD" to 0.91, "EUR" to 1.0, "LKR" to 390.0)),
        Pair("LKR", mapOf("USD" to 0.0034, "EUR" to 0.0031, "LKR" to 1.0))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowBack.setOnClickListener {
            navigateToProfileActivity()
        }

        // Initialize UI components
        val amountInput = findViewById<EditText>(R.id.amount)
        val baseCurrencySpinner = findViewById<Spinner>(R.id.base_currency_spinner)
        val targetCurrencySpinner = findViewById<Spinner>(R.id.target_currency)
        val convertButton = findViewById<Button>(R.id.convert_Btn)
        val convertedAmountText = findViewById<TextView>(R.id.converted_amount)

        // Setup spinners with currency options
        val currencies = arrayOf("USD", "EUR", "LKR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        baseCurrencySpinner.adapter = adapter
        targetCurrencySpinner.adapter = adapter

        //  Convert button
        convertButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            if (amount != null) {
                val baseCurrency = baseCurrencySpinner.selectedItem.toString()
                val targetCurrency = targetCurrencySpinner.selectedItem.toString()

                // Perform the conversion
                val convertedAmount = convertCurrency(amount, baseCurrency, targetCurrency)
                convertedAmountText.text = "Converted Amount: $convertedAmount $targetCurrency"
                convertedAmountText.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Currency conversion function
    private fun convertCurrency(amount: Double, baseCurrency: String, targetCurrency: String): Double {
        val baseToTargetRate = exchangeRates[baseCurrency]?.get(targetCurrency) ?: 1.0
        return amount * baseToTargetRate
    }

}