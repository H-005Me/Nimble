package com.example.nimble.profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.adapters.OrdersAdapterForListViews
import com.example.nimble.database.Database
import com.example.nimble.entities.OrdersClass
import com.example.nimble.user.user
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class SeeReservationsActity : AppCompatActivity() {
    var ordersList = ArrayList<OrdersClass>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_see_reservation_activity)
        //primeste datele de la o baza de date
        //afiseaza comenzile
        //numele restaurantului,data,ora,minutul,masa,completata sau in asteptare
        //
        //aici trebuie sa fie un user_id
        //
        var orderShower = findViewById<ListView>(R.id.orderslist)
        val res = Database.runQuery(
            """
            SELECT user_id,name,year,month,day,hour,minutes,tableselected,status,expired,remarks,command_id FROM tbl_orders;
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
                val remarks = res.getString(11)
                val orderdid = res.getInt(12)
                var itworks = OrdersClass(name, year, month, day, hour, minutes, table, status)

                itworks.setRemarks(remarks)
                itworks.setId(orderdid)
                if (userid == user.getId())
                    ordersList.add(itworks)

                db_works = true
            }
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        //
        //
        /***
         *
         * 0 -> still in progress
         * 1 -> completed
         * 2 -> expired
         * 3 -> declined
         * 4 -> accepted
         * ***/

        for (x in ordersList.indices) {

            if (ordersList[x].getYear() < year && ordersList[x].getStatus() == 0)
                ordersList[x].setStatus(2)
            else if (ordersList[x].getYear() == year && ordersList[x].getStatus() == 0) {
                if (ordersList[x].getMonth() < month)
                    ordersList[x].setStatus(2)
                else if (ordersList[x].getMonth() == month)
                    if (ordersList[x].getDay() < day)
                        ordersList[x].setStatus(2)
                    else if (ordersList[x].getDay() == day)
                        if (ordersList[x].getHour() < hour)
                            ordersList[x].setStatus(2)
                        else if (ordersList[x].getHour() == hour)
                            if (ordersList[x].getMinute() + 1 <= minute)
                                ordersList[x].setStatus(2)

            }


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ordersList.sortWith(compareByDescending<OrdersClass> { it.getResponse() }.thenBy { it.getResponse() }
                .thenBy { it.getYear() }
                .thenBy { it.getMonth() }.thenBy { it.getDay() }.thenBy { it.getHour() }
                .thenBy { it.getMinute() })
        }

        ordersList.sortedByDescending { it.getResponse() }
        val orders_adapter = OrdersAdapterForListViews(this, ordersList)
        orderShower.adapter = orders_adapter
        orderShower.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, EditOrdersActivity::class.java)
            intent.putExtra("LIST", ordersList[position])
            startActivity(intent)
            finish()
        }

    }


}