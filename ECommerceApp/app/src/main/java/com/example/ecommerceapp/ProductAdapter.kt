package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class ProductAdapter(
    private var products: MutableList<Product>,
    private val isGridMode: Boolean,
    private val onCartClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_GRID = 2
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.productImage)
        val name: TextView = view.findViewById(R.id.productName)
        val category: TextView = view.findViewById(R.id.productCategory)
        val rating: RatingBar = view.findViewById(R.id.productRating)
        val price: TextView = view.findViewById(R.id.productPrice)
        val btnAdd: Button = view.findViewById(R.id.btnAddToCart)
    }

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.productImage)
        val name: TextView = view.findViewById(R.id.productName)
        val price: TextView = view.findViewById(R.id.productPrice)
        val btnCart: ImageButton = view.findViewById(R.id.btnCartIcon)
    }

    override fun getItemViewType(position: Int) =
        if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_GRID) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_grid, parent, false)
            GridViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_list, parent, false)
            ListViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is ListViewHolder) {
            holder.name.text = product.name
            holder.category.text = product.category
            holder.rating.rating = product.rating
            holder.price.text = "৳${product.price}"
            holder.image.setImageResource(product.imageRes)
            holder.btnAdd.text = if (product.inCart) "Remove" else "Add"
            holder.btnAdd.setOnClickListener { onCartClick(product) }
        } else if (holder is GridViewHolder) {
            holder.name.text = product.name
            holder.price.text = "৳${product.price}"
            holder.image.setImageResource(product.imageRes)
            holder.btnCart.setOnClickListener { onCartClick(product) }
        }
    }

    override fun getItemCount() = products.size

    fun updateList(newList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = products.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(o: Int, n: Int) = products[o].id == newList[n].id
            override fun areContentsTheSame(o: Int, n: Int) = products[o] == newList[n]
        })
        products.clear()
        products.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int): Product {
        val removed = products.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }

    fun restoreItem(product: Product, position: Int) {
        products.add(position, product)
        notifyItemInserted(position)
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(products, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(products, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun getProducts(): List<Product> = products
}