package com.amit.basic.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
/*
Broadcast are sent over system wide. Your app has to register broadcast receiver.
Example on system boot => system send boot complete broadcast.
NOTE: register your broadcast receiver in mainActivity and also deregister on activity destroy
If app is not launched then we'll use static receiver and only bootComplete is available because of restrictions
NOTE: register your receiver in manifest only(No needed in main activity)
<receiver android:name:".AirplaneModeReceiver">
<intent-filter>
Define intent filter
/>
*/

class AirplaneModeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val isTurnedOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            print("Amit")
        }
    }
}