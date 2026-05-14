package com.example.hastashilpa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ProductAdapter(private val items: List<ProductItem>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val btnShare: MaterialButton = view.findViewById(R.id.btnShareProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvPrice.text = item.price
        
        holder.btnShare.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Connecting to buyer for ${item.name}...", Toast.LENGTH_SHORT).show()
        }
        
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Viewing details for ${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = items.size
}
