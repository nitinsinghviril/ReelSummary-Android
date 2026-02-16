package com.reelsummary

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class ShareReceiverActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    
    // TODO: Replace with your API key from console.anthropic.com
    private val apiKey = "YOUR_ANTHROPIC_API_KEY_HERE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }
        
        val progressBar = ProgressBar(this)
        val statusText = TextView(this).apply {
            text = "Generating summary..."
            textSize = 16f
            setPadding(0, 20, 0, 0)
        }
        
        layout.addView(progressBar)
        layout.addView(statusText)
        setContentView(layout)
        
        handleIncomingShare(statusText)
    }

    private fun handleIncomingShare(statusText: TextView) {
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let { sharedText ->
                        processSharedUrl(sharedText, statusText)
                    }
                }
            }
        }
    }

    private fun processSharedUrl(sharedText: String, statusText: TextView) {
        val instagramUrl = extractInstagramUrl(sharedText)
        
        if (instagramUrl == null) {
            statusText.text = "No Instagram URL found"
            Toast.makeText(this, "No Instagram URL found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val summary = withContext(Dispatchers.IO) {
                    generateSummary(instagramUrl, sharedText)
                }
                
                statusText.text = "Summary:\n\n$summary"
                Toast.makeText(
                    this@ShareReceiverActivity,
                    "Summary created!",
                    Toast.LENGTH_SHORT
                ).show()
                
            } catch (e: Exception) {
                statusText.text = "Error: ${e.message}"
                Toast.makeText(
                    this@ShareReceiverActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun generateSummary(videoUrl: String, extractedText: String): String {
        val prompt = """
            You are an AI assistant that creates concise summaries of Instagram Reels.
            
            Instagram Reel URL: $videoUrl
            Shared text: $extractedText
            
            Please provide a clear, concise summary of this reel in 2-3 sentences.
        """.trimIndent()
        
        val jsonBody = JSONObject().apply {
            put("model", "claude-sonnet-4-5-20250929")
            put("max_tokens", 500)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
        }

        val request = Request.Builder()
            .url("https://api.anthropic.com/v1/messages")
            .addHeader("x-api-key", apiKey)
            .addHeader("anthropic-version", "2023-06-01")
            .addHeader("content-type", "application/json")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null) {
            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse.getJSONArray("content")
            return content.getJSONObject(0).getString("text")
        } else {
            throw Exception("API Error: ${response.code}")
        }
    }

    private fun extractInstagramUrl(text: String): String? {
        val pattern = Regex("https?://(?:www\\.)?instagram\\.com/(?:p|reel)/[A-Za-z0-9_-]+/?")
        return pattern.find(text)?.value
    }
}
