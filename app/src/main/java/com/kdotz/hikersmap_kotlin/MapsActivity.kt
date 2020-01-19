package com.kdotz.hikersmap_kotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MapsActivity : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    private lateinit var latitude : TextView
    private lateinit var longitude : TextView
    private lateinit var accuracy : TextView
    private lateinit var altitude : TextView
    private lateinit var address : TextView

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latitude = findViewById(R.id.latitude)
        longitude = findViewById(R.id.Longitude)
        accuracy = findViewById(R.id.Accuracy)
        altitude = findViewById(R.id.altitude)
        address = findViewById(R.id.address)

        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            @SuppressLint("SetTextI18n")
            override fun onLocationChanged(location: Location) {
                Log.i("Location Found", location.toString())
                latitude.text = "Latitude: " + location.latitude.toString()
                longitude.text = "Latitude: " + location.longitude.toString()
                accuracy.text = "Accuracy: " + location.accuracy.toString()
                altitude.text = "Altitude: " + location.altitude.toString()

                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                address.text = "Address: " + addressList[0].getAddressLine(0).replace(",", ",\n")

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {

            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }


    }

}
