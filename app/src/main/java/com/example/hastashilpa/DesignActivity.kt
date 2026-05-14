package com.example.hastashilpa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val rvDesigns = findViewById<RecyclerView>(R.id.rvDesigns)
        rvDesigns.layoutManager = LinearLayoutManager(this)

        val designs = listOf(
            DesignItem("Bamboo Laptop Stand", "Modern Office", R.drawable.ic_bamboo_laptop_stand),
            DesignItem("Geometric Lampshade", "Home Decor", R.drawable.ic_lampshade),
            DesignItem("Minimalist Fruit Basket", "Kitchenware", R.drawable.ic_basket),
            DesignItem("Cane Tablet Holder", "Tech Accessories", R.drawable.ic_bamboo_laptop_stand)
        )

        rvDesigns.adapter = DesignAdapter(designs) { item ->
            val intent = Intent(this, BlueprintActivity::class.java)
            intent.putExtra("DESIGN_NAME", item.title)
            intent.putExtra("DESIGN_IMAGE", item.imageResId)
            startActivity(intent)
        }
    }
}
