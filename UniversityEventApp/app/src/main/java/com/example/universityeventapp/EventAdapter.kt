package com.example.universityeventapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private var events: List<Event>,
    private val onClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val banner: ImageView = view.findViewById(R.id.eventBanner)
        val title: TextView = view.findViewById(R.id.tvEventTitle)
        val date: TextView = view.findViewById(R.id.tvEventDate)
        val seats: TextView = view.findViewById(R.id.tvEventSeats)
        val price: TextView = view.findViewById(R.id.tvEventPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.banner.setImageResource(event.imageRes)
        holder.title.text = event.title
        holder.date.text = "${event.date} | ${event.venue}"
        holder.seats.text = "Seats: ${event.availableSeats}"
        holder.price.text = "৳${event.price.toInt()}"
        holder.view.setOnClickListener { onClick(event) }
    }

    override fun getItemCount() = events.size

    fun updateList(filtered: List<Event>) {
        events = filtered
        notifyDataSetChanged()
    }
}