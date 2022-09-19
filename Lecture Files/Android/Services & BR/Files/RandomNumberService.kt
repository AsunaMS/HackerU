package com.example.intents

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class RandomNumberService : Service() {

    // object that can execute actions on other threads with delay
    private lateinit var timer: Timer

    override fun onBind(intent: Intent): IBinder? {
        // we do not user binding in this example
        return null
    }

    private fun toast(toast: String) {
        // toasting logic goes here
        Toast.makeText(applicationContext, toast, Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Send a notification that service is started
        toast("Service started.")
        // Do a periodic task
        timer = Timer()
        timer.schedule(500, 5000) {
            showRandomNumber()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("Service destroyed.")
        timer.cancel()
    }

    // Custom method to do a task
    private fun showRandomNumber() {
        val rand = Random()
        val number = rand.nextInt(100)
        toast("Random Number : $number")
    }
}