package com.amit.basic.permission_basics

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class PermissionBasics : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            val viewModel = viewModel<PermissionViewModel>()
            val dialogQueue = viewModel.visiblePermissionDialogQueue

            val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(
                        Manifest.permission.CAMERA,
                        isGranted = isGranted,
                    )
                }
            )
            val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { perms ->
                    perms.keys.forEach { permission ->
                        viewModel.onPermissionResult(
                            permission = permission,
                            isGranted = perms[permission] == true,
                        )
                    }

                }
            )


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        cameraPermissionResultLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                ) { Text("Request Single Permission") }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        multiplePermissionResultLauncher.launch(
                            arrayOf(
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CALL_PHONE
                            )
                        )
                    }
                ) { Text("Request Multiple Permission") }

                dialogQueue.reversed().forEach { permission ->
                    PermissionRequiredDialog(
                        permissionText = when (permission) {
                            Manifest.permission.CAMERA -> {
                                CameraPermissionTextProvider()
                            }

                            Manifest.permission.RECORD_AUDIO -> {
                                AudioPermissionTextProvider()
                            }

                            Manifest.permission.CALL_PHONE -> {
                                CallPermissionTextProvider()
                            }

                            else -> return@forEach
                        },
                        isPermanentlyDenied = !shouldShowRequestPermissionRationale(permission),
                        onDismiss = viewModel::dismissDialog,
                        onOKClick = {
                            viewModel.dismissDialog()
                            multiplePermissionResultLauncher.launch(
                                arrayOf(
                                    permission
                                )
                            )
                        },
                        onGoToSettings = {
                            openAppSetting()
                        },
                    )
                }
            }
        }
    }
}

fun Activity.openAppSetting(){
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",packageName,null)).also {
        startActivity(it)
    }
}