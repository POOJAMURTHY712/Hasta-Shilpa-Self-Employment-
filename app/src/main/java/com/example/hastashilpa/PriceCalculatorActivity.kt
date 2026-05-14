package com.example.hastashilpa

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class PriceCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_calculator)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        val etMaterial = findViewById<TextInputEditText>(R.id.etMaterial)
        val etHours = findViewById<TextInputEditText>(R.id.etHours)
        val etMargin = findViewById<TextInputEditText>(R.id.etMargin)
        val btnCalculate = findViewById<MaterialButton>(R.id.btnCalculate)
        val tvSuggestedPrice = findViewById<TextView>(R.id.tvSuggestedPrice)
        val tvEarningsPerHour = findViewById<TextView>(R.id.tvEarningsPerHour)

        btnCalculate.setOnClickListener {
            val materialCost = etMaterial.text.toString().toDoubleOrNull() ?: 0.0
            val hours = etHours.text.toString().toDoubleOrNull() ?: 0.0
            val marginPercent = etMargin.text.toString().toDoubleOrNull() ?: 20.0

            // Simple Logic: Labor at ₹150/hr (Base artisan wage suggestion)
            val laborCost = hours * 150.0
            val baseCost = materialCost + laborCost
            val profit = baseCost * (marginPercent / 100.0)
            val finalPrice = baseCost + profit

            tvSuggestedPrice.text = "₹ ${String.format("%.2f", finalPrice)}"
            
            val totalEarnings = laborCost + profit
            val hourlyRate = if (hours > 0) totalEarnings / hours else 0.0
            tvEarningsPerHour.text = "Earnings: ₹${String.format("%.2f", hourlyRate)} / hour"
        }
    }
}
