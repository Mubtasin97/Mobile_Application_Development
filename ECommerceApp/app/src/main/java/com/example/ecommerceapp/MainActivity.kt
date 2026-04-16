package com.example.ecommerceapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.Collections

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var toolbar: Toolbar

    private var isGridMode = false
    private var currentCategory = "All"
    private var currentQuery = ""

    private val categories = listOf("All", "Electronics", "Clothing", "Books", "Food", "Toys")

    private var cartMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)

        setupCategoryFilter()
        showSkeleton()

        Handler(Looper.getMainLooper()).postDelayed({
            hideSkeleton()
            setupAdapter()
            setupItemTouchHelper()
        }, 1200)
    }

    private fun showSkeleton() {
        findViewById<LinearLayout>(R.id.skeletonView).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.emptyStateLayout).visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun hideSkeleton() {
        findViewById<LinearLayout>(R.id.skeletonView).visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun setupCategoryFilter() {
        val layout = findViewById<LinearLayout>(R.id.categoryFilterLayout)
        layout.removeAllViews()

        categories.forEach { cat ->
            val btn = Button(this).apply {
                text = cat
                isAllCaps = false
                setOnClickListener {
                    currentCategory = cat
                    filterProducts()
                }
            }
            layout.addView(btn)
        }
    }

    private fun setupAdapter() {
        val filtered = getFilteredList()

        adapter = ProductAdapter(filtered.toMutableList(), isGridMode) { product ->
            val indexInAll = ProductRepository.allProducts.indexOfFirst { it.id == product.id }
            val indexInVisible = adapter.getProducts().indexOfFirst { it.id == product.id }

            if (product.inCart) {
                ProductRepository.cartItems.removeAll { it.id == product.id }
                if (indexInAll >= 0) ProductRepository.allProducts[indexInAll].inCart = false
                if (indexInVisible >= 0) adapter.getProducts()[indexInVisible].inCart = false
                Toast.makeText(this, "${product.name} removed from cart", Toast.LENGTH_SHORT).show()
            } else {
                if (ProductRepository.cartItems.none { it.id == product.id }) {
                    ProductRepository.cartItems.add(product.copy(inCart = true))
                }
                if (indexInAll >= 0) ProductRepository.allProducts[indexInAll].inCart = true
                if (indexInVisible >= 0) adapter.getProducts()[indexInVisible].inCart = true
                Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }

            if (indexInVisible >= 0) adapter.notifyItemChanged(indexInVisible)
            if (indexInAll >= 0) {
                val visibleNow = getFilteredList()
                adapter.updateList(visibleNow)
                updateEmptyState(visibleNow.isEmpty())
            }
            updateCartBadge()
        }

        recyclerView.layoutManager = if (isGridMode) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }

        recyclerView.adapter = adapter
        updateEmptyState(filtered.isEmpty())
        updateCartBadge()
    }

    private fun setupItemTouchHelper() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = vh.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition
                if (fromPosition == RecyclerView.NO_POSITION || toPosition == RecyclerView.NO_POSITION) {
                    return false
                }

                adapter.swapItems(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val position = vh.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return

                val deleted = adapter.removeItem(position)
                ProductRepository.allProducts.removeAll { it.id == deleted.id }
                ProductRepository.cartItems.removeAll { it.id == deleted.id }

                Snackbar.make(recyclerView, "${deleted.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        ProductRepository.allProducts.add(position, deleted)
                        adapter.restoreItem(deleted, position)
                        filterProducts()
                        updateCartBadge()
                    }
                    .show()

                updateEmptyState(adapter.getProducts().isEmpty())
                updateCartBadge()
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }

    private fun filterProducts() {
        val filtered = getFilteredList()
        adapter.updateList(filtered)
        updateEmptyState(filtered.isEmpty())
    }

    private fun getFilteredList(): List<Product> {
        return ProductRepository.allProducts.filter { product ->
            val matchesCategory = currentCategory == "All" || product.category == currentCategory
            val matchesQuery = product.name.contains(currentQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        findViewById<LinearLayout>(R.id.emptyStateLayout).visibility =
            if (isEmpty) View.VISIBLE else View.GONE

        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        cartMenuItem = menu.findItem(R.id.action_cart)
        updateCartBadge()

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText.orEmpty()
                filterProducts()
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }

            R.id.action_toggle_view -> {
                isGridMode = !isGridMode
                setupAdapter()
                setupItemTouchHelper()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val count = ProductRepository.cartItems.size
        cartMenuItem?.title = if (count > 0) "Cart ($count)" else "Cart"
        cartMenuItem?.icon = createBadgeIcon(count)
    }

    private fun createBadgeIcon(count: Int): android.graphics.drawable.Drawable {
        val cartDrawable = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.ic_shopping_cart
        )!!.mutate()

        if (count <= 0) return cartDrawable

        val bitmap = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        cartDrawable.setBounds(12, 12, 76, 76)
        cartDrawable.draw(canvas)

        val circlePaint = Paint().apply {
            color = Color.RED
            isAntiAlias = true
        }
        canvas.drawCircle(72f, 24f, 20f, circlePaint)

        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 24f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            setFakeBoldText(true)
        }

        val text = if (count > 99) "99+" else count.toString()
        canvas.drawText(text, 72f, 32f, textPaint)

        return BitmapDrawable(this@MainActivity.resources, bitmap)
    }
}