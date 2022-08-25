package com.serverless.aircar

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.window.SplashScreen
import androidx.core.app.ActivityCompat

class SplashActivity : AppCompatActivity() {
    val MY_PERMISSION_ACCESS_ALL = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkPermisson()
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent= Intent( this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 1000)
    }
    fun checkPermisson(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            var permissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)
        } else {
            startApp()
        }
    }
    fun startApp(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent( baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === MY_PERMISSION_ACCESS_ALL) {
            if (grantResults.size > 0) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) System.exit(0)
                }
            }
            startApp()
        }
    }
}