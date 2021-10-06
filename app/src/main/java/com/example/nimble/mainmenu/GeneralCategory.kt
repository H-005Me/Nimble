package com.example.nimble.mainmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.nimble.R
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.adapters.MyListAdapter
import com.example.nimble.entities.RestaurantsClass

class GeneralCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_category)
        var theList = intent.getSerializableExtra("LIST") as ArrayList<RestaurantsClass>
        var theIndices = intent.getSerializableExtra("INDICES") as ArrayList<Int>
        var theNewList = ArrayList<RestaurantsClass>()
        var theName = intent.getSerializableExtra("NAME") as String
        var backButton = findViewById<Button>(R.id.btBackGeneralCategory)
        for (each in theIndices) {
            theNewList.add(theList[each])
        }
        backButton.setOnClickListener {
            finish()
        }
        var theListView = findViewById<ListView>(R.id.lvCategoryL)
        var theNameView = findViewById<TextView>(R.id.tvCategoryName)
        var new_Adapter = MyListAdapter(this, theNewList)
        theListView.adapter = new_Adapter
        theNameView.text = theName
        theListView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, GeneralRestaurant::class.java)
            intent.putExtra("LIST", theNewList[position])
            startActivity(intent)
        }
    }
}