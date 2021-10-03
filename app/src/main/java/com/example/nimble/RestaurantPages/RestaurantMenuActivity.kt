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
import android.content.Intent
import android.widget.Adapter
import com.example.nimble.adapters.CategoriesOfMenuAdapter


class RestaurantMenuActivity : AppCompatActivity() {
    var categoriesArray = ArrayList<String>()
    var foodArray = ArrayList<ProductClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        //data from previous activity

        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass

        //elements
        var confirmBtn = findViewById<Button>(R.id.confirmOrderButton)
        var back = findViewById<Button>(R.id.backBtnMenu)
        var foodList = findViewById<RecyclerView>(R.id.foodList)
        var categoryList = findViewById<RecyclerView>(R.id.typeOfFoodList)
        //data
        var id = theList.getId() /// current restaurant id
        getFood(id)
        //get the food of each restaurant

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

        categoriesArray.add("Populare")
        categoriesArray.add("Pizza")
        categoriesArray.add("Burger")
        categoriesArray.add("Salate")


        var adapter = FoodAdapter(foodArray, this, foodArray)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager.VERTICAL
        )
        foodList.addItemDecoration(dividerItemDecoration)

        var adapter1 = CategoriesOfMenuAdapter(categoriesArray, this)
        categoryList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryList.adapter = adapter1
    }

    fun getFood(id: Int) {
        var meals = Database.runQuery(
            """
                SELECT food_id, name, type, ingredients, price,quantity FROM tbl_food WHERE restaurant_id = $id
            """.trimIndent()
        )
        while (meals!!.next()) {
            val foodId = meals.getInt(1)
            val name = meals.getString(2)
            val type = meals.getString(3)
            val ingredients = meals.getString(4)
            val price = meals.getDouble(5)
            var quantity = meals.getDouble(6)
            var newCategory = 1
            for (x in categoriesArray.indices) {
                if (type == categoriesArray[x])
                    newCategory = 0
            }
            if (newCategory == 1)
                categoriesArray.add(type)
            var food =
                ProductClass(name, price, quantity, R.drawable.ic_launcher_background, type, foodId)
        }
        var index = 0
        var food = ProductClass("Burger", 24.5, 50.0, R.drawable.bg_categ_burger, "Burger", index)
        ++index
        foodArray.add(food)
        food =
            ProductClass("Cheese Burger", 23.5, 50.0, R.drawable.bg_categ_burger, "Burger", index)
        ++index
        foodArray.add(food)
        food = ProductClass("Supa de pui", 26.9, 50.0, R.drawable.bg_categ_burger, "Supa", index)
        ++index
        foodArray.add(food)
        food = ProductClass(
            "Piept de pui",
            25.7,
            50.0,
            R.drawable.bg_categ_burger,
            "Carne de pui",
            index
        )
        ++index
        foodArray.add(food)
        food =
            ProductClass("Coca-Cola", 7.5, 50.0, R.drawable.bg_categ_burger, "Racoritoare", index)
        ++index
        foodArray.add(food)
        food =
            ProductClass("Pizza margherita", 24.5, 50.0, R.drawable.bg_categ_burger, "Pizza", index)
        ++index
        foodArray.add(food)

    }

    fun onItemClick(position: Int) {

        var foodList = findViewById<RecyclerView>(R.id.foodList)
        var new_foodList = ArrayList<ProductClass>()
        for (i in foodArray.indices) {
            if (foodArray[i].getCategoryOfProduct() == categoriesArray[position])
                new_foodList.add(foodArray[i])
        }
        var adapter = FoodAdapter(new_foodList, this, foodArray)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
    }
}