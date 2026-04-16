package com.example.universityeventapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView

class SeatAdapter(
    private val context: Context,
    private val seatStates: IntArray,
    private val onSelectionChanged: () -> Unit
) : BaseAdapter() {

    override fun getCount() = seatStates.size
    override fun getItem(pos: Int) = seatStates[pos]
    override fun getItemId(pos: Int) = pos.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View(context)
        val size = ((parent?.width ?: 300) / 6) - 10

        view.layoutParams = AbsListView.LayoutParams(size, size)

        view.setBackgroundColor(
            when (seatStates[pos]) {
                1 -> 0xFFF44336.toInt()
                2 -> 0xFF2196F3.toInt()
                else -> 0xFF4CAF50.toInt()
            }
        )
        view.setOnClickListener {
            if (seatStates[pos] == 1) return@setOnClickListener
            seatStates[pos] = if (seatStates[pos] == 2) 0 else 2
            notifyDataSetChanged()
            onSelectionChanged()
        }
        return view
    }
}