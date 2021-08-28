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
import android.util.Log


var RestaurantsList = ArrayList<RestaurantsClass>()

class MainMenu : AppCompatActivity(), ProductsAdapter.onItemClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var locationManager: LocationManager
    var respectedGPS = true
    private val locationPermissionCode = 2

    var myLocation: Location? = null

    inner class MyLocationListener : LocationListener {
        constructor() {
            myLocation = Location("me")
//            myLocation!!.longitude = longitude
//            myLocation!!.latitude = latitude
            longitude = myLocation!!.longitude
            latitude = myLocation!!.latitude
            var i = 0
            while (i < RestaurantsList.size) {

                RestaurantsList[i].setCurrentLatitude(latitude)
                RestaurantsList[i].setCurrentLongitude(longitude)
                RestaurantsList[i].reDistance()
                ++i
            }
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
        }

        override fun onProviderDisabled(provider: String) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }
    }

    companion object {
        var latitude = 0.0
        var longitude = 0.0

    }


    var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // valorile le iau din baza de date
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var offetsList = findViewById<ListView>(R.id.offerts)
        val categoryList = findViewById<GridView>(R.id.category)
        val searchBar = findViewById<Button>(R.id.searchButton)
        val mainButton = findViewById<Button>(R.id.homebutton)
        var getRes = findViewById<Button>(R.id.getRestaurants)
        prepareRestaurantsData()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation()
            respectedGPS = true
        } else {
            respectedGPS = false
        }



        mainButton.setOnClickListener {

            var rest = RestaurantsList[0].getCurrentLatitude()
            var rest1 = RestaurantsList[0].getCurrentLongitude()
            var rest2 = RestaurantsList[0].getDistance()
            Toast.makeText(this, "$rest2", Toast.LENGTH_SHORT).show()
        }

//        while (i<900000)
//        {
//            Toast.makeText(this, "$", Toast.LENGTH_LONG).show()
//            i++
//        }

        var numbersMap = mutableMapOf("one" to 9000)


        var index = 0
        RestaurantsList.sortByDescending { it.getDistance() }
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
        offetsList.adapter = myListAdapter1
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
                respectedGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (respectedGPS == true) {
                    listView.isEnabled = true
                    listView.visibility = View.VISIBLE
                    getRes.isEnabled = false
                    getRes.visibility = View.GONE
                } else {
                    Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show()
                }
            } else {

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
        offetsList.isEnabled = false
        categoryList.isEnabled = false
        offetsList.visibility = View.GONE
        categoryList.visibility = View.GONE
        offers.setOnClickListener()
        {
            listView.isEnabled = false
            categoryList.isEnabled = false
            offetsList.isEnabled = true
            listView.visibility = View.GONE
            offetsList.visibility = View.VISIBLE
            categoryList.visibility = View.GONE
            offetsList.smoothScrollToPosition(0)
            getRes.isEnabled = false
            getRes.visibility = View.GONE
        }
        categories.setOnClickListener()
        {
            listView.isEnabled = false
            offetsList.isEnabled = false
            categoryList.isEnabled = true
            listView.visibility = View.GONE
            offetsList.visibility = View.GONE
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
            offetsList.visibility = View.GONE
            categoryList.visibility = View.GONE
            offetsList.isEnabled = false
            categoryList.isEnabled = false
            listView.smoothScrollToPosition(0)
        }

        var recommendedRestaurants = findViewById<RecyclerView>(R.id.recommendedlist)
        var myRecAdapter = ProductsAdapter(RestaurantsList, this)
        recommendedRestaurants.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedRestaurants.adapter = myRecAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            var toShow = RestaurantsList[position].getCurrentLatitude()
            var toShow1 = RestaurantsList[position].getCurrentLongitude()
            var toShow2 = RestaurantsList[position].getDistance()
            Toast.makeText(this, "$toShow2", Toast.LENGTH_SHORT).show()
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
    }
    private fun prepareRestaurantsData() {
        getLocation()
        var restaurants = RestaurantsClass(
            "Casa Piratilor",

            1500,
            4.5,
            R.drawable.ic_launcher_background,
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
            R.drawable.ic_launcher_background,
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
            R.drawable.ic_launcher_background,
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
            R.drawable.ic_launcher_background,
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

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )

        } else {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                MyLocationListener()
            )
        }

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
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

}




