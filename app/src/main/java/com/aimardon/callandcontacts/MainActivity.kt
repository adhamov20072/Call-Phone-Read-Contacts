package com.aimardon.callandcontacts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.aimardon.callandcontacts.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var requestPermission: ActivityResultLauncher<String>
    lateinit var requestPermissionCall: ActivityResultLauncher<String>
    lateinit var adapter: Adapter
    val arraylist = ArrayList<PNdata>()
    var number:String=""
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = Adapter() {
            number=it.phone
            call(it.phone)
            //Toast.makeText(this,number.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
        requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                    readContacts()
                    adapter.submitList(arraylist)
            }
        }
        permissionContact()
        requestPermissionCall = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                call(number)
            }
        }
        permissionCall()
    }



    fun call(number: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
    }

    @SuppressLint("Recycle", "Range")
     fun readContacts() {
        val phoneData =
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
        if (phoneData!!.count > 0) {
            while (phoneData.moveToNext()) {
                val name =
                    phoneData.getString(phoneData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone =
                    phoneData.getString(phoneData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                arraylist.add(PNdata(name, phone))
            }
        }
    }
    fun permissionContact(){
        val given = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (given) {
                readContacts()
                adapter.submitList(arraylist)
        } else {
            requestPermission.launch(android.Manifest.permission.READ_CONTACTS)
        }
    }
    fun permissionCall(){
        val given = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
        if (given) {
                readContacts()
                adapter.submitList(arraylist)
            }
         else {
            requestPermission.launch(android.Manifest.permission.READ_CONTACTS)
        }
    }
}