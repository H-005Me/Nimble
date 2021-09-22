package com.example.nimble.mainmenu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.adapters.GridAdapter
import com.example.nimble.adapters.MyListAdapter
import com.example.nimble.adapters.OffertsAdapter
import com.example.nimble.adapters.ProductsAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.*
import com.example.nimble.maps_activity.MapsActivity
import com.example.nimble.profile.ProfileActivity
import com.example.qr_good_app.QrActivity


var RestaurantsList = ArrayList<RestaurantsClass>()
var OffertsList = ArrayList<OffertsClass>()
class MainMenu : AppCompatActivity(), ProductsAdapter.onItemClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var locationManager: LocationManager
    var respectedGPS = false
    var arrayOfCategoriesNames = arrayListOf<String>()
    var latitude = 0.0
    var longitude = 0.0
    var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        val mapsButton = findViewById<Button>(R.id.mapsbutton)
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var offertsList = findViewById<ListView>(R.id.offerts)
        val categoryList = findViewById<GridView>(R.id.category)
        val searchBar = findViewById<Button>(R.id.searchButton)
        val mainButton = findViewById<Button>(R.id.homebutton)
        var getRes = findViewById<Button>(R.id.getRestaurants)
        val profileButton = findViewById<Button>(R.id.profilebutton)
        var recommendedRestaurants = findViewById<RecyclerView>(R.id.recommendedlist)
        var BtnScanner = findViewById<Button>(R.id.btnscanner)
        val closeRestaurants = findViewById<Button>(R.id.closeButton)
        var offers = findViewById<Button>(R.id.offersButton)
        var categories = findViewById<Button>(R.id.categoriesButton)

        checkLocation()

        //disabled for the moment
        mapsButton.isEnabled = true
        ///
        mainButton.isEnabled = true

        var numbersMap = mutableMapOf("one" to 9000)
        var index = 0
        while (index < RestaurantsList.size) {
            numbersMap.put(RestaurantsList[index].getTitle(), index)
            index++
        }
        index = 0
        //click listeneres
        searchBar.setOnClickListener() {

            val intent = Intent(this, SearchActiviy::class.java)
            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)

        }
        BtnScanner.setOnClickListener {
            var intent = Intent(this, QrActivity::class.java)
            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)

        }
        mapsButton.setOnClickListener {
            var intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        var myListAdapter = MyListAdapter(this, RestaurantsList)
        listView.adapter = myListAdapter
        var myListAdapter1 = OffertsAdapter(this, OffertsList)
        offertsList.adapter = myListAdapter1
        var categoriesList = ArrayList<CategoriesClass>()
//        for (index in RestaurantsList.indices) {
//            var newcategoriesList = RestaurantsList[index].getCategories()
//            for (new_index1 in newcategoriesList.indices) {
//                var isThere = false
//                for (new_index in categoriesList.indices)
//                    if (newcategoriesList[new_index1] == categoriesList[new_index].getName()) {
//                        isThere = true
//                        categoriesList[new_index].addIndices(index)
//                    }
//                if (isThere == false) {
//                    categoriesList.add(CategoriesClass(newcategoriesList[new_index1], 0))
//                    categoriesList[categoriesList.size - 1].addIndices(index)
//                }
//            }
//        }
        identifyCategories(categoriesList)
        getRes.setOnClickListener {
            checkLocation()
            if (respectedGPS == true) {
                listView.isEnabled = true
                listView.visibility = View.VISIBLE
                getRes.isEnabled = false
                getRes.visibility = View.GONE
                identifyCategories(categoriesList)
                var myGridAdapter = GridAdapter(this, categoriesList)
                categoryList.adapter = myGridAdapter
                for (each in categoriesList.indices)
                    categoriesList[each].setIndices(removeDuplicates(categoriesList[each].getTheIndices()))
                var arrayOfCategoriesNames =
                    arrayListOf<String>(
                        "Burger",
                        "Desert",
                        "Peste",
                        "Pizza",
                        "Cartofi",
                        "Salata",
                        "Saorma",
                        "Supa"
                    )
                var arrayOfCategoriesIcons = arrayListOf<Int>(
                    R.drawable.bg_categ_burger,
                    R.drawable.bg_categ_dessert,
                    R.drawable.bg_categ_fish,
                    R.drawable.bg_categ_pizza,
                    R.drawable.bg_categ_potatos,
                    R.drawable.bg_categ_salads,
                    R.drawable.bg_categ_shawarma,
                    R.drawable.bg_categ_soup
                )
                //to change the categories photos
                for (x in categoriesList.indices) {
                    for (y in arrayOfCategoriesNames.indices)
                        if (categoriesList[x].getName() == arrayOfCategoriesNames[y])
                            categoriesList[x].setPhoto(arrayOfCategoriesIcons[y])
                }
                var myRecAdapter = ProductsAdapter(RestaurantsList, this)
                recommendedRestaurants.adapter = myRecAdapter
            }
        }
        //fiecare categorie are nume si un  icon; acestea sunt
        var arrayOfCategoriesNames =
            arrayListOf<String>(
                "Burger",
                "Desert",
                "Peste",
                "Pizza",
                "Cartofi",
                "Salata",
                "Saorma",
                "Supa"
            )
        var arrayOfCategoriesIcons = arrayListOf<Int>(
            R.drawable.bg_categ_burger,
            R.drawable.bg_categ_dessert,
            R.drawable.bg_categ_fish,
            R.drawable.bg_categ_pizza,
            R.drawable.bg_categ_potatos,
            R.drawable.bg_categ_salads,
            R.drawable.bg_categ_shawarma,
            R.drawable.bg_categ_soup
        )
        //to change the categories photos
        for (x in categoriesList.indices) {
            for (y in arrayOfCategoriesNames.indices)
                if (categoriesList[x].getName() == arrayOfCategoriesNames[y])
                    categoriesList[x].setPhoto(arrayOfCategoriesIcons[y])
        }
        for (each in categoriesList.indices)
            categoriesList[each].setIndices(removeDuplicates(categoriesList[each].getTheIndices()))
        var myGridAdapter = GridAdapter(this, categoriesList)
        categoryList.adapter = myGridAdapter

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
            myGridAdapter = GridAdapter(this, categoriesList)
            categoryList.adapter = myGridAdapter
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
            intent.putExtra("NAME", categoriesList[position].getName())
            startActivity(intent)
        }
        offertsList.setOnItemClickListener { parent, view, position, id ->

            var intent = Intent(this, GeneralRestaurant::class.java)
            var theRestaurant = RestaurantsList[0]
            for (x in RestaurantsList.indices) {
                if (OffertsList[position].getName() == RestaurantsList[x].getTitle())
                    theRestaurant = RestaurantsList[x]
            }
            intent.putExtra("LIST", theRestaurant)
            intent.putExtra("CATEGORIES", categoriesList)
            startActivity(intent)
        }
        profileButton.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun prepareRestaurantsData() {
        var bgPageOfRestaurantsArray = arrayListOf<Int>(
            R.drawable.bg_simple_casa_piratilor,
            R.drawable.bg_simple_marty,
            R.drawable.bg_simple_klaus,
            R.drawable.bg_simple_papion,
            R.drawable.bg_simple_pizza_hut,
            R.drawable.bg_simple_kfc
        )
        var locationMapArray = arrayListOf<Int>(
            R.drawable.location_casa_piratilor,
            R.drawable.location_marty,
            R.drawable.location_klaus,
            R.drawable.logo_papion,
            R.drawable.logo_pizza_hut,
            R.drawable.logo_kfc
        )
        //the current order:
        //casa Piratilor
        //marty
        //klaus
        //papion
        //pizza hut
        //kfc

        var restaurants = RestaurantsClass(
            "Casa Piratilor",

            1500,
            4.5,
            R.drawable.logo_casa_piratilor,
            R.drawable.bg_simple_casa_piratilor,
            46.754761489348375, 23.549074595438668,
            arrayOf(
                MenuClass(
                    "Cartofi",
                    arrayOf(ProductClass("Cartofi prajiti", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Cartofi",
                    arrayOf(ProductClass("Cartofi dulci", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Supa",
                    arrayOf(ProductClass("Supa de pui", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Desert",
                    arrayOf(ProductClass("Inghetata", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        restaurants.setPageBackground(bgPageOfRestaurantsArray[0])

        restaurants.setStreet("Strada Răvașului")
        RestaurantsList.add(restaurants)

        restaurants = RestaurantsClass(
            "Marty",

            1500,
            4.5,
            R.drawable.logo_marty,
            R.drawable.bg_simple_marty,
            46.771753344308706, 23.58713642701307,
            arrayOf(
                MenuClass(
                    "Pizza",
                    arrayOf(ProductClass("Salami", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Pizza",
                    arrayOf(ProductClass("Capriciosa", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Desert",
                    arrayOf(ProductClass("Tiramisu", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Peste",
                    arrayOf(ProductClass("File de somon", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )

        restaurants.setPageBackground(bgPageOfRestaurantsArray[1])

        restaurants.setStreet("Piața Muzeului")
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "Klausen Burger",

            1500,
            4.5,
            R.drawable.logo_klaus,
            R.drawable.bg_simple_klaus,
            46.77303059272974, 23.589206542353935,
            arrayOf(
                MenuClass(
                    "Burger",
                    arrayOf(ProductClass("Double Cheese Burger", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Cartofi",
                    arrayOf(ProductClass("Cartofi preajiti", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        restaurants.setStreet("Strada Regele Ferdinand")
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "La Papion",

            1500,
            4.5,
            R.drawable.logo_papion,
            R.drawable.bg_simple_papion,
            46.57051053103731, 23.785073506109164,
            arrayOf(
                MenuClass(
                    "Desert",
                    arrayOf(ProductClass("Briosa", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Saorma",
                    arrayOf(ProductClass("Saorma de pui", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        restaurants.setPageBackground(R.drawable.bg_simple_papion)

        restaurants.setStreet("Piața 1 Decembrie, Turda")
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "Pizza Hut", 150, 4.9,
            R.drawable.logo_pizza_hut,
            R.drawable.bg_simple_pizza_hut,
            46.77219094166171, 23.62581808448148,
            arrayOf(
                MenuClass(
                    "Pizza",
                    arrayOf(ProductClass("Margherita", 25.0, 250.0, R.drawable.ic_launcher_background))
                ), MenuClass(
                    "Peste",
                    arrayOf(ProductClass("Dorada", 25.0, 250.0, R.drawable.ic_launcher_background))
                )
            )
        )
        restaurants.setStreet("Str. Alexandru Vaida-Voievod")
        restaurants.setPageBackground(R.drawable.bg_simple_pizza_hut)

        RestaurantsList.add(restaurants)

        restaurants = RestaurantsClass(
            "KFC", 150, 4.9,
            R.drawable.logo_kfc,
            R.drawable.bg_simple_kfc,
            46.77236134944, 23.626857336072423,
            arrayOf(
                MenuClass(
                    "Cartofi",
                    arrayOf(ProductClass("Cartofi prajiti", 25.0, 250.0, R.drawable.ic_launcher_background))
                ),
                MenuClass(
                    "Burger",
                    arrayOf(ProductClass("Zinger", 25.0, 250.0, R.drawable.ic_launcher_background))
                ),
                MenuClass(
                    "Salata",
                    arrayOf(ProductClass("Salata cu pui", 25.5, 150.0, R.drawable.ic_launcher_background))
                ),
                MenuClass(
                    "Saorma",
                    arrayOf(ProductClass("Twister de pui", 5.00, 0.5, R.drawable.ic_launcher_background))
                )
            )
        )
        restaurants.setStreet("Str. Alexandru Vaida-Voievod")
        restaurants.setPageBackground(R.drawable.bg_simple_kfc)
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
            Log.i("lat", lat.toString())
            Log.i("long", long.toString())
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
                            "Supa",
                            arrayOf(
                                ProductClass(
                                    "Ciorba de Burta",
                                    25.0,
                                    250.0,
                                    R.drawable.ic_launcher_background
                                )
                            )
                        ), MenuClass(
                            "Peste",
                            arrayOf(
                                ProductClass(
                                    "Ton",
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
        for (x in RestaurantsList.indices) {
            RestaurantsList[x].setPageBackground(bgPageOfRestaurantsArray[x])
            RestaurantsList[x].setLocationMap(locationMapArray[x])
        }
        RestaurantsList.sortBy { it.getDistance() }
    }
    private fun setUpOffers() {
        OffertsList.add(
            OffertsClass(
                1,
                "Pizza Hut",
                R.drawable.bg_offer_pizza_hut,
                1,
                150.5,
                2021,
                10,
                25,
                5,
                30
            )
        )
        OffertsList.add(
            OffertsClass(
                1,
                "KFC",
                R.drawable.bg_offer_kfc,
                1,
                550.0,
                2021,
                10,
                25,
                5,
                30
            )
        )
        OffertsList.add(
            OffertsClass(
                1,
                "Marty",
                R.drawable.bg_offer_marty,
                1,
                150.0,
                2021,
                10,
                25,
                5,
                30
            )
        )

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

    fun checkLocation() {
        try {
            if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                    latitude = loc!!.latitude
                    longitude = loc.longitude

                    RestaurantsList.clear()
                    OffertsList.clear()
                    prepareRestaurantsData()
                    setUpOffers()
                    respectedGPS = true
                } else {
                    Toast.makeText(this, "gps disabled", Toast.LENGTH_SHORT).show()
                    respectedGPS = false
                }


            } else {
                Toast.makeText(this, "wtf", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    101
                );
            }

        } catch (ex: Exception) {
            Toast.makeText(this, "$ex", Toast.LENGTH_SHORT).show()
            val loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (loc != null) {
                latitude = loc.latitude
                longitude = loc.longitude
            }
            RestaurantsList.clear()
            OffertsList.clear()
            prepareRestaurantsData()
            setUpOffers()
            respectedGPS = true
        }
    }

    fun identifyCategories(categoriesList: ArrayList<CategoriesClass>) {
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
    }
}

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == locationPermissionCode) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//                respectedGPS = true
//            } else {
//                respectedGPS = false
//                var someList = findViewById<ListView>(R.id.CloseRestaurants)
//                someList.isEnabled = false
//                someList.visibility = View.VISIBLE
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
//    private fun isPermissionGranted() : Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//    private fun isGpsEnabled():Boolean{
//
//    }







