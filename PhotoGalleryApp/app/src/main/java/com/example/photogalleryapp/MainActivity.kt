package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PhotoAdapter
    private lateinit var selectionToolbar: LinearLayout
    private lateinit var selectedCountText: TextView
    private val allPhotos = mutableListOf<Photo>()
    private var isSelectionMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.photoGridView)
        selectionToolbar = findViewById(R.id.selectionToolbar)
        selectedCountText = findViewById(R.id.selectedCountText)

        loadSamplePhotos()

        adapter = PhotoAdapter(this, allPhotos)
        gridView.adapter = adapter

        setupCategoryTabs()
        setupGridClick()
        setupFab()
    }

    private fun loadSamplePhotos() {
        allPhotos.add(Photo(1, R.drawable.photo1, "Mountain View", "Nature"))
        allPhotos.add(Photo(2, R.drawable.photo2, "Forest Path", "Nature"))
        allPhotos.add(Photo(3, R.drawable.photo3, "City Skyline", "City"))
        allPhotos.add(Photo(4, R.drawable.photo4, "Busy Street", "City"))
        allPhotos.add(Photo(5, R.drawable.photo5, "Tiger", "Animals"))
        allPhotos.add(Photo(6, R.drawable.photo6, "Cat", "Animals"))
        allPhotos.add(Photo(7, R.drawable.photo7, "Pizza", "Food"))
        allPhotos.add(Photo(8, R.drawable.photo8, "Burger", "Food"))
        allPhotos.add(Photo(9, R.drawable.photo9, "Beach", "Travel"))
        allPhotos.add(Photo(10, R.drawable.photo10, "Mountain Trek", "Travel"))
        allPhotos.add(Photo(11, R.drawable.photo11, "Sunset", "Nature"))
        allPhotos.add(Photo(12, R.drawable.photo12, "Night City", "City"))
    }

    private fun setupCategoryTabs() {
        findViewById<Button>(R.id.tabAll).setOnClickListener { filterPhotos("All") }
        findViewById<Button>(R.id.tabNature).setOnClickListener { filterPhotos("Nature") }
        findViewById<Button>(R.id.tabCity).setOnClickListener { filterPhotos("City") }
        findViewById<Button>(R.id.tabAnimals).setOnClickListener { filterPhotos("Animals") }
        findViewById<Button>(R.id.tabFood).setOnClickListener { filterPhotos("Food") }
        findViewById<Button>(R.id.tabTravel).setOnClickListener { filterPhotos("Travel") }
    }

    private fun filterPhotos(category: String) {
        val filtered = if (category == "All") allPhotos else allPhotos.filter { it.category == category }
        adapter = PhotoAdapter(this, filtered.toMutableList())
        gridView.adapter = adapter
    }

    private fun setupGridClick() {
        gridView.setOnItemClickListener { _, _, position, _ ->
            if (isSelectionMode) {
                allPhotos[position].isSelected = !allPhotos[position].isSelected
                adapter.notifyDataSetChanged()
                updateSelectedCount()
            } else {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image_res", allPhotos[position].resourceId)
                startActivity(intent)
            }
        }

        gridView.setOnItemLongClickListener { _, _, position, _ ->
            if (!isSelectionMode) {
                isSelectionMode = true
                adapter.setSelectionMode(true)
                selectionToolbar.visibility = View.VISIBLE
            }
            allPhotos[position].isSelected = true
            adapter.notifyDataSetChanged()
            updateSelectedCount()
            true
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val count = adapter.getSelectedPhotos().size
            adapter.deleteSelected()
            exitSelectionMode()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnShare).setOnClickListener {
            Toast.makeText(this, "Photos shared!", Toast.LENGTH_SHORT).show()
            exitSelectionMode()
        }
    }

    private fun updateSelectedCount() {
        val count = adapter.getSelectedPhotos().size
        selectedCountText.text = "$count selected"
    }

    private fun exitSelectionMode() {
        isSelectionMode = false
        adapter.setSelectionMode(false)
        selectionToolbar.visibility = View.GONE
        allPhotos.forEach { it.isSelected = false }
        adapter.notifyDataSetChanged()
    }

    private fun setupFab() {
        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddPhoto)
            .setOnClickListener {
                val categories = arrayOf("Nature", "City", "Animals", "Food", "Travel")
                AlertDialog.Builder(this)
                    .setTitle("Choose Category")
                    .setItems(categories) { _, which ->
                        val category = categories[which]
                        val newPhoto = Photo(
                            id = allPhotos.size + 1,
                            resourceId = R.drawable.photo1,
                            title = "New Photo",
                            category = category
                        )
                        allPhotos.add(newPhoto)
                        adapter = PhotoAdapter(this, allPhotos)
                        gridView.adapter = adapter
                        Toast.makeText(this, "Added to $category", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
    }
}