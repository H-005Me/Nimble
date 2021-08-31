package com.example.nimble.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nimble.R
import com.example.nimble.RestaurantPages.ImagesActivity
import com.example.nimble.RestaurantPages.ReservationActivity
import com.example.nimble.RestaurantPages.RestaurantMenuActivity
import com.example.nimble.RestaurantPages.ReviewsActivity
import com.example.nimble.adapters.GridAdapterRestaurants
import com.example.nimble.loading.LoadingActivity
import com.example.nimble.mainmenu.MainMenu

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val homeButton = findViewById<Button>(R.id.homebutton)
        val profilePic = findViewById<ImageView>(R.id.userimage)
        val profileName = findViewById<TextView>(R.id.username)
        val userList = findViewById<ListView>(R.id.userlist)
        var optionsList = arrayOf("Comenzile mele", "Rezervarile mele", "Profilul meu", "Log out")
        var myGridAdapter = GridAdapterRestaurants(this, optionsList)
        userList.adapter = myGridAdapter
        userList.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent()
            if (position == 0)
                intent = Intent(this, RestaurantMenuActivity::class.java)
            else if (position == 1)
                intent = Intent(this, ReservationActivity::class.java)
            else if (position == 2)
                intent = Intent(this, ImagesActivity::class.java)
            else if (position == 3)
                intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
        }





        homeButton.setOnClickListener {
            var intent = Intent(this, MainMenu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

    }
}