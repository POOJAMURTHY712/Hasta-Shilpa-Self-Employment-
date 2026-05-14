package com.example.hastashilpa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Main Navigation Cards (Discovery & Inventory)
        val cardDesigns = findViewById<MaterialCardView>(R.id.cardDesigns)
        val cardPrice = findViewById<MaterialCardView>(R.id.cardPrice)
        val cardTracker = findViewById<MaterialCardView>(R.id.cardTracker)
        val cardMarket = findViewById<MaterialCardView>(R.id.cardMarket)

        cardDesigns.setOnClickListener {
            startActivity(Intent(this, DesignActivity::class.java))
        }

        cardPrice.setOnClickListener {
            startActivity(Intent(this, PriceCalculatorActivity::class.java))
        }

        cardTracker.setOnClickListener {
            startActivity(Intent(this, MaterialTrackerActivity::class.java))
        }

        cardMarket.setOnClickListener {
            startActivity(Intent(this, ProductListingActivity::class.java))
        }

        // Refined UI Architecture: Floating Bottom Navigation
        // This manages the global navigation across the app's core modules
        val navLibrary = findViewById<ImageView>(R.id.navLibrary)
        val navPrice = findViewById<ImageView>(R.id.navPrice)
        val navInnovator = findViewById<ImageView>(R.id.navInnovator)
        val navTracker = findViewById<ImageView>(R.id.navTracker)
        val navMarket = findViewById<ImageView>(R.id.navMarket)

        // Discovery Flow: Browse modern designs
        navLibrary.setOnClickListener {
            startActivity(Intent(this, DesignActivity::class.java))
        }

        // Economic Planning: Set fair prices based on material and labor
        navPrice.setOnClickListener {
            startActivity(Intent(this, PriceCalculatorActivity::class.java))
        }

        // Collaborative Innovation: Interaction with Gemini AI
        navInnovator.setOnClickListener {
            startActivity(Intent(this, AiInnovatorActivity::class.java))
        }

        // Inventory Control: Tracking material usage
        navTracker.setOnClickListener {
            startActivity(Intent(this, MaterialTrackerActivity::class.java))
        }

        // Marketplace: Connecting artisans with buyers
        navMarket.setOnClickListener {
            startActivity(Intent(this, ProductListingActivity::class.java))
        }
    }
}
