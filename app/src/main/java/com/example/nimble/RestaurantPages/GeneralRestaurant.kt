package com.example.nimble.RestaurantPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.nimble.R
import com.example.nimble.adapters.GridAdapterRestaurants
import com.example.nimble.adapters.TagsAdapter
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.MainMenu
import com.example.nimble.profile.ProfileActivity
import com.example.qr_good_app.QrActivity

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
        val description = findViewById<TextView>(R.id.descriptionofrestaurant)
        val homeButton = findViewById<Button>(R.id.homebutton)
        val mapsButton = findViewById<Button>(R.id.mapsbutton)
        val profileButton = findViewById<Button>(R.id.profilebutton)
        val locationMap = findViewById<ImageView>(R.id.locationPhoto)
        locationMap.setImageResource(theList.getLocationMap())
        description.text = theList.getStreet()
        var myAdapter = TagsAdapter(this, theList.getCategories())
        tagsGrid.adapter = myAdapter
        tagsGrid.setOnItemClickListener { parent, view, position, id ->
            var new_intent = Intent(this, GeneralRestaurant::class.java)

        }

        var optionsList = arrayOf("Meniu", "Rezerva", "Imagini", "Recenzii", "Oferte")
        var resourcesList = arrayListOf<Int>(
            R.drawable.ic_menu,
            R.drawable.ic_calendar,
            R.drawable.ic_image,
            R.drawable.ic_star,
            R.drawable.ic_offer,
            R.drawable.ic_home
        )
        var myGridAdapter = GridAdapterRestaurants(this, optionsList, resourcesList)
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
        homeButton.setOnClickListener {
            intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
        mapsButton.setOnClickListener {
            intent = Intent(this, QrActivity::class.java)
            startActivity(intent)
            finish()
        }
        profileButton.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}