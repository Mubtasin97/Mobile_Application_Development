package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalText: TextView
    private lateinit var checkoutButton: Button
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<Toolbar>(R.id.cartToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Cart"

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalText = findViewById(R.id.tvTotalPrice)
        checkoutButton = findViewById(R.id.btnCheckout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        refreshCart()

        checkoutButton.setOnClickListener {
            Toast.makeText(this, "Checkout successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshCart() {
        val cartList = ProductRepository.cartItems.toMutableList()

        adapter = ProductAdapter(cartList, false) { product ->
            product.inCart = false
            ProductRepository.cartItems.removeAll { it.id == product.id }
            refreshCart()
        }

        recyclerView.adapter = adapter
        refreshTotal()
    }

    private fun refreshTotal() {
        val total = ProductRepository.cartItems.sumOf { item -> item.price }
        totalText.text = "Total: ৳%.2f".format(total)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}