package com.amit.basic.permission_basics

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PermissionRequiredDialog(
    permissionText: PermissionTextProvider,
    isPermanentlyDenied: Boolean,
    onDismiss: () -> Unit,
    onOKClick: () -> Unit,
    onGoToSettings: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if(isPermanentlyDenied){
                    onGoToSettings
                }
                else{
                    onOKClick
                }
            }) { Text(if(isPermanentlyDenied) "Grant Permission" else "OK")}
        },
        title = {
            Text("Permission Required")
        },
        text = {
            Text(permissionText.getDescription(isPermanentlyDenied))
        }
    )
}

interface PermissionTextProvider{
    fun getDescription(isPermanentlyDenied: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "Enable camera permission from settings"
        } else{
            "Camera permission required"
        }
    }
}

class AudioPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "Enable Record_Audio permission from settings"
        } else{
            "Record_Audio permission required"
        }
    }
}

class CallPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "Enable Phone_Call permission from settings"
        } else{
            "Phone_Call permission required"
        }
    }
}