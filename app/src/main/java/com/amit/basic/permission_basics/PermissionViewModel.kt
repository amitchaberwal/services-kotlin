package com.amit.basic.permission_basics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun dismissDialog(){
        visiblePermissionDialogQueue.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if(!isGranted){
            visiblePermissionDialogQueue.add(0, permission)
        }
    }
}