package com.example.contactbookapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>) :
    ArrayAdapter<Contact>(context, 0, contactList) {

    private val fullContactList = ArrayList(contactList)

    private val colors = listOf(
        Color.parseColor("#FF5722"), Color.parseColor("#4CAF50"),
        Color.parseColor("#2196F3"), Color.parseColor("#9C27B0"),
        Color.parseColor("#F44336")
    )

    fun addNewContact(contact: Contact) {
        contactList.add(contact)
        fullContactList.add(contact)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder()
            holder.initialView = view.findViewById(R.id.contactInitial)
            holder.nameView = view.findViewById(R.id.contactName)
            holder.phoneView = view.findViewById(R.id.contactPhone)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val contact = getItem(position)!!
        holder.nameView.text = contact.name
        holder.phoneView.text = contact.phone
        holder.initialView.text = contact.initial
        holder.initialView.setBackgroundColor(colors[position % colors.size])

        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtered = mutableListOf<Contact>()
                if (constraint.isNullOrEmpty()) {
                    filtered.addAll(fullContactList)
                } else {
                    val query = constraint.toString().lowercase().trim()
                    for (contact in fullContactList) {
                        if (contact.name.lowercase().contains(query)) {
                            filtered.add(contact)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filtered
                results.count = filtered.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results != null && results.values != null) {
                    addAll(results.values as List<Contact>)
                }
                notifyDataSetChanged()
            }
        }
    }

    private class ViewHolder {
        lateinit var initialView: TextView
        lateinit var nameView: TextView
        lateinit var phoneView: TextView
    }
}