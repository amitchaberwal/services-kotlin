package com.amit.basic.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/*
Register this receiver to receive test broadcast
NOTE: register your broadcast receiver in mainActivity and also deregister on activity destroy
*/
class BroadCastTestReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "TEST_ACTION"){
            print("TEXT_ACTION_RECEIVED")
        }
    }
}