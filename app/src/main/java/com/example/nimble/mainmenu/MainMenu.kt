package com.example.nimble.mainmenu

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import android.net.wifi.WifiManager
import com.example.nimble.loading.LoadingActivity
import android.net.NetworkInfo

import android.net.ConnectivityManager
import androidx.constraintlayout.solver.state.State


class MainMenu : AppCompatActivity(), ProductsAdapter.onItemClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var locationManager: LocationManager
    var respectedGPS = false
    var latitude = 0.0
    var longitude = 0.0
    var RestaurantsList = ArrayList<RestaurantsClass>()
    var OffertsList = ArrayList<OffertsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val mapsButton = findViewById<Button>(R.id.btMaps)
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var offertsList = findViewById<ListView>(R.id.lvOfferts)
        val categoryList = findViewById<GridView>(R.id.gvCategory)
        val searchBar = findViewById<Button>(R.id.btSearch)
        val mainButton = findViewById<Button>(R.id.btHome)
        var getRes = findViewById<Button>(R.id.btGetRestaurants)
        val profileButton = findViewById<Button>(R.id.btProfile)
        var recommendedRestaurants = findViewById<RecyclerView>(R.id.rvRecommended)
        var BtnScanner = findViewById<Button>(R.id.btScanner)
        val closeRestaurants = findViewById<Button>(R.id.btClose)
        var offers = findViewById<Button>(R.id.btOfferts)
        var categories = findViewById<Button>(R.id.btCategories)
        val layout = findViewById<LinearLayout>(R.id.llScrollable)
        val params = layout.layoutParams
        // Gets the layout params that will allow you to resize the layout
        BtnScanner.setOnClickListener {
            var intent = Intent(this, QrActivity::class.java)
            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)

        }
        searchBar.setOnClickListener() {

            val intent = Intent(this, SearchActiviy::class.java)
            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)

        }

        mapsButton.setOnClickListener {
            var intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        mapsButton.isEnabled = true
        mainButton.isEnabled = true
        if (checkInternet() == true) {
            checkLocation()
            params.height = RestaurantsList.size * 550
            //enabled for the moment
            var numbersMap = mutableMapOf("one" to 9000)
            var index = 0
            while (index < RestaurantsList.size) {
                numbersMap.put(RestaurantsList[index].getTitle(), index)
                index++
            }
            index = 0
            //click listeneres

            var myListAdapter = MyListAdapter(this, RestaurantsList)
            listView.adapter = myListAdapter
            var myListAdapter1 = OffertsAdapter(this, OffertsList)
            offertsList.adapter = myListAdapter1
            var categoriesList = ArrayList<CategoriesClass>()
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
            var arrayOfCategoriesNames = arrayListOf<String>(
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
                val params = layout.layoutParams
                params.height = OffertsList.size * 700
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
                val params = layout.layoutParams
                params.height = categoriesList.size * 350
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
                val params = layout.layoutParams
                params.height = RestaurantsList.size * 550
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
        } else {
            Toast.makeText(this, "Please turn on the internet", Toast.LENGTH_SHORT).show()
            var getRes = findViewById<Button>(R.id.btGetRestaurants)
            try {
                getRes.setOnClickListener {

                    if (checkInternet() == true) {

                        checkLocation()
                        Database.connect()
                        var intent = Intent(this, MainMenu::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            } catch (e: java.lang.Exception) {

            }
        }
    }

    private fun prepareRestaurantsData() {
        var bgPageOfRestaurantsArray = arrayListOf<Int>(
            R.drawable.bg_maps_casa_piratilor,
            R.drawable.bg_maps_marty,
            R.drawable.bg_maps_klaus,
            R.drawable.bg_maps_papion,
            R.drawable.bg_maps_pizza_hut,
            R.drawable.bg_maps_kfc
        )
        var locationMapArray = arrayListOf<Int>(
//            R.drawable.bg_maps_casa_piratilor,
//            R.drawable.bg_maps_marty,
//            R.drawable.bg_maps_klaus,
//            R.drawable.bg_maps_papion,
//            R.drawable.bg_maps_pizza_hut,
//            R.drawable.bg_maps_kfc
        )

        //aici pun un while,iau valori din baza de date,si le pun in RestaurantsList

        val res = Database.runQuery(
            """
            SELECT name,reviews_no,rating,lat,long,address,id,bgpage FROM tbl_restaurants;
        """.trimIndent()
        )

        while (res!!.next()) {
            val name: String = res.getString(1)
            val reviews: Int = res.getInt(2)
            val rating = res.getDouble(3)
            var lat = res.getDouble(4)
            var long = res.getDouble(5)
            var address = res.getString(6)
            var id = res.getInt(7)
            var bg = "none"
            if (res.getString(8) != null)
                bg = res.getString(8)
            Log.i("lat", lat.toString())
            Log.i("long", long.toString())
            var restaurants = RestaurantsClass(
                name,
                reviews,
                rating,
                R.drawable.logo_restaurant_1,
                R.drawable.bg_simple_casa_piratilor,
                lat,
                long,
                arrayOf("Peste", "Supa", "Pizza", "Desert", "Racoritoare")
            )

            //klaus papion pizza hut
            restaurants.setId(id)
            //
            if (bg != "none")
                restaurants.setPageBackground(bg)

            restaurants.setStreet(address)

            RestaurantsList.add(restaurants)
        }

        /// TODO This is only for the contest, change this later
        /**
         * Tucano
         * Klausen
         * The Soviet
         * O'Peter's
         * Storia
         * Che Guevara
         * Garlic
         * DOT
         * Casa Piratilor
         * Noodle Pack
         */
        RestaurantsList[0].setBackground(R.drawable.bg_banner_tucano_puerto_rico)
        RestaurantsList[0].setIcon(R.drawable.logo_tucano_puerto_rico)
        RestaurantsList[0].setPageBackground("https://i.imgur.com/VUbp1s2.jpg")
        RestaurantsList[0].setShownNameOfCloseRestaurantShown("Tucano");
        //RestaurantsList[0].setLocationMap()
        RestaurantsList[1].setBackground(R.drawable.bg_banner_klausen_burger)
        RestaurantsList[1].setIcon(R.drawable.logo_klausen_burger)
        RestaurantsList[1].setPageBackground("https://i.imgur.com/GC4rW5D.jpg")
        RestaurantsList[1].setLocationMap(R.drawable.bg_maps_klaus)
        RestaurantsList[1].setShownNameOfCloseRestaurantShown("Klausen Burger")

        RestaurantsList[2].setShownNameOfCloseRestaurantShown("The Soviet")
        RestaurantsList[2].setBackground(R.drawable.bg_banner_the_soviet)
        RestaurantsList[2].setIcon(R.drawable.logo_the_soviet)
        RestaurantsList[2].setPageBackground("https://i.imgur.com/iiiCHNL.jpg")
        //RestaurantsList[2].setLocationMap()

        RestaurantsList[3].setShownNameOfCloseRestaurantShown("O'Peter's")
        RestaurantsList[3].setBackground(R.drawable.bg_banner_opeters_pub)
        RestaurantsList[3].setIcon(R.drawable.logo_opeters_pub)
        RestaurantsList[3].setPageBackground("https://i.imgur.com/avRaxl1.jpg")
        //RestaurantsList[3].setLocationMap()
        RestaurantsList[4].setBackground(R.drawable.bg_banner_storia)
        RestaurantsList[4].setIcon(R.drawable.logo_storia)
        RestaurantsList[4].setPageBackground("https://i.imgur.com/0fx2dZW.jpg")
        RestaurantsList[4].setShownNameOfCloseRestaurantShown("Storia")
        //RestaurantsList[4].setLocationMap()
        RestaurantsList[5].setBackground(R.drawable.bg_banner_che_guevara)
        RestaurantsList[5].setIcon(R.drawable.logo_che_guevara)
        RestaurantsList[5].setPageBackground("https://i.imgur.com/0rye7lf.jpg")
        RestaurantsList[5].setShownNameOfCloseRestaurantShown("Che Guevara")
        //RestaurantsList[5].setLocationMap()
        RestaurantsList[6].setBackground(R.drawable.bg_banner_garlic)
        RestaurantsList[6].setIcon(R.drawable.logo_garlic)
        RestaurantsList[6].setPageBackground("https://i.imgur.com/71j764b.jpg")
        RestaurantsList[6].setShownNameOfCloseRestaurantShown("Garlic")

        //RestaurantsList[6].setLocationMap()
        RestaurantsList[7].setBackground(R.drawable.bg_banner_dot)
        RestaurantsList[7].setIcon(R.drawable.logo_dot)
        RestaurantsList[7].setPageBackground("https://i.imgur.com/nOEJVNY.jpg")
        RestaurantsList[7].setShownNameOfCloseRestaurantShown("DOT")
        //RestaurantsList[7].setLocationMap()
        RestaurantsList[8].setBackground(R.drawable.bg_banner_casa_piratilor)
        RestaurantsList[8].setIcon(R.drawable.logo_casa_piratilor)
        RestaurantsList[8].setPageBackground("https://i.imgur.com/D0jh4p2.jpg")
        RestaurantsList[8].setLocationMap(R.drawable.bg_maps_casa_piratilor)
        RestaurantsList[9].setBackground(R.drawable.bg_banner_noodlepack)
        RestaurantsList[9].setIcon(R.drawable.logo_noodle_pack)
        RestaurantsList[9].setPageBackground("https://i.imgur.com/glPlh6Q.jpg")
        //RestaurantsList[9].setLocationMap()

        var i = 0
        while (i < RestaurantsList.size) {

            RestaurantsList[i].setCurrentLatitude(latitude)
            RestaurantsList[i].setCurrentLongitude(longitude)
            RestaurantsList[i].reDistance()
            ++i
        }
        for (x in locationMapArray.indices) {
            RestaurantsList[x].setPageBackground(bgPageOfRestaurantsArray[x].toString())
            RestaurantsList[x].setLocationMap(locationMapArray[x])
            RestaurantsList[x].setId(x)
        }
        RestaurantsList.sortBy { it.getDistance() }
    }

    private fun setUpOffers() {
        OffertsList.add(
            OffertsClass(
                1,
                "Pizza Hut",
                R.drawable.banner_shape,
                1,
                150.5,
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


            val loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (loc != null) {
                latitude = loc.latitude
                longitude = loc.longitude
            }
            RestaurantsList.clear()
            OffertsList.clear()

            try {
                prepareRestaurantsData()

            } catch (e: java.lang.Exception) {

            }
            setUpOffers()
            respectedGPS = true
        }
    }

    fun identifyCategories(categoriesList: ArrayList<CategoriesClass>) {
        /**
         *  you give the array
         *  it makes the categoriesList the unique categories
         *  ex: Cheeseburger with category: burger,cheese
         *  ex: Chicken Burger with category: burger,chicken
         *  categoriesList becomes from burger,cheese,burger,chicken
         *  to burger,cheese,chicken
         *
         * */
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

    fun checkInternet(): Boolean {
        val conMan = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobile: NetworkInfo.State? = conMan.getNetworkInfo(0)!!.state
        val wifi: NetworkInfo.State? = conMan.getNetworkInfo(1)!!.state
        /**
         *  user can have either mobile data or wifi or both
         *  this function return if the user has internet so that it can access the database
         * */
        /**
         *   if the user has wifi or mobile data enabled return that it has internet(true)
         *   else returns false
         *
         * */
        if (mobile === NetworkInfo.State.CONNECTED || mobile === NetworkInfo.State.CONNECTING) {
            return true
            /**
             * checks mobile data
             * */
        } else if (wifi === NetworkInfo.State.CONNECTED || wifi === NetworkInfo.State.CONNECTING) {
            return true
            /**
             * checks wifi
             * */
        } else
            return false


    }
}