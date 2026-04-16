package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val event = intent.getSerializableExtra("event") as Event
        title = event.title

        findViewById<ImageView>(R.id.detailImage).setImageResource(event.imageRes)
        findViewById<TextView>(R.id.tvDetailTitle).text = event.title
        findViewById<TextView>(R.id.tvDetailDateTime).text = "📅 ${event.date}  🕐 ${event.time}"
        findViewById<TextView>(R.id.tvDetailVenue).text = "📍 ${event.venue}"
        findViewById<TextView>(R.id.tvDetailDescription).text = event.description

        val photos = listOf(event.imageRes, event.imageRes, event.imageRes)
        val photoRecycler = findViewById<RecyclerView>(R.id.recyclerPhotos)
        photoRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoRecycler.adapter = PhotoAdapter(photos)


        val speakers = event.speakers
        val speakerRecycler = findViewById<RecyclerView>(R.id.recyclerSpeakers)
        speakerRecycler.layoutManager = LinearLayoutManager(this)
        speakerRecycler.adapter = SpeakerAdapter(speakers)

        if (speakers.isEmpty()) {
            findViewById<TextView>(R.id.tvSpeakersTitle)?.visibility = View.GONE
            speakerRecycler.visibility = View.GONE
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val intent = Intent(this, SeatBookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}