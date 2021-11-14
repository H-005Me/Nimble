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
import com.example.nimble.adapters.GridAdapterRestaurants
import com.example.nimble.loading.LoadingActivity
import com.example.nimble.mainmenu.MainMenu
import com.example.nimble.user.user

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val homeButton = findViewById<Button>(R.id.btHome)
        val profilePic = findViewById<ImageView>(R.id.userimage)
        val profileName = findViewById<TextView>(R.id.username)
        val userList = findViewById<ListView>(R.id.userlist)

        // aici se fac schimbarile
        var optionsList = arrayOf("Comenzile mele", "Rezervarile mele", "Profilul meu", "Log out")
        var resourcesList = arrayListOf<Int>(
            R.drawable.ic_menu_yellow,
            R.drawable.ic_calendar_yellow,
            R.drawable.ic_profile_yellow,
            R.drawable.ic_logout_yellow
        )
        // restul e automat


        var myGridAdapter = GridAdapterRestaurants(this, optionsList, resourcesList)
        userList.adapter = myGridAdapter
        userList.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent()
            if (position == 0)
                intent = Intent(this, SeeOrdersActivity::class.java) /// TODO comenzi
            else if (position == 1)
                intent = Intent(this, SeeReservationsActity::class.java)
            else if (position == 2)
                intent = Intent(this, ImagesActivity::class.java)
            else if (position == 3) {
                intent = Intent(this, LoadingActivity::class.java)
                /// Delete all other activities (shows the loading activity for a split second for some reason
                /// activites are still deleted in login activity, so this is not that necessary
                //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
        profileName.text = user.getFullName()
        profilePic.setImageResource(user.getProfilePicture())


        homeButton.setOnClickListener {
            var intent = Intent(this, MainMenu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

    }
}