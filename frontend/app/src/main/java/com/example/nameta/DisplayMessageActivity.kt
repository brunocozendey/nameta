package com.example.nameta

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.data.DialogflowDataSource

class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)!!


        // Send message to Dialogflow and receive a response from matched intent
        val credentials = resources.openRawResource(R.raw.credentials_dialogflow)
        val dataSource = DialogflowDataSource(credentials)
        val sessionId = "my-android-session"
        val languageCode = "pt-BR"
        val result = dataSource.detectIntentTexts(message, sessionId, languageCode)

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = result
        }

    }
}