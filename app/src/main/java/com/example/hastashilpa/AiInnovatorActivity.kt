package com.example.hastashilpa

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class ChatMessage(val text: String, val isUser: Boolean)

class AiInnovatorActivity : AppCompatActivity() {

    private var chatMessages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    private lateinit var pbLoading: ProgressBar
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_innovator)

        loadChat()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val rvChat = findViewById<RecyclerView>(R.id.rvChat)
        val etPrompt = findViewById<EditText>(R.id.etPrompt)
        val btnSend = findViewById<FloatingActionButton>(R.id.btnSend)
        pbLoading = findViewById(R.id.pbLoading)

        adapter = ChatAdapter(chatMessages)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = adapter

        // Initial AI Message (Welcome Message) if empty
        if (chatMessages.isEmpty()) {
            chatMessages.add(ChatMessage("Namaste! I can help you design modern bamboo products. What should we create today?", false))
            adapter.notifyItemInserted(0)
            saveChat()
        } else {
            rvChat.scrollToPosition(chatMessages.size - 1)
        }

        btnSend.setOnClickListener {
            val query = etPrompt.text.toString().trim()
            if (query.isNotEmpty()) {
                // Add User Message
                chatMessages.add(ChatMessage(query, true))
                saveChat()
                adapter.notifyItemRowsInserted()
                etPrompt.text.clear()
                
                // Simulate AI response
                simulateAiResponse(query)
            }
        }
    }

    private fun simulateAiResponse(query: String) {
        pbLoading.visibility = View.VISIBLE
        
        val response = when {
            query.contains("lamp", ignoreCase = true) || query.contains("light", ignoreCase = true) -> 
                "For a modern bamboo lamp, consider a 'slat-style' cylindrical design. Use thin, vertical bamboo strips with 5mm gaps to create a rhythm of light and shadow."
            query.contains("stand", ignoreCase = true) -> 
                "A bamboo laptop stand works best with an 'A-frame' structure. Focus on smooth, rounded joints and a natural oil finish for that premium tech-accessory feel."
            query.contains("basket", ignoreCase = true) -> 
                "Try a 'nested' geometric set of baskets. Instead of traditional rounded tops, use square bases that transition into circular rims for a sculptural look."
            else -> "That's an interesting idea. For modern bamboo crafts, I recommend focusing on 'Organic Modernism'—keeping the natural texture of the pole while using clean, minimalist silhouettes."
        }
        
        // Add AI Message with slight delay to simulate processing
        findViewById<View>(android.R.id.content).postDelayed({
            pbLoading.visibility = View.GONE
            chatMessages.add(ChatMessage(response, false))
            saveChat()
            adapter.notifyItemRowsInserted()
        }, 1500)
    }

    private fun saveChat() {
        val prefs = getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE)
        val json = gson.toJson(chatMessages)
        prefs.edit().putString("chat_history", json).apply()
    }

    private fun loadChat() {
        val prefs = getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE)
        val json = prefs.getString("chat_history", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<ChatMessage>>() {}.type
            chatMessages = gson.fromJson(json, type)
        }
    }
    
    private fun ChatAdapter.notifyItemRowsInserted() {
        notifyItemInserted(chatMessages.size - 1)
        findViewById<RecyclerView>(R.id.rvChat).smoothScrollToPosition(chatMessages.size - 1)
    }

    class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
        class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvMessage: TextView = view.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val layout = android.R.layout.simple_list_item_1
            val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return ChatViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            val msg = messages[position]
            holder.tvMessage.text = if (msg.isUser) "You: ${msg.text}" else "Gemini: ${msg.text}"
            
            if (msg.isUser) {
                holder.tvMessage.setTextColor(0xFF5A5A40.toInt())
            } else {
                holder.tvMessage.setTextColor(0xFF8B4513.toInt())
            }
            holder.tvMessage.textSize = 14f
        }

        override fun getItemCount() = messages.size
    }
}
