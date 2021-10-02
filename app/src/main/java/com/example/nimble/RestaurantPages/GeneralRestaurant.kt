package com.example.nimble.RestaurantPages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.nimble.R
import com.example.nimble.adapters.GridAdapterRestaurants
import com.example.nimble.adapters.TagsAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.MainMenu
import com.example.nimble.profile.ProfileActivity
import com.example.qr_good_app.QrActivity
import com.squareup.picasso.Picasso

class GeneralRestaurant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_restaurant)
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        val icon = findViewById<ImageView>(R.id.backgroundImage)
        val title = findViewById<TextView>(R.id.titleRestaurant)
        val menulist = findViewById<GridView>(R.id.optionsMenu)
        val tagsGrid = findViewById<GridView>(R.id.tagsGrid)
        val description = findViewById<TextView>(R.id.descriptionofrestaurant)
        val homeButton = findViewById<Button>(R.id.homebutton)
        val mapsButton = findViewById<Button>(R.id.mapsbutton)
        val profileButton = findViewById<Button>(R.id.profilebutton)
        val locationMap = findViewById<ImageView>(R.id.locationPhoto)
        val backButton = findViewById<Button>(R.id.backButtonGeneralRestaurant)
        locationMap.setImageResource(theList.getLocationMap())
        description.text = theList.getStreet()
        val reservationBtn = findViewById<Button>(R.id.toReserveBtn)

        /***
         * Picasso testing
         */
//        Toast.makeText(this, Toast.LENGTH_SHORT).show()
        Picasso.get().load(theList.getPageBackground()).into(icon)


        /**
         *
         * */

        var tags = ArrayList<String>()
        tags.add(theList.getPriceRange())
        tags.add(theList.getRestaurantType())
        tags.add(theList.getPayMethod())

        var myAdapter = TagsAdapter(this, tags)
        tagsGrid.adapter = myAdapter
        setGeneralThings(theList)
        backButton.setOnClickListener {
            finish()
        }
//        Database.runUpdate("""
//            INSERT INTO tbl_tables (restaurant_id,is_reserved,position)
//            VALUES ('${theList.getId()}','0','la geam');
//        """.trimIndent())
//        Toast.makeText(this, theList.getId().toString(), Toast.LENGTH_SHORT).show()
//        Database.runUpdate("""
//            INSERT INTO tbl_tables (restaurant_id,is_reserved,position)
//            VALUES ('${theList.getId()}','0','la geam');
//        """.trimIndent())
//        Toast.makeText(this, theList.getId().toString(), Toast.LENGTH_SHORT).show()
//        Database.runUpdate("""
//            INSERT INTO tbl_tables (restaurant_id,is_reserved,position)
//            VALUES ('${theList.getId()}','0','centrale');
//        """.trimIndent())
//        Toast.makeText(this, theList.getId().toString(), Toast.LENGTH_SHORT).show()
//        Database.runUpdate("""
//            INSERT INTO tbl_tables (restaurant_id,is_reserved,position)
//            VALUES ('${theList.getId()}','0','terasa');
//        """.trimIndent())
        var optionsList = arrayOf("Meniu", "Imagini", "Recenzii", "Oferte")
        var resourcesList = arrayListOf<Int>(
            R.drawable.ic_menu,

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
                intent = Intent(this, ImagesActivity::class.java)
            else if (position == 2)
                intent = Intent(this, ReviewsActivity::class.java)
            else if (position == 3)
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
        reservationBtn.setOnClickListener {
            var intent = Intent()
            intent = Intent(this, ReservationActivity::class.java)
            intent.putExtra("LIST", theList)
            startActivity(intent)
        }
    }

    private fun setGeneralThings(theList: RestaurantsClass) {
        var theType = theList.getRestaurantType()
        var thePrice = 1
        var thePayMethod = 0
//        if()

    }
}