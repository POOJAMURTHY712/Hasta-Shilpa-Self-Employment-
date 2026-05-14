package com.example.hastashilpa

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class MaterialBatch(val name: String, val bamboo: Int, val cane: Int, val timestamp: String)

class MaterialTrackerActivity : AppCompatActivity() {

    private var historyList = mutableListOf<MaterialBatch>()
    private lateinit var adapter: HistoryAdapter
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_tracker)

        loadHistory()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val etBatchName = findViewById<TextInputEditText>(R.id.etBatchName)
        val etBamboo = findViewById<TextInputEditText>(R.id.etBambooPoles)
        val etCane = findViewById<TextInputEditText>(R.id.etCaneBundles)
        val btnSave = findViewById<MaterialButton>(R.id.btnLogBatch)
        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)

        adapter = HistoryAdapter(historyList)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = adapter

        btnSave.setOnClickListener {
            val name = etBatchName.text.toString()
            val bamboo = etBamboo.text.toString().toIntOrNull() ?: 0
            val cane = etCane.text.toString().toIntOrNull() ?: 0

            if (name.isNotEmpty()) {
                val newBatch = MaterialBatch(name, bamboo, cane, java.text.SimpleDateFormat("dd MMM, HH:mm", java.util.Locale.getDefault()).format(java.util.Date()))
                historyList.add(0, newBatch)
                saveHistory()
                adapter.notifyItemInserted(0)
                rvHistory.scrollToPosition(0)
                
                etBatchName.text?.clear()
                etBamboo.text?.clear()
                etCane.text?.clear()
                
                Toast.makeText(this, "Batch Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveHistory() {
        val prefs = getSharedPreferences("MaterialTrackerPrefs", Context.MODE_PRIVATE)
        val json = gson.toJson(historyList)
        prefs.edit().putString("history_list", json).apply()
    }

    private fun loadHistory() {
        val prefs = getSharedPreferences("MaterialTrackerPrefs", Context.MODE_PRIVATE)
        val json = prefs.getString("history_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<MaterialBatch>>() {}.type
            historyList = gson.fromJson(json, type)
        }
    }

    class HistoryAdapter(private val items: List<MaterialBatch>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(android.R.id.text1)
            val tvDetails: TextView = view.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.tvName.text = item.name
            holder.tvDetails.text = "Bamboo: ${item.bamboo}, Cane: ${item.cane} | ${item.timestamp}"
        }

        override fun getItemCount() = items.size
    }
}
