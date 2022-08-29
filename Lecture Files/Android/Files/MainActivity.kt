package com.example.intents

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun moveToNextScreen() {
        // Intent with a result expected
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    fun moveToNextScreenWithResult() {
        // Intent with a result expected
        val intent = Intent(this, SecondActivity::class.java)
        val intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // here we receive result from the intent
        }
        // launch the intent with the result launcher
        intentLauncher.launch(intent)
    }

    fun implicitIntents() {
        // implicit intent to send data to other app
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }
        // lets the user choose an app to send the data to (ex: send SMS)
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}