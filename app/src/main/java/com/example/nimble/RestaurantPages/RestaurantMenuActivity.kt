package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nimble.R
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import com.example.nimble.database.Database
import com.example.nimble.entities.ProductClass
import com.example.nimble.entities.RestaurantsClass
import androidx.recyclerview.widget.DividerItemDecoration

import FoodAdapter
import ShowedFoodAdapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.*
import com.example.nimble.adapters.CategoriesOfMenuAdapter
import com.example.nimble.user.user


class RestaurantMenuActivity : AppCompatActivity() {
    var categoriesArray = ArrayList<String>()
    var foodArray = ArrayList<ProductClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        //data from previous activity

        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass

        //elements
        var confirmBtn = findViewById<Button>(R.id.btConfirmOrder)
        var back = findViewById<Button>(R.id.btBackMenu)
        var foodList = findViewById<RecyclerView>(R.id.rvFoodList)
        var categoryList = findViewById<RecyclerView>(R.id.rvTypesOfFoodList)
        //data
        var id = theList.getId() /// current restaurant id
        getFood(id)
        //get the food of each restaurant

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
        var adapter = FoodAdapter(foodArray, foodArray)
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
        confirmBtn.setOnClickListener {
            ShowPopup(adapter)

        }
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
        for (i in foodArray.indices) {
            var type = foodArray[i].getCategoryOfProduct()
            var newCategory = 1
            for (x in categoriesArray.indices) {
                if (type == categoriesArray[x])
                    newCategory = 0
            }
            if (newCategory == 1)
                categoriesArray.add(type)
        }
    }

    fun onItemClick(position: Int) {

        var foodList = findViewById<RecyclerView>(R.id.rvFoodList)

        var new_foodList = ArrayList<ProductClass>()
        for (i in foodArray.indices) {
            if (foodArray[i].getCategoryOfProduct() == categoriesArray[position])
                new_foodList.add(foodArray[i])
        }
        var adapter = FoodAdapter(new_foodList, foodArray)
        foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodList.adapter = adapter
    }

    fun ShowPopup(originalAdapter: FoodAdapter) {
        val myDialog: Dialog = Dialog(this)
        myDialog.setContentView(R.layout.pop_up_confirm_order)
        var new_foodList = myDialog.findViewById<RecyclerView>(R.id.ShowAddedDishesRecList)
        val closeButton = myDialog.findViewById<Button>(R.id.ConfirmOrderBtn)
        val backButton = myDialog.findViewById<Button>(R.id.BackButtonOrders)
        //we have all the dishes
        //we take all those that have the .getHowMany() over 1
        //we put them in the array,we put the array in the adapter
        //we override the onClikFunction

        var pickedFoodList = ArrayList<ProductClass>()
        for (i in foodArray.indices) {
            if (foodArray[i].getHowManyAdded() > 0)
                pickedFoodList.add(foodArray[i])
        }
        var adapter = ShowedFoodAdapter(pickedFoodList, foodArray)
        new_foodList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        new_foodList.adapter = adapter



        closeButton.setOnClickListener {
            if (pickedFoodList.size == 0)
            else {
                Toast.makeText(this, "Order has been made", Toast.LENGTH_SHORT).show()

                myDialog.dismiss()
                finish()
            }
        }
        backButton.setOnClickListener {
            originalAdapter.notifyDataSetChanged()
            myDialog.dismiss()
        }

        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

}