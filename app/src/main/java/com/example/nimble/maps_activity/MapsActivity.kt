/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */

package com.example.nimble.maps_activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.RestaurantPages.RestaurantMenuActivity
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.restaurant_perspective.reservations.MainMenuRestaurantsPerspective


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

// displaying the user's location.
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback

class MapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback
{
    private lateinit var map: GoogleMap
    private val TAG = MapsActivity::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near the Googleplex.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        //These coordinates represent the lattitude and longitude of the Googleplex.
        val latitude = 46.7546248
        val longitude = 23.5490349
        val zoomLevel = 15f
        val overlaySize = 100f

        val homeLatLng = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))

        val marker1 = map.addMarker(MarkerOptions().position(homeLatLng))

        setMapStyle(map)
        enableMyLocation()

        marker1.showInfoWindow()

        map.setOnMarkerClickListener(this)

        //map.setOnInfoWindowClickListener

        /**
         * See CustomInfoWindowForGoogleMap class below
         */
        //map.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this))
    }

    // Initializes contents of Activity's standard options menu. Only called the first time options
    // menu is displayed.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    // Called whenever an item in your options menu is selected.
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // Allows map styling and theming to be customized.
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    // Checks that users have given permission
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Checks if users have given their location and sets location enabled if so.
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    // Callback for the result from requesting permissions.
    // This method is invoked for every call on requestPermissions(android.app.Activity, String[],
    // int).
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    /// TODO
    override fun onMarkerClick(marker: Marker?): Boolean
    {
        Log.d("dbg", "Marker clicked $marker")

        var intent = Intent(this, GeneralRestaurant::class.java)

        /// TODO hardcoded, change some stuff, etc
        var currentRestaurant = RestaurantsClass("Casa Piratilor", 1666, 3.9, R.drawable.logo_restaurant_1, R.drawable.bg_simple_casa_piratilor,
            46.754644, 23.549038, arrayOf("Peste", "Supa", "Pizza", "Desert", "Racoritoare"))
        currentRestaurant.setBackground(R.drawable.bg_banner_casa_piratilor)
        currentRestaurant.setIcon(R.drawable.logo_casa_piratilor)
        currentRestaurant.setPageBackground("https://i.imgur.com/D0jh4p2.jpg")
        currentRestaurant.setLocationMap(R.drawable.bg_maps_casa_piratilor)
        currentRestaurant.setStreet("Str. Ravasului 16")

        intent.putExtra("LIST", currentRestaurant)
        startActivity(Intent(intent))

        return false
    }
}


/**
 * idfk wtf this is, but it DOESN'T work
 */
/*class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.maps_infowindow, null)
    var btOpenMenu: Button? = null

    private fun rendowWindowText(marker: Marker, view: View) {

        btOpenMenu = view.findViewById<Button>(R.id.openMenu)

        btOpenMenu!!.setOnClickListener {
            Log.d("dbg", "it works")
        }
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}*/

