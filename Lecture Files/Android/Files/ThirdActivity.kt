package com.example.intents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        // declare an intent to start our service
        val intent  = Intent(this,
            RandomNumberService::class.java)
        // starts the service
        startService(intent)
    }

}