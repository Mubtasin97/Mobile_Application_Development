package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            startActivity(Intent(this, EventsListActivity::class.java))
        }

        findViewById<Button>(R.id.btnBookings).setOnClickListener {
            Toast.makeText(this, "My Bookings - Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnNotifications).setOnClickListener {
            Toast.makeText(this, "Notifications - Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            Toast.makeText(this, "Profile - Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnRegisterNow).setOnClickListener {
            startActivity(Intent(this, EventsListActivity::class.java))
        }
    }
}