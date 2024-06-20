package com.vanilla.contentprovider

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnGetContactPressed(v: View) {
        getPhoneContacts()
    }

    private fun getPhoneContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        } else {
            val contentResolver: ContentResolver = contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentResolver.query(uri, null, null, null, null)

            cursor?.let {
                Log.i("CONTACT_PROVIDER", "TOTAL # of Contacts ::: ${it.count}")

                if (it.count > 0) {
                    while (it.moveToNext()) {
                        val contactName = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val contactNumber = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                        Log.i("CONTACT_PROVIDER", "Contacts Name ::: $contactName Ph # ::: $contactNumber")
                    }
                }
                it.close()
            }
        }
    }
}
