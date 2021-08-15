package com.example.nimble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.entities.MenuClass
import com.example.nimble.entities.ProductClass


import com.example.nimble.mainmenu.SearchActiviy
import example.javatpoint.com.kotlincustomlistview.MyListAdapter
import kotlin.collections.ArrayList
import com.example.nimble.mainmenu.GridAdapter
import example.javatpoint.com.kotlincustomlistview.OffertsAdapter

var RestaurantsList = ArrayList<RestaurantsClass>()
class MainActivity : AppCompatActivity() {
    var names = arrayOf<String>("Casa Piratilor", "Marty", "La Papion", "Klausen Burger", "Central")
    var distances = arrayOf<Double>(4.4, 3.3, 5.5, 5.3, 8.2)
    // mai vin fotografiile
    //Reviews,logo,reviews,
    //new 1 line
    private lateinit var linearLayoutManager: LinearLayoutManager

    var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // valorile le iau din baza de date
        prepareRestaurantsData()
        var index = 0
        var numbersMap = mutableMapOf("one" to 9000)



        RestaurantsList.sortBy { d1 -> d1.getDistance() }
        while (index < RestaurantsList.size) {
            numbersMap.put(RestaurantsList[index].getTitle(), index)
            index++
        }
        index = 0
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var offetsList = findViewById<ListView>(R.id.offerts)
        val categoryList = findViewById<GridView>(R.id.category)
        val searchBar = findViewById<Button>(R.id.searchButton)
        searchBar.setOnClickListener()
        {
            val intent = Intent(this, SearchActiviy::class.java)

            intent.putExtra("LIST", RestaurantsList)
            startActivity(intent)
        }
        /**
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
         **/






        var myListAdapter = MyListAdapter(this, RestaurantsList)
        listView.adapter = myListAdapter
        var myListAdapter1 = OffertsAdapter(this, RestaurantsList)
        offetsList.adapter = myListAdapter1
        var myGridAdapter = GridAdapter(this, RestaurantsList)
        categoryList.adapter = myGridAdapter
        val closeRestaurants = findViewById<Button>(R.id.closeButton)
        var offers = findViewById<Button>(R.id.offersButton)
        var categories = findViewById<Button>(R.id.categoriesButton)
        listView.isEnabled = true
        offetsList.isEnabled = false
        categoryList.isEnabled = false

        listView.visibility = View.VISIBLE
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
        }
        closeRestaurants.setOnClickListener()
        {
            listView.isEnabled = true
            listView.visibility = View.VISIBLE
            offetsList.visibility = View.GONE
            categoryList.visibility = View.GONE
            offetsList.isEnabled = false
            categoryList.isEnabled = false
            listView.smoothScrollToPosition(0)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(applicationContext, "$view", Toast.LENGTH_SHORT).show()
            var intent=Intent(this, GeneralRestaurant::class.java)
            startActivity(intent)
        }





        //recommendedRest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
    private fun prepareRestaurantsData() {

        var restaurants = RestaurantsClass(
            "Casa Piratilor",
            4.0,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
           arrayOf(MenuClass("Mancare normala",arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))),MenuClass("Mancare normala",
               arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))
           )
        ))
        RestaurantsList.add(restaurants)

        restaurants = RestaurantsClass(
            "Marty",
            4.3,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
            arrayOf(MenuClass("Mancare normala",arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))),MenuClass("Mancare normala",
                arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))
            )
            )

        )
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "Klausen Burger",
            3.9,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
            arrayOf(MenuClass("Mancare normala",arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))),MenuClass("Mancare normala",
                arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))
            )
            )
        )
        RestaurantsList.add(restaurants)
        restaurants = RestaurantsClass(
            "La Papion",
            1.4,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
            arrayOf(MenuClass("Mancare normala",arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))),MenuClass("Mancare normala",
                arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))
            )
            )
        )
        RestaurantsList.add(restaurants)
        //aici pun un while,iau valori din baza de date,si le pun in RestaurantsList
    }
    fun getNamesPositino(name:String): Int {
        for (item in RestaurantsList.indices)
            if (RestaurantsList[item].getTitle() == name)
                return item
        return -1
    }
}

