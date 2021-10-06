package com.example.nimble.restaurant_perspective

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.adapters.AdapterOrdersRestaurantPerspective
import com.example.nimble.database.Database
import com.example.nimble.entities.OrdersClass
import com.example.nimble.user.user

class MainMenuRestaurantsPerspective : AppCompatActivity() {
    var commandArrays = ArrayList<OrdersClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_restaurants_perspective)
        val theRecyclerView = findViewById<RecyclerView>(R.id.ordersList)
        val res = Database.runQuery(
            """
            SELECT user_id,name,year,month,day,hour,minutes,tableselected,status,expired,remarks,command_id FROM tbl_orders WHERE name='Casa Piratilor';
        """.trimIndent()
        )
        var db_works = false
        //code is where to take values from the database
        //to put them in the adapter

        while (res!!.next()) {

            val userid = res.getInt(1)
            val name = res.getString(2)
            val year = res.getInt(3)
            val month = res.getInt(4)
            val day = res.getInt(5)
            val hour = res.getInt(6)
            val minutes = res.getInt(7)
            val table = res.getInt(8)
            val status = res.getInt(9)
            val expired = res.getInt(10)
            val remarks = res.getString(11)
            val orderdid = res.getInt(12)
            val nameOfUser = Database.runQuery(
                """
                            SELECT firstName,lastName 
                             FROM tbl_users WHERE id='$userid';
            """.trimIndent()
            )
            nameOfUser!!.next()
            val firstName = nameOfUser!!.getString(1)
            val lastName = nameOfUser.getString(2)

            var itworks = OrdersClass(name, year, month, day, hour, minutes, table, status)
            itworks.setUserName("$lastName $firstName")
            itworks.setRemarks(remarks)
            itworks.setId(orderdid)
            commandArrays.add(itworks)
        }

        var adapter = AdapterOrdersRestaurantPerspective(commandArrays)
        theRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        theRecyclerView.adapter = adapter
    }
}