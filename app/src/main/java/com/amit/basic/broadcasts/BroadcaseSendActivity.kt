package com.amit.basic.broadcasts

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.amit.basic.Greeting
import com.amit.basic.ui.theme.AndroidBasicsTheme

/*
Send broadcast to all apps

 */
class BroadcastSendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "SEND BROADCAST",
                        modifier = Modifier.padding(innerPadding)
                    )
                    Button(onClick = {
                        sendBroadcast(Intent("TEST_ACTION"))
                    }) { }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}