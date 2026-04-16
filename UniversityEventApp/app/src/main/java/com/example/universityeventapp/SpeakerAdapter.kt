package com.example.universityeventapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SpeakerAdapter(private val speakers: List<String>) :
    RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder>() {

    companion object {
         val SPEAKER_IMAGE = R.drawable.speaker_profile
    }

    inner class SpeakerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val speakerImage: ImageView = view.findViewById(R.id.speakerImage)
        val speakerName: TextView = view.findViewById(R.id.speakerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_speaker, parent, false)
        return SpeakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.speakerName.text = speaker


        holder.speakerImage.setImageResource(SPEAKER_IMAGE)


        holder.speakerImage.clipToOutline = true
    }

    override fun getItemCount() = speakers.size
}