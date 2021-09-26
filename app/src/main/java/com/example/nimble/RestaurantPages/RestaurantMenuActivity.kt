package com.example.nimble.RestaurantPages

import FoodAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nimble.R
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import android.view.View
import android.widget.ExpandableListView
import com.example.nimble.adapters.ExpandableListAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.CategoriesClass
import com.example.nimble.entities.ProductClass
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
        var categoriesArray = ArrayList<String>()
        var foodArray = ArrayList<ProductClass>()
        var extras = intent.extras
        var theList = extras!!.get("LIST") as RestaurantsClass

        var id = theList.getId() /// current restaurant id
        var meals = Database.runQuery(
            """
            SELECT food_id, name, type, ingredients, price FROM tbl_food WHERE restaurant_id = $id
        """.trimIndent()
        )
//        Database.runUpdate(
//            """
//            INSERT INTO tbl_orders (user_id, name, year, month, day, hour, minutes, tableselected, status,
//            expired,remarks)
//            VALUES ('$firstV', '$name', '$year', '$month', '$day', '$hour', '$minuteF' ,'$new_table', '$statusV','$statusV','$the_remark' );
//        """.trimIndent()
        while (meals!!.next()) {
            val foodId = meals.getInt(1)
            val name = meals.getString(2)
            val type = meals.getString(3)
            val ingredients = meals.getString(4)
            val price = meals.getDouble(5)
            var is_found = 0
            println("$foodId ; $name ; $type ; $ingredients ; $price")
        }
        var food = ProductClass("sushi", 50.5, 599.9, R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("sushi", 50.5, 599.9, R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("sushi", 50.5, 599.9, R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("sushi", 50.5, 599.9, R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("sushi", 50.5, 599.9, R.drawable.offerts_marty_0)
        foodArray.add(food)
        var foodList = findViewById<RecyclerView>(R.id.foodList)
        var adapter = FoodAdapter(foodArray, this)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
    }
}