package com.amit.basic.flow_basics

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// A flow is a coroutine which emits values over a period of time
class FlowBasics: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            //State Flow
            val viewModel = viewModel<FlowViewModel>()
            val timerState = viewModel.countDownFlow.collectAsState(initial = 10)


            //SharedFlow
            LaunchedEffect(key1 = true) {
                viewModel.sharedFlow.collect {
                    print("Perform any action")
                }
            }

            Box(modifier = Modifier.fillMaxSize()){
                Text(timerState.value.toString(),
                    modifier = Modifier.align(Alignment.Center)
                    )
            }
        }
    }
}