package com.example.intents

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class FourthActivity : AppCompatActivity() {

    private val listView: ListView by lazy { findViewById(R.id.ContactsLv) }

    private val mReadContactsPermissionRequestCode = 0

    private val mContactRequestData: Array<String> = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.Contacts.CONTACT_STATUS,
        ContactsContract.Contacts.HAS_PHONE_NUMBER
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        // Request READ_CONTACTS permission from user
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            mReadContactsPermissionRequestCode
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // if the requested permission is READ_CONTACTS
        if (requestCode == mReadContactsPermissionRequestCode) {
            // if the permission was granted -> query contacts from Content Provider
            if (grantResults[0] == PERMISSION_GRANTED) {
                // access Android Contacts Content Provider
                val mCursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    mContactRequestData,
                    null,
                    null,
                    null
                )
                val list = mutableListOf<String>()
                // read contacts from the cursor object
                if (mCursor != null && mCursor.count > 0) {
                    while (mCursor.moveToNext()) {
                        list.add(mCursor.getString(0))
                    }
                }
                // apply a simple array adapter with the contact list
                listView.adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}