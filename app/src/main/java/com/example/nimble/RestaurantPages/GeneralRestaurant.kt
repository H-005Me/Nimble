package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.nimble.R
import com.example.nimble.adapters.GridAdapterRestaurants
import com.example.nimble.adapters.TagsAdapter
import com.example.nimble.entities.RestaurantsClass

class GeneralRestaurant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_restaurant)
        var theList=intent.getSerializableExtra("LIST") as RestaurantsClass
        val icon = findViewById<ImageView>(R.id.backgroundImage)

        val title= findViewById<TextView>(R.id.titleRestaurant)
        val menulist=findViewById<GridView>(R.id.optionsMenu)
        val tagsGrid=findViewById<GridView>(R.id.tagsGrid)

        var myAdapter =TagsAdapter(this,theList.getCategories())
        tagsGrid.adapter=myAdapter
        var optionsList= arrayOf("Meniu","Rezerva","Imagini","Recenzii")
        var myGridAdapter = GridAdapterRestaurants(this, optionsList)
        menulist.adapter = myGridAdapter
        title.text=theList.getTitle()


    }
}