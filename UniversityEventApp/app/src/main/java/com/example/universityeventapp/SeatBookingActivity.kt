package com.example.universityeventapp

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SeatBookingActivity : AppCompatActivity() {
    private val totalSeats = 48
    private val seatStates = IntArray(totalSeats)
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Book Seats"

        event = intent.getSerializableExtra("event") as Event

        for (i in 0 until totalSeats) {
            if (Math.random() < 0.3) seatStates[i] = 1
        }

        val grid = findViewById<GridView>(R.id.gridSeats)
        val adapter = SeatAdapter(this, seatStates) { updateSummary() }
        grid.adapter = adapter

        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            val selected = seatStates.count { it == 2 }
            if (selected == 0) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Confirm Booking")
                    .setMessage("Book $selected seat(s) for ৳${selected * event.price.toInt()}?")
                    .setPositiveButton("Confirm") { _, _ ->
                        Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    private fun updateSummary() {
        val selected = seatStates.count { it == 2 }
        val total = selected * event.price.toInt()
        findViewById<TextView>(R.id.tvSummary).text =
            "$selected seats selected | Total: ৳$total"
    }

    override fun onSupportNavigateUp(): Boolean {
        handleBack()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        handleBack()
    }

    private fun handleBack() {
        val selected = seatStates.count { it == 2 }
        if (selected > 0) {
            AlertDialog.Builder(this)
                .setTitle("Leave?")
                .setMessage("You have $selected seat(s) selected. Leave without booking?")
                .setPositiveButton("Leave") { _: android.content.DialogInterface, _: Int -> finish() }
                .setNegativeButton("Stay", null)
                .show()
        } else finish()
    }
}