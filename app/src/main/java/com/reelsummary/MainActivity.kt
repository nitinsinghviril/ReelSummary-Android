package com.reelsummary

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this).apply {
            text = "ReelSummary App\n\nShare an Instagram Reel to this app to create a summary!"
            textSize = 18f
            setPadding(40, 40, 40, 40)
        }
        
        setContentView(textView)
    }
}
