package com.example.nimble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView


import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainmenu.RestaurantsClass

import com.example.nimble.mainmenu.SearchActiviy
import example.javatpoint.com.kotlincustomlistview.MyListAdapter

class MainActivity : AppCompatActivity() {
    var names=arrayOf<String>("Casa Piratilor","Marty","La Papion","Klausen Burger","Central")
    // mai vin fotografiile
    //Reviews,logo,reviews,
    //new 1 line
    private var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // valorile le iau din baza de date
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var listView1= findViewById<ListView>(R.id.offerts)
        var listView2 = findViewById<ListView>(R.id.category)
        val searchBar= findViewById<Button>(R.id.searchButton)
        searchBar.setOnClickListener()
        {
            val intent= Intent(this, SearchActiviy::class.java)
            startActivity(intent)
        }
        var myListAdapter= MyListAdapter(this,names)
        listView.adapter=myListAdapter
         myListAdapter= MyListAdapter(this,names)
        listView2.adapter=myListAdapter
         myListAdapter= MyListAdapter(this,names)
        listView1.adapter=myListAdapter
        val closeRestaurants = findViewById<Button>(R.id.closeButton)
        var offers=findViewById<Button>(R.id.offersButton)
        var categories = findViewById<Button>(R.id.categoriesButton)
        listView.isEnabled=true
        listView1.isEnabled=false
        listView2.isEnabled=false

        listView.visibility= View.VISIBLE
        listView1.visibility= View.GONE
        listView2.visibility= View.GONE
        offers.setOnClickListener()
        {
            listView.isEnabled=false
            listView2.isEnabled=false
            listView1.isEnabled=true
            listView.visibility= View.GONE
            listView1.visibility= View.VISIBLE
            listView2.visibility= View.GONE
            listView1.smoothScrollToPosition(0)
        }
        categories.setOnClickListener()
        {
            listView.isEnabled=false
            listView1.isEnabled=false
            listView2.isEnabled=true
            listView.visibility= View.GONE
            listView1.visibility= View.GONE
            listView2.visibility= View.VISIBLE
            listView2.smoothScrollToPosition(0)
        }
        closeRestaurants.setOnClickListener()
        {
            listView.isEnabled=true
            listView.visibility= View.VISIBLE
            listView1.visibility= View.GONE
            listView2.visibility= View.GONE
            listView1.isEnabled=false
            listView2.isEnabled=false
            listView.smoothScrollToPosition(0)
        }
        val recommendedRest=findViewById<ListView>(R.id.recRestaurants)
        myListAdapter= MyListAdapter(this,names)
        recommendedRest.adapter=myListAdapter

    }
    private fun prepareRestaurantsData() {
        var restaurants = RestaurantsClass("Casa Piratilor",4.5,1500,4.5)
        RestaurantsList.add(restaurants)


        restaurants=RestaurantsClass("Marty",4.5,1500,4.5)
        RestaurantsList.add(restaurants)
        restaurants=RestaurantsClass("Klausen Burger",4.5,1500,4.5)
        RestaurantsList.add(restaurants)
        restaurants=RestaurantsClass("La Papion",4.5,1500,4.5)
        RestaurantsList.add(restaurants)
        //aici pun un while,iau valori din baza de date,si le pun in RestaurantsList
    }
}