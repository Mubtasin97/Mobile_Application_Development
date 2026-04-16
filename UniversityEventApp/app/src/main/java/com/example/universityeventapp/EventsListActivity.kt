package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsListActivity : AppCompatActivity() {
    private lateinit var adapter: EventAdapter
    private val allEvents = SampleData.getEvents()
    private val categories = listOf("All", "Tech", "Sports", "Cultural", "Academic", "Social")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Events"

        val recycler = findViewById<RecyclerView>(R.id.recyclerEvents)
        adapter = EventAdapter(allEvents) { event ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        setupCategoryChips()

        findViewById<SearchView>(R.id.searchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(text: String?): Boolean {
                    adapter.updateList(allEvents.filter {
                        it.title.contains(text ?: "", ignoreCase = true)
                    })
                    return true
                }
            })
    }

    private fun setupCategoryChips() {
        val chipGroup = findViewById<LinearLayout>(R.id.chipGroup)
        categories.forEach { cat ->
            val chip = Button(this)
            chip.text = cat
            chip.setOnClickListener {
                adapter.updateList(
                    if (cat == "All") allEvents
                    else allEvents.filter { it.category == cat }
                )
            }
            chipGroup.addView(chip)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}