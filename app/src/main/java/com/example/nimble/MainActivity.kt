package com.example.nimble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView


import com.example.mainmenu.RestaurantsClass
import com.example.nimble.RestaurantPages.CasaPiratilorPage
import com.example.nimble.RestaurantPages.KlausenBurgerPage

import com.example.nimble.mainmenu.SearchActiviy
import example.javatpoint.com.kotlincustomlistview.MyListAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
    var names=arrayOf<String>("Casa Piratilor","Marty","La Papion","Klausen Burger","Central")
    var distances=arrayOf<Double>(4.4,3.3,5.5,5.3,8.2)
    // mai vin fotografiile
    //Reviews,logo,reviews,
    //new 1 line
     var RestaurantsList = mutableListOf<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // valorile le iau din baza de date
        var i=0
        var j=1
        while(i<names.size) {
            j=i+1
            while(j<names.size)
            {
                if (distances[i] > distances[j])
                {
                    var aux=distances[i]
                    var aux1=names[j]
                distances[i] = distances[j]
                names[i] = names[j]
                    distances[j]=aux
                    names[j]=aux1
                    ++j
                }

            j++
            }
            i++
        }
        val listView = findViewById<ListView>(R.id.CloseRestaurants)
        var listView1= findViewById<ListView>(R.id.offerts)
        var listView2 = findViewById<ListView>(R.id.category)
        val searchBar= findViewById<Button>(R.id.searchButton)
        searchBar.setOnClickListener()
        {
            val intent= Intent(this, SearchActiviy::class.java)
            startActivity(intent)
        }
        var myListAdapter= MyListAdapter(this,names,distances,true)
        listView.adapter=myListAdapter
         myListAdapter= MyListAdapter(this,names,distances,true)
        listView2.adapter=myListAdapter
        myListAdapter= MyListAdapter(this,names,distances,true)
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
        listView.setOnItemClickListener{parent, view, position, id ->
            if(position==0)
            {
                val intent= Intent(this, CasaPiratilorPage::class.java)
                startActivity(intent)
            }
            else if(position==1)
            {
                val inent=Intent(this, KlausenBurgerPage::class.java)
                startActivity(intent)
            }


        }


       myListAdapter= MyListAdapter(this,names,distances,true)

        recommendedRest.adapter=myListAdapter
        recommendedRest.setRotation(90F)

        //recommendedRest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
    private fun prepareRestaurantsData() {

        var restaurants = RestaurantsClass("Casa Piratilor",4.5,1500,4.5,null,null)
        RestaurantsList.add(restaurants)


        restaurants=RestaurantsClass("Marty",4.5,1500,4.5,null,null)
        RestaurantsList.add(restaurants)
        restaurants=RestaurantsClass("Klausen Burger",4.5,1500,4.5,null,null)
        RestaurantsList.add(restaurants)
        restaurants=RestaurantsClass("La Papion",4.5,1500,4.5,null,null)
        RestaurantsList.add(restaurants)
        //aici pun un while,iau valori din baza de date,si le pun in RestaurantsList
    }
}
