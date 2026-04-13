package com.example.photogalleryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class PhotoAdapter(
    private val context: Context,
    private var photos: MutableList<Photo>
) : BaseAdapter() {

    private var isSelectionMode = false

    fun setSelectionMode(mode: Boolean) {
        isSelectionMode = mode
        notifyDataSetChanged()
    }

    fun getSelectedPhotos(): List<Photo> = photos.filter { it.isSelected }

    fun deleteSelected() {
        photos.removeAll { it.isSelected }
        notifyDataSetChanged()
    }

    override fun getCount() = photos.size
    override fun getItem(position: Int) = photos[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.photoImage),
                view.findViewById(R.id.photoTitle),
                view.findViewById(R.id.photoCheckBox)
            )
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val photo = photos[position]
        holder.image.setImageResource(photo.resourceId)
        holder.title.text = photo.title
        holder.checkBox.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        holder.checkBox.isChecked = photo.isSelected

        return view!!
    }

    private class ViewHolder(
        val image: ImageView,
        val title: TextView,
        val checkBox: CheckBox
    )
}