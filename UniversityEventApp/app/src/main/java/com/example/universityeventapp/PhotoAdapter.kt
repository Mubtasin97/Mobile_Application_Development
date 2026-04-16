package com.example.universityeventapp

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(private val photos: List<Int>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val img = ImageView(parent.context)
        img.layoutParams = ViewGroup.LayoutParams(200, 200)
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        return PhotoViewHolder(img)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.imageView.setImageResource(photos[position])
    }

    override fun getItemCount() = photos.size
}