package com.amit.basic.content_providers

import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import java.util.Calendar

class ContentProviderBasics : ComponentActivity() {
    private fun getImagesFromDB() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
        )
        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        var millisYesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis
        val selectionArgs = arrayOf(millisYesterday.toString())

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            var imageList = mutableListOf<ImageData>()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                imageList.add(ImageData(id = id, name = name, uri = uri))
            }
        }
    }

    private fun getContacts() {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactProject = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        contentResolver.query(
            uri, contactProject,
            null, null, null
        )?.use{ cursor ->
            val nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumn)
                val number = cursor.getString(numberColumn)
                Log.d("Contacts", "Name: $name, Number: $number")
            }
        }
    }
}

data class ImageData(
    val id: Long,
    val name: String,
    val uri: Uri
)