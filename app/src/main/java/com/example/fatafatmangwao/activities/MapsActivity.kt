package com.example.fatafatmangwao.activities

import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.fatafatmangwao.R
import com.example.fatafatmangwao.databinding.ActivityMapsBinding
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMapsBinding
    private lateinit var supportFragmentMap: SupportMapFragment
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportFragmentMap = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        Dexter.withContext(applicationContext)
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    getCurrentLocation()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()

    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else {

        }
        val task = fusedLocationProviderClient.lastLocation
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showToast("enable location pls", Toast.LENGTH_SHORT )
        } else {
            task.addOnSuccessListener { location ->
                supportFragmentMap.getMapAsync { googleMap ->
                    if(location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions = MarkerOptions().position(latLng).title("Current Location")
                        googleMap.addMarker(markerOptions)
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    } else{
                        showToast("Please turn on your device location", Toast.LENGTH_SHORT)
                    }
                }
            }

        }

    }
}