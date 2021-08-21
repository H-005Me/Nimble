package com.example.nimble.mainmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R


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


var RestaurantsList = ArrayList<RestaurantsClass>()
class MainMenu : AppCompatActivity(),ProductsAdapter.onItemClickListener{
    private lateinit var linearLayoutManager: LinearLayoutManager
    var RestaurantsList = ArrayList<RestaurantsClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // valorile le iau din baza de date
        prepareRestaurantsData()

        var numbersMap = mutableMapOf("one" to 9000)


        var index = 0
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
        for (each in categoriesList.indices)
            categoriesList[each].setIndices(removeDuplicates(categoriesList[each].getTheIndices()))
        var myGridAdapter = GridAdapter(this, categoriesList)
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

        var recommendedRestaurants = findViewById<RecyclerView>(R.id.recommendedlist)
        var myRecAdapter = ProductsAdapter(RestaurantsList, this)
        recommendedRestaurants.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedRestaurants.adapter = myRecAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(applicationContext, "$view", Toast.LENGTH_SHORT).show()
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
        var restaurants = RestaurantsClass(
            "Casa Piratilor",
            4.0,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
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
            4.3,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
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
            3.9,
            1500,
            4.5,
            R.drawable.ic_launcher_background,
            R.drawable.background_logo,
            arrayOf(MenuClass("Normala",arrayOf(ProductClass("sushi", 25.0,250.0,R.drawable.ic_launcher_background))),MenuClass("Picanta",
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
}

