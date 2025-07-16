package com.amit.basic.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.amit.basic.R

/*
To start foreground service, create notification for showing that service is running
NOTE ->
1. create running_channel in onCreate of application class to make this service work.
2. Register service inside application in manifest to make it work
(if intent is needed then we can also define intent-filter inside the service in manifest)
3. declare permission of foreground service to show notification.
4. Request for permission on app launch
5. Start service using intent(started from main activity button click)
 */
class RunningForgroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(
            this,
            "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service Active")
            .setContentText("Elapsed Time: 00:50")
            .build()
        startForeground(1,notification)
    }

    enum class Actions{
        START, STOP
    }
}