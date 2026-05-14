package com.example.hastashilpa

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.print.PrintHelper
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton

class BlueprintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blueprint)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        val tvLength = findViewById<TextView>(R.id.tvLength)
        val tvWidth = findViewById<TextView>(R.id.tvWidth)
        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        val llBackNav = findViewById<LinearLayout>(R.id.llBackNav)
        val ivProductImage = findViewById<ImageView>(R.id.ivProductImage)
        val btnDownload = findViewById<MaterialButton>(R.id.btnDownloadBlueprint)

        // Retrieve data from intent
        val designName = intent.getStringExtra("DESIGN_NAME") ?: "Bamboo Blueprint"
        val designImage = intent.getIntExtra("DESIGN_IMAGE", R.drawable.ic_bamboo_laptop_stand)
        
        collapsingToolbar.title = designName
        ivProductImage.setImageResource(designImage)

        llBackNav.setOnClickListener {
            onBackPressed()
        }

        // MAKE DOWNLOAD/PRINT WORK WITH INFO
        btnDownload.setOnClickListener {
            Toast.makeText(this, "Generating Detailed Technical Printout...", Toast.LENGTH_SHORT).show()
            generateDetailedPrintout(designName, tvLength.text.toString(), tvWidth.text.toString(), tvHeight.text.toString())
        }

        // Dynamic measurements for modern designs
        when (designName) {
            "Bamboo Laptop Stand" -> {
                tvLength.text = "35"
                tvWidth.text = "25"
                tvHeight.text = "12"
            }
            "Geometric Lampshade" -> {
                tvLength.text = "20"
                tvWidth.text = "20"
                tvHeight.text = "35"
            }
            "Minimalist Fruit Basket" -> {
                tvLength.text = "30"
                tvWidth.text = "30"
                tvHeight.text = "15"
            }
            else -> {
                tvLength.text = "40"
                tvWidth.text = "20"
                tvHeight.text = "10"
            }
        }
    }

    private fun generateDetailedPrintout(name: String, l: String, w: String, h: String) {
        val ivSketch = findViewById<ImageView>(R.id.ivSketch)
        
        // Create a bitmap large enough for sketch + text info
        val width = ivSketch.width
        val height = ivSketch.height + 400
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Background
        canvas.drawColor(Color.WHITE)
        
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 50f
            isAntiAlias = true
        }

        // Header Info
        canvas.drawText("HASTA-SHILPA BLUEPRINT", 50f, 80f, paint.apply { textSize = 60f; isFakeBoldText = true })
        canvas.drawText("Design: $name", 50f, 160f, paint.apply { textSize = 45f; isFakeBoldText = false })
        canvas.drawText("Measurements: L:${l}cm, W:${w}cm, H:${h}cm", 50f, 230f, paint)
        canvas.drawText("Date: ${java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date())}", 50f, 300f, paint)
        
        // Draw the technical sketch below the info
        canvas.save()
        canvas.translate(0f, 350f)
        ivSketch.draw(canvas)
        canvas.restore()

        val printHelper = PrintHelper(this)
        printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printHelper.printBitmap("HastaShilpa-$name", bitmap)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
