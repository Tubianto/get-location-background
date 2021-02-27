package com.tubianto.getlocationbackground.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.tubianto.getlocationbackground.service.LocationService

class RestartBackgroundService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("Broadcast Listened", "Service tried to stop")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, LocationService::class.java))
        } else {
            context.startService(Intent(context, LocationService::class.java))
        }
    }
}