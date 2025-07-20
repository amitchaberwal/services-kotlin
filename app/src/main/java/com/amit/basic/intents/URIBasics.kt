package com.amit.basic.intents

import android.net.Uri
import androidx.activity.ComponentActivity
import java.io.File
import java.io.FileOutputStream

class URIBasics: ComponentActivity(){
    fun useResourceUri(){
        val uri = Uri.parse("android.resource://$packageName/drawable/contentName")
        val contentBytes = contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
        var file = File(filesDir, "contentFile.jpg")
        FileOutputStream(file).use {
            it.write(contentBytes)
        }

        print("path: ${file.toURI()}")
    }
}