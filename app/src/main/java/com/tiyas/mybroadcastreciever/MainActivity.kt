package com.tiyas.mybroadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tiyas.mybroadcastreciever.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val  ACTION_DOWNLOAD_STATUS = "download_status"
        private const val SMS_REQUEST_CODE = 101
    }

        private lateinit var  downloadReceiver : BroadcastReceiver
    private var mainBinding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        mainBinding?.btnPermission?.setOnClickListener(this)
        mainBinding?.btnDownload?.setOnClickListener(this)

        downloadReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(DownloadService.TAG, "dowwnload selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }

        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    override fun onClick(v : View) {
        when(v.id){
            R.id.btn_permission -> PermissionManager.check(this, android.Manifest.permission.RECEIVE_SMS,
                SMS_REQUEST_CODE )
            R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        mainBinding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE){
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this,"Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "SMs receiver permission ditolak", Toast.LENGTH_SHORT).show()

            }
        }
    }
}