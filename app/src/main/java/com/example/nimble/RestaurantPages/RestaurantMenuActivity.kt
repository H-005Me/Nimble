package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nimble.R
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
import FoodAdapter
import com.example.nimble.adapters.CategoriesOfMenuAdapter


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
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        var id = theList.getId() /// current restaurant id
        var back = findViewById<Button>(R.id.backBtnMenu)
        var meals = Database.runQuery(
            """
            SELECT food_id, name, type, ingredients, price FROM tbl_food WHERE restaurant_id = $id
        """.trimIndent()
        )
        var confirmBtn = findViewById<Button>(R.id.confirmOrderButton)
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
        var food = ProductClass("Cheese Burger", 20.0, 500.0, R.drawable.bg_categ_burger)
        foodArray.add(food)
        food = ProductClass("Margherita", 30.0, 500.0, R.drawable.bg_categ_burger)
        foodArray.add(food)
        food = ProductClass("Omleta", 15.0, 500.0, R.drawable.bg_categ_burger)
        foodArray.add(food)
        food = ProductClass("Prosciutto", 30.0, 500.0, R.drawable.bg_categ_burger)
        foodArray.add(food)
        food = ProductClass("Platou de pui", 130.0, 500.0, R.drawable.bg_categ_burger)
        foodArray.add(food)

        var categories = ArrayList<String>()
        categories.add("Populare")
        categories.add("Pizza")
        categories.add("Burger")
        categories.add("Salate")


        var foodList = findViewById<RecyclerView>(R.id.foodList)
        var categoryList = findViewById<RecyclerView>(R.id.typeOfFoodList)

        var adapter = FoodAdapter(foodArray, this)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager.VERTICAL
        )
        foodList.addItemDecoration(dividerItemDecoration)

        var adapter1 = CategoriesOfMenuAdapter(categories, this)
        categoryList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryList.adapter = adapter1
    }
}