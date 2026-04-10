package com.example.contactbookapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val contacts = mutableListOf<Contact>()
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactListView: ListView
    private lateinit var contactSearchView: SearchView
    private lateinit var emptyMessageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactListView = findViewById(R.id.contactListView)
        contactSearchView = findViewById(R.id.contactSearchView)
        emptyMessageText = findViewById(R.id.emptyMessageText)

        // First add the starting contacts
        addSomeStartingContacts()

        // Now create the adapter (so it sees all starting contacts)
        contactAdapter = ContactAdapter(this, contacts)
        contactListView.adapter = contactAdapter

        contactListView.emptyView = emptyMessageText

        contactListView.setOnItemClickListener { _, _, position, _ ->
            val contact = contacts[position]
            Toast.makeText(this, "${contact.name}\nPhone: ${contact.phone}\nEmail: ${contact.email}", Toast.LENGTH_LONG).show()
        }

        contactListView.setOnItemLongClickListener { _, _, position, _ ->
            val contact = contacts[position]
            AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Delete ${contact.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    contacts.removeAt(position)
                    contactAdapter.notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addNewContactButton)
            .setOnClickListener { showAddContactDialog() }

        contactSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                contactAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun addSomeStartingContacts() {
        contacts.add(Contact("Nazrul", "01711-234567", "nazrul@gmail.com"))
        contacts.add(Contact("Naim Rahman", "01822-345678", "nrahman@gmail.com"))
        contacts.add(Contact("Raisul", "01933-456789", "raisul@gmail.com"))
        contacts.add(Contact("Samia", "01644-567890", "samia@yahoo.com"))
    }

    private fun showAddContactDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.nameInput)
        val phoneInput = dialogView.findViewById<EditText>(R.id.phoneInput)
        val emailInput = dialogView.findViewById<EditText>(R.id.emailInput)

        AlertDialog.Builder(this)
            .setTitle("Add New Contact")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString().trim()
                val phone = phoneInput.text.toString().trim()
                val email = emailInput.text.toString().trim()
                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    contactAdapter.addNewContact(Contact(name, phone, email))
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}