package com.reelsummary

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 100, 60, 60)
            setBackgroundColor(Color.parseColor("#FAFAFA"))
        }
        
        // App Icon (emoji)
        val iconText = TextView(this).apply {
            text = "🎬🤖"
            textSize = 64f
            gravity = android.view.Gravity.CENTER
            setPadding(0, 0, 0, 20)
        }
        
        // App Title with gradient
        val titleText = TextView(this).apply {
            text = "ReelSummary"
            textSize = 36f
            setTextColor(Color.parseColor("#E1306C"))
            gravity = android.view.Gravity.CENTER
            setPadding(0, 0, 0, 10)
            
            // Add gradient effect
            post {
                val paint: TextPaint = paint
                val width = paint.measureText(text.toString())
                val textShader: Shader = LinearGradient(
                    0f, 0f, width, textSize,
                    intArrayOf(
                        Color.parseColor("#E1306C"),
                        Color.parseColor("#C13584"),
                        Color.parseColor("#5B51D8")
                    ),
                    null, Shader.TileMode.CLAMP
                )
                paint.shader = textShader
            }
        }
        
        // Tagline
        val taglineText = TextView(this).apply {
            text = "✨ AI-powered Reel summaries"
            textSize = 16f
            setTextColor(Color.parseColor("#424242"))
            gravity = android.view.Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }
        
        // Instructions
        val instructionsText = TextView(this).apply {
            text = """
                📱 How to use:
                
                1️⃣ Open Instagram
                2️⃣ Find any Reel
                3️⃣ Tap Share button (✈️)
                4️⃣ Select "ReelSummary"
                5️⃣ Get instant AI summary!
                
                💡 Tip: Works with any public reel
                
                🤖 Powered by Google Gemini AI
            """.trimIndent()
            textSize = 16f
            setTextColor(Color.parseColor("#424242"))
            lineHeight = 36
            setPadding(20, 0, 20, 0)
        }
        
        layout.addView(iconText)
        layout.addView(titleText)
        layout.addView(taglineText)
        layout.addView(instructionsText)
        
        setContentView(layout)
    }
}
