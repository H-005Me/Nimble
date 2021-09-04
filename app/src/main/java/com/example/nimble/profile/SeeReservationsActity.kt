package com.example.nimble.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.adapters.OrdersAdapterForListViews
import com.example.nimble.database.Database
import com.example.nimble.entities.OrdersClass

class SeeReservationsActity : AppCompatActivity() {
    var ordersList = ArrayList<OrdersClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_see_reservation_activity)
        //primeste datele de la o baza de date
        //afiseaza comenzile
        //numele restaurantului,data,ora,minutul,masa,completata sau in asteptare
        var orderShower = findViewById<ListView>(R.id.orderslist)
        val res = Database.runQuery(
            """
            SELECT user_id,name,year,month,day,hour,minutes,tableselected,status,expired FROM tbl_orders;
        """.trimIndent()
        )
        var db_works = false
        if (res != null)
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
                ordersList.add(OrdersClass(name, year, month, day, hour, minutes, table, status))
                db_works = true
            }
        val orders_adapter = OrdersAdapterForListViews(this, ordersList)
        orderShower.adapter = orders_adapter
        orderShower.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, EditOrdersActivity::class.java)
            intent.putExtra("LIST", ordersList[position])
            startActivity(intent)
        }

    }


}