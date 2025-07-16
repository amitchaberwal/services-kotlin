package com.amit.basic.intents

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.amit.basic.broadcasts.BroadcastSendActivity
import com.amit.basic.ui.theme.AndroidBasicsTheme

class IntentBasics : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Start Intent",
                        modifier = Modifier.padding(innerPadding)
                    )
                    Button(onClick = {
                        //1. Start Activity
                        Intent(applicationContext,BroadcastSendActivity::class.java ).also {
                            startActivity(it)
                        }

                        //2. Start Other App
                        Intent(Intent.ACTION_SEND).also {
                            it.`package` = "com.google.android.youtube"
                            if(it.resolveActivity(packageManager) != null){
                                startActivity(it)
                            }
                        }

                        //3. Send Mail
                        val intentSendMail = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("amit@gmail.com"))
                            putExtra(Intent.EXTRA_TEXT, "how are you")
                        }
                        if(intentSendMail.resolveActivity(packageManager) != null){
                            startActivity(intentSendMail)
                        }
                        //NOTE: Also add query in manifest outside application for sharing intent to other app

                        //4. Get intent stream
//                        override fun onNewIntent(intent: Intent) {
//                            super.onNewIntent(intent)
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                                val uri = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri :: class.java)
//                            }
//                        }

                    }) { }
                }
            }
        }
    }

}