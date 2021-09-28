package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import com.example.nimble.adapters.ExpandableListAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.CategoriesClass
import com.example.nimble.entities.ProductClass
import com.example.nimble.entities.RestaurantsClass
import androidx.recyclerview.widget.DividerItemDecoration

import android.widget.Button
import java.security.AccessController.getContext
import com.example.nimble.Extender.ItemDecoration
import android.R

import androidx.core.content.ContextCompat
import FoodAdapter


class RestaurantMenuActivity : AppCompatActivity() {
    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nimble.R.layout.activity_restaurant_menu)

        /// I have 0 idea what this does
        /*var expandableListView=findViewById<ExpandableListView>(R.id.expand_activities_button)
        expandableListView.setAdapter(ExpandableListAdapter(this,expandableListView, header, body))*/
        var categoriesArray = ArrayList<String>()
        var foodArray = ArrayList<ProductClass>()
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        var id = theList.getId() /// current restaurant id
        var back = findViewById<Button>(com.example.nimble.R.id.backBtnMenu)
        var meals = Database.runQuery(
            """
            SELECT food_id, name, type, ingredients, price FROM tbl_food WHERE restaurant_id = $id
        """.trimIndent()
        )
        var confirmBtn = findViewById<Button>(com.example.nimble.R.id.confirmOrderButton)
        confirmBtn.setOnClickListener {
            Toast.makeText(this, "The order has been made", Toast.LENGTH_SHORT).show()
            finish()
        }
        back.setOnClickListener {
            finish()
        }
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
        var food =
            ProductClass("burger", 30.0, 500.0, com.example.nimble.R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("burger", 30.0, 500.0, com.example.nimble.R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("burger", 30.0, 500.0, com.example.nimble.R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("burger", 30.0, 500.0, com.example.nimble.R.drawable.offerts_marty_0)
        foodArray.add(food)
        food = ProductClass("burger", 30.0, 500.0, com.example.nimble.R.drawable.offerts_marty_0)
        foodArray.add(food)
        var foodList = findViewById<RecyclerView>(com.example.nimble.R.id.foodList)


        var adapter = FoodAdapter(foodArray, this)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this,
                com.example.nimble.R.drawable.recyclerview_divider
            )!!
        )
        foodList.addItemDecoration(dividerItemDecoration)
    }
}