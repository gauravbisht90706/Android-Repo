package com.example.message_send


import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var msgField:EditText
    private lateinit var phoneField:EditText
    private lateinit var sendButton:Button
    private val SMSpermissionRequest = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        msgField = findViewById(R.id.msg)
        phoneField = findViewById(R.id.phone)
        sendButton = findViewById(R.id.send)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),
        SMSpermissionRequest)
        sendButton.setOnClickListener {
            val phone = phoneField.text.toString()
            val msg = msgField.text.toString()

            if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(msg)){
                Toast.makeText(this, "Empty Field", Toast.LENGTH_SHORT).
                        show()
                return@setOnClickListener
            }
            val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)

            if(permissionCheck ==PackageManager.PERMISSION_GRANTED){
                sendMSG(phone, msg)
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),
                SMSpermissionRequest)
            }

            phoneField.setText("")
        }



    }

    private fun sendMSG(phone: String, msg: String) {
        if(TextUtils.isDigitsOnly(phone) && phone!!.length == 10){
            val smsManager:SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, msg, null, null)
            Toast.makeText(this, "Message Sent to number $phone",Toast.LENGTH_SHORT).show()
            Log.d("Message Sent", "YES");
        }
        else{
            Log.d("Message Sent", "NO");
            Toast.makeText(this, "Please Enter the Correct Number", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == SMSpermissionRequest){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(this, "You don't have Required Permission", Toast.LENGTH_SHORT).show()
                finishActivity(0)
            }
        }
    }


}