package com.example.myapplication

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityConfirmBinding
import com.example.myapplication.util.UiUtil
import com.example.myapplication.model.BookingModel
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfirmBinding

    private lateinit var adultCount: TextView
    private lateinit var childrenCount: TextView
    private lateinit var reservationDate: TextView
    private lateinit var subtotalValue: TextView
    private lateinit var bookingFeeValue: TextView
    private lateinit var totalValue: TextView
    private lateinit var cancelReservation: TextView
    private lateinit var doneButton: View

    // Initialize Firebase Firestore
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing views
        adultCount = findViewById(R.id.adult_count)
        childrenCount = findViewById(R.id.children_count)
        reservationDate = findViewById(R.id.date)
        subtotalValue = findViewById(R.id.subtotal_value)
        bookingFeeValue = findViewById(R.id.booking_fee_value)
        totalValue = findViewById(R.id.total_value)
        cancelReservation = findViewById(R.id.cancel_reservation)
        doneButton = binding.doneButton

        binding.backIcon.setOnClickListener {
            navigateToBookingActivity()
        }

        // Receiving booking data passed from BookingActivity
        val adults = intent.getIntExtra("adultCount", 0)
        val children = intent.getIntExtra("childrenCount", 0)
        val date = intent.getStringExtra("reservationDate") ?: "N/A"
        val subtotal = intent.getStringExtra("subtotal")
        val bookingFee = intent.getStringExtra("bookingFee")
        val total = intent.getStringExtra("total")

        // Setting received data
        adultCount.text = adults.toString()
        childrenCount.text = children.toString()
        reservationDate.text = date
        subtotalValue.text = subtotal
        bookingFeeValue.text = bookingFee
        totalValue.text = total

        // Cancel reservation
        cancelReservation.setOnClickListener {
            cancelBooking()
        }

        binding.doneButton.setOnClickListener {
            saveBookingDetails()
        }
    }

    private fun navigateToBookingActivity() {
        val intent = Intent(this, BookingActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Booking cancellation
    private fun cancelBooking() {
        val intent = Intent(this, BookingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun saveBookingDetails() {
        // Get the details to save
        val placeName = findViewById<TextView>(R.id.place_name).text.toString()
        val adultCountText = adultCount.text.toString()
        val childrenCountText = childrenCount.text.toString()
        val reservationDateText = reservationDate.text.toString()
        val totalValueText = totalValue.text.toString()

        // Create a new booking model object
        val booking = BookingModel(
            placeName = placeName,
            adultCount = adultCountText,
            childrenCount = childrenCountText,
            reservationDate = reservationDateText,
            totalValue = totalValueText
        )

        // Reference to Firebase Firestore
        val bookingRef = firestore.collection("Bookings").document()

        // Save the complete booking data
        bookingRef.set(booking)
            .addOnSuccessListener {
                UiUtil.showToast(applicationContext, "Booking confirmed!")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                UiUtil.showToast(applicationContext, "Failed to save booking: ${e.message}")
            }
    }
}
