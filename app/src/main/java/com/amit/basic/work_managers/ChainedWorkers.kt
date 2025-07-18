package com.amit.basic.work_managers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import kotlin.time.Duration

class DownloadWorker(private val context: Context,private val params: WorkerParameters): CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        val stringUri = params.inputData.getString("input_data")
        // Download and Save to file logic
        delay(10000L)
        //
        val outputPath = "downloaded_${stringUri}"

        Log.d("MainActivity", "ChainedWorkManagerDownload: $outputPath")
        return Result.success(
            workDataOf("downloaded_data" to outputPath)
        )
    }
}


class ProcessWorker(private val context: Context, private val params: WorkerParameters): CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        val input = params.inputData.getString("downloaded_data")
        // Process input logic
        delay(10000L)
        //
        val output = "processed_${input}"
        Log.d("MainActivity", "ChainedWorkManagerProcess: $output")
        return Result.success(
            workDataOf("processed_data" to output)
        )
    }
}