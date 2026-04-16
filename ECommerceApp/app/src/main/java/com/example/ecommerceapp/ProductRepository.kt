package com.example.ecommerceapp

object ProductRepository {
    val cartItems = mutableListOf<Product>()

    val allProducts = mutableListOf(
        Product(1, "Wireless Headphones", 2500.0, 4.5f, "Electronics", R.drawable.img_headphones),
        Product(2, "Smart Watch", 4200.0, 4.4f, "Electronics", R.drawable.img_smartwatch),
        Product(3, "T-Shirt", 500.0, 4.0f, "Clothing", R.drawable.img_tshirt),
        Product(4, "Jeans", 900.0, 4.1f, "Clothing", R.drawable.img_jeans),
        Product(5, "Kotlin Book", 800.0, 5.0f, "Books", R.drawable.img_book_kotlin),
        Product(6, "Android Dev Book", 700.0, 4.7f, "Books", R.drawable.img_book_android),
        Product(7, "Chocolate Box", 300.0, 4.2f, "Food", R.drawable.img_chocolate_box),
        Product(8, "Snack Pack", 250.0, 4.0f, "Food", R.drawable.img_snack_pack),
        Product(9, "LEGO Set", 1500.0, 4.8f, "Toys", R.drawable.img_lego_set),
        Product(10, "Toy Car", 600.0, 4.3f, "Toys", R.drawable.img_toy_car)
    )
}