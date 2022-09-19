package com.example.intents

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {


    // define the airplane mode change receiver
    private val receiver: AirplaneModeChangeReceiver by lazy {
        AirplaneModeChangeReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // register the receiver when the activity is created
        registerBroadcastReceiver()
    }

    private fun registerBroadcastReceiver() {
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {

            // receiver is the broadcast receiver that we have registered

            // and it is the intent filter that we have created

            registerReceiver(receiver, it)
        }
    }

    // unregister the receiver when the activity is stopped
    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}