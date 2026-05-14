package com.example.hastashilpa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class DesignItem(val title: String, val category: String, val imageResId: Int)

class DesignAdapter(private val items: List<DesignItem>, private val onItemClick: (DesignItem) -> Unit) :
    RecyclerView.Adapter<DesignAdapter.DesignViewHolder>() {

    class DesignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivDesignImage)
        val tvTitle: TextView = view.findViewById(R.id.tvDesignTitle)
        val tvCategory: TextView = view.findViewById(R.id.tvDesignCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_design, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(holder: DesignViewHolder, position: Int) {
        val item = items[position]
        holder.ivImage.setImageResource(item.imageResId)
        holder.tvTitle.text = item.title
        holder.tvCategory.text = item.category
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}
