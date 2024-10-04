package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton = findViewById<FrameLayout>(R.id.left_circle)
        backButton.setOnClickListener {
            navigateToHomeActivity()
        }

        val convertCurrencyText = findViewById<TextView>(R.id.convert_currency_text)
        convertCurrencyText.setOnClickListener {
            navigateToCurrencyActivity()
        }

        val bookButton = findViewById<Button>(R.id.book_button)
        bookButton.setOnClickListener {
            navigateToBookingActivity()
        }

    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToCurrencyActivity() {
        val intent = Intent(this, CurrencyActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToBookingActivity() {
        val intent = Intent(this, BookingActivity::class.java)
        startActivity(intent)
        finish()
    }
}