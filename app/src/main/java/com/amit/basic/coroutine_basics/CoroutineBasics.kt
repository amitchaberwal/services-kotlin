package com.amit.basic.coroutine_basics

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class CoroutineBasics: ComponentActivity(){

    //1. Coroutine in global scope
    fun launchInGlobalScope(){
        // coroutine always need a scope
        GlobalScope.launch {
            val res = networkCall()
            delay(1000L)
            print("Coroutine Thread: ${Thread.currentThread().name}")
        }
    }
    //2. Suspend function -> we can call it only in coroutine
    suspend fun networkCall(): String{
        delay(2000L)
        return "network response"
    }

    //3. Coroutine Context -> we can run on different thread inside of a coroutine
    fun launchInCoroutineContexts(){
        GlobalScope.launch (Dispatchers.IO){
            val response = networkCall()
            withContext(Dispatchers.Main){
                //Update UI from response
            }

        }
        // for custom thread
        GlobalScope.launch (newSingleThreadContext("MyThread")){
        }
    }
    //4. runBlocking -> it will block the main thread -> it is use to use suspend function in main thread
    suspend fun runBlockingFunction(){
        runBlocking {
            delay(1000L)
            launch {
                delay(3000L)
            }
            launch { delay(3000L) }
        }
    }

    //5. Jobs, waiting, cancellation
    fun coroutineJob(){
        val job = GlobalScope.launch(Dispatchers.Main){
            repeat(5){
                //check if it is canceled or not
                if(isActive){
                    print("coroutine is working:$it")
                }

                delay(1000L)

                // used to create a timeout coroutine -> if it take more time than it will be cancelled
                withTimeout(5000L){
                    delay(6000L)

                }
            }
        }
        // to use coroutine in main thread
        runBlocking {
            delay(2000L)
            // cancel coroutine after 2 sec..coroutine should have enough time to cancel it
            job.cancel()
        }
    }

    //6. Async/ Await
    fun asyncAwaitFun(){
        GlobalScope.launch {
            // it is used to check that how much time did it took
            val timeMillis = measureTimeMillis {
                val req1 = async { networkCall() }
                val req2 = async { networkCall() }
                print("First Request: ${req1.await()}")
                print("Second Request: ${req2.await()}")
                // it will wait till both of these are completed
            }
            print("Time taken: $timeMillis ms")
        }
    }

    //7. we should not use global scope(as it can keep running(if not finished) even if that activity is destroyed), instead we should use lifecycleScope, viewModelScope
    fun lifeCycleScopeCoroutine(){
        lifecycleScope.launch {

        }
        // in viewModel -> use viewModelScope

         // Note we can wait for a function(which is not suspend) by using await on call
        // Example ->  val res = api.getResponse().await()
    }

    //8. Exception handling in coroutine. use the handler at the root coroutine
    fun exceptionHandledCoroutine(){
        val handler = CoroutineExceptionHandler { _, throwable ->
            print("Exception: $throwable")
        }

        lifecycleScope.launch(handler) {
            throw Exception("Error")
        }

        CoroutineScope(Dispatchers.IO + handler).launch {
            supervisorScope {
                delay(1000L)
                throw Exception("Coroutine 1 failed")
            }

//            launch {
//                delay(1000L)
//                print("coroutine 2 passed")
//            }
        }
        // in the above coroutine 1 will fail but coroutine 2 will work
    }
    // lifecycleScope and viewModelScope comes with supervisor job handler
}