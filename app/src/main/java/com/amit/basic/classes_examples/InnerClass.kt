package com.amit.basic.classes_examples

import java.io.File

class SessionHelper(
    val file: File
) {
    inner class FileHelper(
        val path: String
    ){
        fun doSomething(){
            print("$path, ${file.path}")
        }
    }
}