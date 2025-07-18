package com.amit.basic

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.amit.basic.broadcasts.AirplaneModeReceiver
import com.amit.basic.broadcasts.BroadCastTestReceiver
import com.amit.basic.services.RunningForgroundService
import com.amit.basic.ui.theme.AndroidBasicsTheme
import com.amit.basic.work_managers.DownloadWorker
import com.amit.basic.work_managers.PhotoCompressionWorker
import com.amit.basic.work_managers.ProcessWorker
import java.util.UUID

class MainActivity : ComponentActivity() {
    // Airplane BroadCast Receiver: Create instance
    private val airplaneModeReceiver = AirplaneModeReceiver()

    // Test BroadCast Receiver: Create instance
    private val testReceiver = BroadCastTestReceiver()

    //WorkManager: Initialize
    private lateinit var workManager: WorkManager
    private var workerId: UUID? = null

    private lateinit var chainedWorkManager: WorkManager
    private var downloadWorkerId: UUID? = null
    private var processWorkerId: UUID? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Intent: get extras on activity launch
        val intent = intent.extras
        print("getIntentExtrasInit:${intent.toString()}")

        // Airplane BroadCast Receiver: register
        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        // Test BroadCast Receiver: register
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                testReceiver,
                IntentFilter("TEST_ACTION"),
                Context.RECEIVER_NOT_EXPORTED
            )
        }

        // Foreground Service: Request Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        //WorkManager: Initialize
        workManager = WorkManager.getInstance(applicationContext)
        chainedWorkManager = WorkManager.getInstance(applicationContext)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBasicsTheme {
                //WorkManager: Get Result State
                val workerResult = workerId?.let {
                    workManager.getWorkInfoByIdFlow(it).collectAsState(initial = null).value
                }
                LaunchedEffect(key1 = workerResult?.outputData) {
                    if(workerResult?.state == WorkInfo.State.SUCCEEDED){
                            val filepath =
                                workerResult.outputData.getString(PhotoCompressionWorker.KEY_RESULT_PATH)
                    }
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Home",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Broadcast")
                        Spacer(modifier = Modifier.height(5.dp))
                        // Foreground Service: Start and stop foreground service
                        Button(onClick = {
                            sendBroadcast(Intent("TEST_ACTION"))
                        }) {
                            Text("Send Broadcast")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Foreground Services")
                        Spacer(modifier = Modifier.height(5.dp))
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
                        Spacer(modifier = Modifier.height(5.dp))
                        // WorkManager: Start and stop foreground service
                        Row {
                            Button(onClick = {
                                startWorker()
                            }) {
                                Text("Work Manager")
                            }
                            Spacer(Modifier.width(5.dp))
                            Button(onClick = {
                                startWorkerChain()
                            }) {
                                Text("Chained Worker")
                            }
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
            val uri = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Airplane BroadCast Receiver: Unregister
        unregisterReceiver(airplaneModeReceiver)

        // Test BroadCast Receiver: Unregister
        unregisterReceiver(testReceiver)
    }

    private fun startWorker() {
        val uri: String = ""
        val request = OneTimeWorkRequestBuilder<PhotoCompressionWorker>()
            .setInputData(
                workDataOf(
                    PhotoCompressionWorker.KEY_CONTENT_URI to uri,
                    PhotoCompressionWorker.KEY_COMPRESSION_THRESHOLD to (1024 * 20L)
                ),
            )
            .setConstraints(
                Constraints(
                    requiresStorageNotLow = true
                )
            )
            .build()

        workerId = request.id
        workManager.enqueue(request)
    }

    private fun startWorkerChain() {
        val input = "test_data"

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>().setInputData(workDataOf("input_data" to input)).build()
        val processRequest = OneTimeWorkRequestBuilder<ProcessWorker>().build()

        downloadWorkerId = downloadRequest.id
        processWorkerId = processRequest.id

        workManager.beginWith(downloadRequest).then(processRequest).enqueue()

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(processRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val processOutput = workInfo.outputData.getString("processed_data")
                    Log.d("MainActivity", "ChainedWorkManager: $processOutput")
                }
            }
    }
}
