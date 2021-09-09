package com.example.nimble.RestaurantPages

import android.content.Intent
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
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        val icon = findViewById<ImageView>(R.id.backgroundImage)
        icon.setImageResource(theList.getBackground())
        val title = findViewById<TextView>(R.id.titleRestaurant)
        val menulist = findViewById<GridView>(R.id.optionsMenu)
        val tagsGrid = findViewById<GridView>(R.id.tagsGrid)

        var myAdapter = TagsAdapter(this, theList.getCategories())
        tagsGrid.adapter = myAdapter
        tagsGrid.setOnItemClickListener { parent, view, position, id ->
            var new_intent = Intent(this, GeneralRestaurant::class.java)

        }
        var optionsList = arrayOf("Meniu", "Rezerva", "Imagini", "Recenzii", "Oferte")
        var myGridAdapter = GridAdapterRestaurants(this, optionsList)
        menulist.adapter = myGridAdapter
        title.text = theList.getTitle()
        menulist.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent()
            if (position == 0)
                intent = Intent(this, RestaurantMenuActivity::class.java)
            else if (position == 1)
                intent = Intent(this, ReservationActivity::class.java)
            else if (position == 2)
                intent = Intent(this, ImagesActivity::class.java)
            else if (position == 3)
                intent = Intent(this, ReviewsActivity::class.java)
            else if (position == 4)
                intent = Intent(this, ReviewsActivity::class.java)
            intent.putExtra("LIST", theList)
            startActivity(intent)
        }


    }
}