package com.tubianto.getlocationbackground

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tubianto.getlocationbackground.service.LocationService
import com.tubianto.getlocationbackground.util.Util

class MainActivity : AppCompatActivity() {
    var mLocationService: LocationService = LocationService()
    lateinit var mServiceIntent: Intent
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        mActivity = this@MainActivity

        if (!Util.isLocationEnabledOrNot(mActivity)) {
            Util.showAlertLocation(
                mActivity,
                getString(R.string.gps_enable),
                getString(R.string.please_turn_on_gps),
                getString(
                    R.string.ok
                )
            )
        }

        requestPermissionsSafely(
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 200
        )
    }

    fun clickStart(view: View) {
        startLocationService()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String>,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun startLocationService(){
        mLocationService = LocationService()
        mServiceIntent = Intent(this, mLocationService.javaClass)
        if (!Util.isMyServiceRunning(mLocationService.javaClass, mActivity)) {
            startService(mServiceIntent)
            Toast.makeText(this, getString(R.string.service_start_successfully), Toast.LENGTH_SHORT).show()
            Log.i("INFO", getString(R.string.service_start_successfully))
        } else {
            Toast.makeText(this, getString(R.string.service_already_running), Toast.LENGTH_SHORT).show()
            Log.i("INFO", getString(R.string.service_already_running))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        /*STOP SERVICE WHERE APPS KILL*/
        if (::mServiceIntent.isInitialized) {
            stopService(mServiceIntent)
        }
        /*STOP SERVICE WHERE APPS KILL*/
    }
}