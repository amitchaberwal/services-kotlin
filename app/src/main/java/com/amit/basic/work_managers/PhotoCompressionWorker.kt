package com.amit.basic.work_managers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/*
1. Add workManager dependency
2.
 */
class PhotoCompressionWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
        return Result.failure()
    }
}