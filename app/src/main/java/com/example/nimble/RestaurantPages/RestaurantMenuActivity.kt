package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nimble.R
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import android.view.View
import android.widget.ExpandableListView
import com.example.nimble.adapters.ExpandableListAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.RestaurantsClass


class RestaurantMenuActivity : AppCompatActivity() {
    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        /// I have 0 idea what this does
        /*var expandableListView=findViewById<ExpandableListView>(R.id.expand_activities_button)
        expandableListView.setAdapter(ExpandableListAdapter(this,expandableListView, header, body))*/

        var extras = intent.extras
        var theList = extras!!.get("LIST") as RestaurantsClass

        var id = theList.getIndex() /// current restaurant id
        var meals = Database.runQuery("""
            SELECT food_id, name, type, ingredients, price FROM tbl_food WHERE restaurant_id = $id
        """.trimIndent())

        while (meals!!.next()) {
            val foodId = meals.getInt(1)
            val name = meals.getString(2)
            val type = meals.getString(3)
            val ingredients = meals.getString(4)
            val price = meals.getDouble(5)

            println("$foodId ; $name ; $type ; $ingredients ; $price")
        }
    }
}