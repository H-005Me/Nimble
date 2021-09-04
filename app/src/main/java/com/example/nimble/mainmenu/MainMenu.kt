package com.example.nimble.mainmenu

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import android.location.LocationListener
import android.support.annotation.NonNull

import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.entities.MenuClass
import com.example.nimble.entities.ProductClass


import com.example.nimble.adapters.MyListAdapter
import kotlin.collections.ArrayList
import com.example.nimble.adapters.GridAdapter
import com.example.nimble.adapters.OffertsAdapter
import com.example.nimble.adapters.ProductsAdapter
import com.example.nimble.entities.CategoriesClass
import android.app.Activity
import android.os.SystemClock
import android.util.Log
import com.example.nimble.database.Database
import com.example.nimble.profile.ProfileActivity
import java.lang.Exception
import java.security.Provider
import java.util.*
import kotlin.collections.LinkedHashSet


var RestaurantsList = ArrayList<RestaurantsClass>()

class MainMenu : AppCompatActivity(), ProductsAdapter.onItemClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var locationManager: LocationManager
    var respectedGPS = true
    private val locationPermissionCode = 2

    var latitude = 0.0
    var longitude = 0.0

    var myLocation: Location? = null
    fun getLocation(): Location? {

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000,
            5f,
            MyLocationListener()
        )
        return myLocation
    }
    inner class MyLocationListener : LocationListener {
        constructor() {
            myLocation = Location("me")
            longitude = myLocation!!.longitude
            latitude = myLocation!!.latitude
            var i = 0
            Log.i("latitude", "$latitude")
            Log.i("longitude", "$longitude")
            while (i < RestaurantsList.size) {

                RestaurantsList[i].setCurrentLatitude(latitude)
                RestaurantsList[i].setCurrentLongitude(longitude)
                RestaurantsList[i].reDistance()
                ++i
            }
            RestaurantsList.sortBy { it.getDistance() }

        }

        override fun onLocationChanged(location: Location) {
            myLocation = location
            latitude = myLocation!!.latitude
            longitude = myLocation!!.longitude
            var i = 0
            while (i < RestaurantsList.size) {

                RestaurantsList[i].setCurrentLatitude(latitude)
                RestaurantsList[i].setCurrentLongitude(longitude)
                RestaurantsList[i].reDistance()
                ++i
            }
            RestaurantsList.sortBy { it.getDistance() }
        }

        override fun onProviderDisabled(provider: String) {

        }

        override fun onProviderEnabled(provider: String) {
            myLocation = Location(provider)
            longitude = myLocation!!.longitude
            latitude = myLocation!!.latitude
            var i = 0
            Log.i("latitude", "$latitude")
            Log.i("longitude", "$longitude")
            while (i < RestaurantsList.size) {

                RestaurantsList[i].setCurrentLatitude(latitude)
                RestaurantsList[i].setCurrentLongitude(longitude)
                RestaurantsList[i].reDistance()
                ++i
            }
            RestaurantsList.sortBy { it.getDistance() }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }
    }
    var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager


        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var offertsList = findViewById<ListView>(R.id.offerts)
        val categoryList = findViewById<GridView>(R.id.category)
        val searchBar = findViewById<Button>(R.id.searchButton)
        val mainButton = findViewById<Button>(R.id.homebutton)
        var getRes = findViewById<Button>(R.id.getRestaurants)
        val profileButton = findViewById<Button>(R.id.profilebutton)
        var recommendedRestaurants = findViewById<RecyclerView>(R.id.recommendedlist)


        try {


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

            ) {
                val loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                latitude = loc!!.latitude
                longitude = loc.longitude
                getLocation()
                longitude = loc.longitude
                latitude = loc.latitude
                prepareRestaurantsData()
                respectedGPS = true

            } else {
                respectedGPS = false
            }
        } catch (ex: Exception) {
            prepareRestaurantsData()
            listView.isEnabled = false
            offertsList.isEnabled = false
            categoryList.isEnabled = false
            listView.visibility = View.GONE
            offertsList.visibility = View.GONE
            categoryList.visibility = View.GONE
            respectedGPS = false

        }
        mainButton.isEnabled = true

        mainButton.setOnClickListener {

            var rest = RestaurantsList[0].getCurrentLatitude()
            var rest1 = RestaurantsList[0].getCurrentLongitude()
            var rest2 = RestaurantsList[0].getDistance()

        }
        var numbersMap = mutableMapOf("one" to 9000)
        var index = 0
        while (index < RestaurantsList.size) {
            numbersMap.put(RestaurantsList[index].getTitle(), index)
            index++
        }
        index = 0

        searchBar.setOnClickListener()
        {

            val intent = Intent(this, SearchActiviy::class.java)
            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)

        }

        var myListAdapter = MyListAdapter(this, RestaurantsList)
        listView.adapter = myListAdapter
        var myListAdapter1 = OffertsAdapter(this, RestaurantsList)
        offertsList.adapter = myListAdapter1
        var categoriesList = ArrayList<CategoriesClass>()
        for (index in RestaurantsList.indices) {
            var newcategoriesList = RestaurantsList[index].getCategories()
            for (new_index1 in newcategoriesList.indices) {
                var isThere = false
                for (new_index in categoriesList.indices)
                    if (newcategoriesList[new_index1] == categoriesList[new_index].getName()) {
                        isThere = true
                        categoriesList[new_index].addIndices(index)
                    }
                if (isThere == false) {
                    categoriesList.add(CategoriesClass(newcategoriesList[new_index1], 0))
                    categoriesList[categoriesList.size - 1].addIndices(index)
                }
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }

        getRes.setOnClickListener {

            var REQUEST = 111
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
            if ((ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED)
            ) {
                if (respectedGPS == true) {
                    try {
                        getLocation()
                        val loc =
                            locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                        latitude = loc!!.latitude
                        longitude = loc.longitude
                        prepareRestaurantsData()

                    } catch (ex: Exception) {
                        Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show()
                }
            }
        }
        for (each in categoriesList.indices)
            categoriesList[each].setIndices(removeDuplicates(categoriesList[each].getTheIndices()))
        var myGridAdapter = GridAdapter(this, categoriesList)
        categoryList.adapter = myGridAdapter
        val closeRestaurants = findViewById<Button>(R.id.closeButton)
        var offers = findViewById<Button>(R.id.offersButton)
        var categories = findViewById<Button>(R.id.categoriesButton)
        if (respectedGPS == true) {
            listView.isEnabled = true
            listView.visibility = View.VISIBLE
            getRes.isEnabled = false
            getRes.visibility = View.GONE

        } else {
            listView.isEnabled = false
            listView.visibility = View.GONE
        }
        offertsList.isEnabled = false
        categoryList.isEnabled = false
        offertsList.visibility = View.GONE
        categoryList.visibility = View.GONE
        offers.setOnClickListener()
        {
            listView.isEnabled = false
            categoryList.isEnabled = false
            offertsList.isEnabled = true
            listView.visibility = View.GONE
            offertsList.visibility = View.VISIBLE
            categoryList.visibility = View.GONE
            offertsList.smoothScrollToPosition(0)
            getRes.isEnabled = false
            getRes.visibility = View.GONE
        }
        categories.setOnClickListener()
        {

            listView.isEnabled = false
            offertsList.isEnabled = false
            categoryList.isEnabled = true
            listView.visibility = View.GONE
            offertsList.visibility = View.GONE
            categoryList.visibility = View.VISIBLE
            categoryList.smoothScrollToPosition(0)
            getRes.isEnabled = false
            getRes.visibility = View.GONE
        }
        closeRestaurants.setOnClickListener()
        {

            if (respectedGPS == true) {
                listView.isEnabled = true
                listView.visibility = View.VISIBLE
                getRes.isEnabled = false
                getRes.visibility = View.GONE
            } else {
                listView.isEnabled = false
                listView.visibility = View.GONE
                getRes.isEnabled = true
                getRes.visibility = View.VISIBLE
            }
            offertsList.visibility = View.GONE
            categoryList.visibility = View.GONE
            offertsList.isEnabled = false
            categoryList.isEnabled = false
            listView.smoothScrollToPosition(0)
        }

        RestaurantsList.sortBy { it.getDistance() }
        var myRecAdapter = ProductsAdapter(RestaurantsList, this)
        recommendedRestaurants.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedRestaurants.adapter = myRecAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, GeneralRestaurant::class.java)
            intent.putExtra("LIST", RestaurantsList[position])
            intent.putExtra("CATEGORIES", categoriesList)
            startActivity(intent)
        }
        categoryList.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, GeneralCategory::class.java)
            intent.putExtra("LIST", RestaurantsList)
            intent.putExtra("INDICES", categoriesList[position].getTheIndices())
            startActivity(intent)
        }
        offertsList.setOnItemClickListener { parent, view, position, id ->

            var intent = Intent(this, GeneralRestaurant::class.java)
            intent.putExtra("LIST", RestaurantsList[position])
            intent.putExtra("CATEGORIES", categoriesList)
            startActivity(intent)
        }
        profileButton.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun prepareRestaurantsData() {
        var restaurants = RestaurantsClass(
            "Casa Piratilor",

            1500,
            4.5,
            R.drawable.logo_casa_piratilor,
            R.drawable.cover_casa_piratilor,
            46.754761489348375, 23.549074595438668,
            arrayOf(
                MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        RestaurantsList.add(restaurants)

        restaurants = RestaurantsClass(
            "Marty",

            1500,
            4.5,
            R.drawable.logo_marty,
            R.drawable.background_logo,
            46.77303059272974, 23.589206542353935,
            arrayOf(
                MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "Klausen Burger",

            1500,
            4.5,
            R.drawable.logo_klaus,
            R.drawable.background_logo,
            46.77303059272974, 23.589206542353935,
            arrayOf(
                MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Picanta",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "La Papion",

            1500,
            4.5,
            R.drawable.logo_papion,
            R.drawable.background_logo,
            46.57051053103731, 23.785073506109164,
            arrayOf(
                MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Normala",
                    arrayOf(ProductClass("sushi", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        RestaurantsList.add(restaurants)
        //aici pun un while,iau valori din baza de date,si le pun in RestaurantsList

        val res = Database.runQuery(
            """
            SELECT name,reviews_no,rating,lat,long FROM tbl_restaurants;
        """.trimIndent()
        )

            while (res!!.next()) {
                val name: String = res!!.getString(1)
                val reviews: Int = res!!.getInt(2)
                val rating = res.getDouble(3)
                var lat = res.getDouble(4)
                var long = res.getDouble(5)
                restaurants = RestaurantsClass(
                    name,
                    reviews,
                    rating,
                    R.drawable.ic_launcher_background,
                    R.drawable.background_logo,
                    lat,
                    long,
                    arrayOf(
                        MenuClass(
                            "Normala",
                            arrayOf(
                                ProductClass(
                                    "sushi",
                                    25.0,
                                    250.0,
                                    R.drawable.ic_launcher_background
                                )
                            )
                        ), MenuClass(
                            "Normala",
                            arrayOf(
                                ProductClass(
                                    "sushi",
                                    25.0,
                                    250.0,
                                    R.drawable.ic_launcher_background
                                )
                            )
                        )
                    )
                )

                RestaurantsList.add(restaurants)
            }

        var i = 0
        while (i < RestaurantsList.size) {

            RestaurantsList[i].setCurrentLatitude(latitude)
            RestaurantsList[i].setCurrentLongitude(longitude)
            RestaurantsList[i].reDistance()
            ++i
        }
        RestaurantsList.sortBy { it.getDistance() }
    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, GeneralRestaurant::class.java)
        intent.putExtra("LIST", RestaurantsList[position])
        startActivity(intent)
    }

    fun <T> removeDuplicates(list: ArrayList<T>?): ArrayList<T> {
        val set: Set<T> = LinkedHashSet(list)
        return ArrayList(set)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                respectedGPS = true
            } else {
                respectedGPS = false
                var someList = findViewById<ListView>(R.id.CloseRestaurants)
                someList.isEnabled = false
                someList.visibility = View.VISIBLE
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

}




