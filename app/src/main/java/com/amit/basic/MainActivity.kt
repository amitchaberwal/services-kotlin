package com.amit.basic

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.amit.basic.broadcasts.AirplaneModeReceiver
import com.amit.basic.broadcasts.BroadCastTestReceiver
import com.amit.basic.services.RunningForgroundService
import com.amit.basic.ui.theme.AndroidBasicsTheme

class MainActivity : ComponentActivity() {
    // Airplane BroadCast Receiver: Create instance
    private val airplaneModeReceiver = AirplaneModeReceiver()

    // Test BroadCast Receiver: Create instance
    private val testReceiver = BroadCastTestReceiver()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Intent: get extras on activity launch
        val intent = intent.extras

        // Airplane BroadCast Receiver: register
        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        // Test BroadCast Receiver: register
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(testReceiver, IntentFilter("TEST_ACTION"),Context.RECEIVER_NOT_EXPORTED)
        }

        // Foreground Service: Request Permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBasicsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Home", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                            },
                        )
                    }
                ) { innerPadding ->
                    Column (modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(innerPadding),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                        ){
                        Text("Foreground Services")
                        Spacer(modifier = Modifier.height(10.dp))
                        // Foreground Service: Start and stop foreground service
                        Row {
                            Button(onClick = {
                                Intent(
                                    applicationContext,
                                    RunningForgroundService::class.java
                                ).also {
                                    it.action = RunningForgroundService.Actions.START.toString()
                                    startService(it)
                                }
                            }) {
                                Text("Start Foreground Service")
                            }
                            Spacer(Modifier.width(5.dp))
                            Button(onClick = {
                                Intent(
                                    applicationContext,
                                    RunningForgroundService::class.java
                                ).also {
                                    it.action = RunningForgroundService.Actions.STOP.toString()
                                    startService(it)
                                }
                            }) {
                                Text("Stop Foreground Service")
                            }
                        }
                        // Foreground Service: END
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Work Manager")
                        Spacer(modifier = Modifier.height(10.dp))
                        // WorkManager: Start and stop foreground service
                        Button(onClick = {

                        }) {
                            Text("Start WorkManager")
                        }

                    }
                }
            }
        }
    }

    // Intent: if launchMode is set to singleTop then get the intent as stream
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        print("OnNewIntent:${intent}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val uri = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri :: class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Airplane BroadCast Receiver: Unregister
        unregisterReceiver(airplaneModeReceiver)

        // Test BroadCast Receiver: Unregister
        unregisterReceiver(testReceiver)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidBasicsTheme {
        Greeting("Android")
    }
}