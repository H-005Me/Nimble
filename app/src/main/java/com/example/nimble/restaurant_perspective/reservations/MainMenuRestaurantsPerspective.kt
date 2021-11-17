package com.example.nimble.restaurant_perspective.reservations

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.RestaurantPages.ReservationActivity
import com.example.nimble.RestaurantPages.getTables
import com.example.nimble.adapters.AdapterOrdersRestaurantPerspective
import com.example.nimble.adapters.TableAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.OrdersClass
import java.util.*
import kotlin.collections.ArrayList

class MainMenuRestaurantsPerspective : AppCompatActivity() {
    var commandArrays = ArrayList<OrdersClass>()
    var tables = getTables(0) /// TODO harcoded restaurant id = 0
    var nrOfTables = ArrayList<String>()

    /// TODO when the server adds a table as occupied, it needs an userid
    val restaurantId = 0

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
            val firstName = nameOfUser.getString(1)
            val lastName = nameOfUser.getString(2)

            var itworks = OrdersClass(name, year, month, day, hour, minutes, table, status)
            itworks.setUserName("$lastName $firstName")
            itworks.setRemarks(remarks)
            itworks.setId(orderdid)
            commandArrays.add(itworks)
        }

        var ordersAdapter = AdapterOrdersRestaurantPerspective(commandArrays, this)
        theRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        theRecyclerView.adapter = ordersAdapter

        var tableAdapter = TableAdapter(this, tables)
        var tablesGrid = findViewById<GridView>(R.id.tablesGrid)

        tablesGrid.adapter = tableAdapter

        tablesGrid.setOnItemClickListener { parent, view, position, id ->
            /// toggle table status
            val tableId = position+1

            if (tables[position].getStatus() == 0) { /// table changed to occupied
                tables[position].setStatus(1)
                /// write in the db
                Database.runUpdate("""
                    UPDATE tbl_tables SET is_reserved = 1 WHERE restaurant_id = $restaurantId AND table_id = $tableId 
                """.trimIndent())
            }

            else if (tables[position].getStatus() == 1) { /// table changed to free
                tables[position].setStatus(0)
                /// write in the db
                Database.runUpdate("""
                    UPDATE tbl_tables SET is_reserved = 0 WHERE restaurant_id = $restaurantId AND table_id = $tableId
                """.trimIndent())
            }
        }
    }


    fun showPopUp(command: Int, position: Int) {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Confirm")
        builder.setMessage("Are you sure?")
        builder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                var id = commandArrays[position].getId()
                if (command == 1)
                    Database.runUpdate(
                        """
                        UPDATE tbl_orders SET status = '4' WHERE command_id='$id';
                    """.trimIndent()
                    )
                else if (command == 2)
                    Database.runUpdate(
                        """
                        UPDATE tbl_orders SET status = '3' WHERE command_id='$id';
                    """.trimIndent()
                    )
                dialog.dismiss()
            })

        builder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing
                dialog.dismiss()
            })

        val alert: android.app.AlertDialog? = builder.create()
        alert!!.show()

    }
}
