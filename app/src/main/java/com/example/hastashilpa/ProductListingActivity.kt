package com.example.hastashilpa

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductListingActivity : AppCompatActivity() {

    private var productList = mutableListOf<ProductItem>()
    private lateinit var adapter: ProductAdapter
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_listing)

        loadProducts()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(productList)
        rvProducts.adapter = adapter

        val fabAddProduct = findViewById<ExtendedFloatingActionButton>(R.id.fabAddProduct)
        fabAddProduct.setOnClickListener {
            showAddProductDialog()
        }
    }

    private fun showAddProductDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_product, null)
        val etName = dialogView.findViewById<EditText>(R.id.etProductName)
        val etPrice = dialogView.findViewById<EditText>(R.id.etProductPrice)

        AlertDialog.Builder(this)
            .setTitle("List New Product")
            .setView(dialogView)
            .setPositiveButton("List") { _, _ ->
                val name = etName.text.toString().trim()
                val price = etPrice.text.toString().trim()
                if (name.isNotEmpty() && price.isNotEmpty()) {
                    val newItem = ProductItem(name, "₹ $price")
                    productList.add(0, newItem)
                    saveProducts()
                    adapter.notifyItemInserted(0)
                    findViewById<RecyclerView>(R.id.rvProducts).scrollToPosition(0)
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveProducts() {
        val prefs = getSharedPreferences("MarketplacePrefs", Context.MODE_PRIVATE)
        val json = gson.toJson(productList)
        prefs.edit().putString("product_list", json).apply()
    }

    private fun loadProducts() {
        val prefs = getSharedPreferences("MarketplacePrefs", Context.MODE_PRIVATE)
        val json = prefs.getString("product_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<ProductItem>>() {}.type
            productList = gson.fromJson(json, type)
        } else {
            // Default items if empty
            productList = mutableListOf(
                ProductItem("Bamboo Chair", "₹ 1,200"),
                ProductItem("Cane Basket", "₹ 500"),
                ProductItem("Bamboo Lamp", "₹ 900"),
                ProductItem("Modern Laptop Stand", "₹ 1,500")
            )
        }
    }
}
