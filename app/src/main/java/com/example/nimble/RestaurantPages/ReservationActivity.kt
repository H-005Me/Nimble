package com.example.nimble.RestaurantPages

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.nimble.R
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.drawable.ColorDrawable

import android.widget.TextView
import com.example.nimble.adapters.TableAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.entities.TablesClass
import com.example.nimble.mainmenu.MainMenu
import org.w3c.dom.Text


class ReservationActivity : AppCompatActivity() {
    var tables = 0
    var TablesList = ArrayList<TablesClass>()
    var tablesNumber = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        getTables()
        var year = 0
        var month = 0
        var day = 0
        var hour = -1
        var minuteF = 0
        val c = Calendar.getInstance()
        val pickDateBtn = findViewById<Button>(R.id.dateButton)
        val dateTv = findViewById<TextView>(R.id.dateText)
        val pickHourBtn = findViewById<Button>(R.id.hourButton)
        val hourRes = findViewById<TextView>(R.id.hourText)
        val hourPicker = findViewById<TextView>(R.id.tablePickButton)
        var chooseTableButton = findViewById<Button>(R.id.choosingTable)
        val resData = findViewById<Button>(R.id.resDetails)
        var confirmReservation = findViewById<Button>(R.id.confirmedReservationButton)
        var confirmationInformer = findViewById<TextView>(R.id.reservationInformer)
        var dateIsPicked: Boolean = true
        var hourIsPicked: Boolean = false
        var tableIsPicked: Boolean = false
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        pickHourBtn.isEnabled = dateIsPicked
        chooseTableButton.isEnabled = hourIsPicked
        confirmReservation.isEnabled = tableIsPicked
        if (year == 0)
            year = c.get(Calendar.YEAR)
        if (month == 0)
            month = c.get(Calendar.MONTH)
        if (day == 0)
            day = c.get(Calendar.DAY_OF_MONTH)
        confirmationInformer.text =
            "You can edit the reservation or cancel it through the Profile->My reservations "
        //TODO("It needs to get an array of activity so that in each day you can see the most popular hours,and based on reservation the color
        // of the hours and days changes")
        pickDateBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val dpd =
                    DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { view: DatePicker, yearPicked: Int, monthPicked: Int, dayOfMonth: Int ->
                            year = yearPicked
                            month = monthPicked
                            day = dayOfMonth
                            dateTv.text = "$day $month $year"
                        },
                        year,
                        month,
                        day

                    )
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000

                dpd.show()
                dateTv.text = "$day $month $year"


            } else {

            }
            dateTv.text = "$day $month $year"
        }
        dateTv.text = "$day $month $year"
        hourRes.text = "Enter an hour"
        if (hour != -1)
            chooseTableButton.isEnabled = true

        pickHourBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            chooseTableButton.isEnabled = true
            val timeSetLister =
                TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    hour = hourOfDay
                    minuteF = minute
                    hourRes.text = SimpleDateFormat("HH:mm").format(cal.time)
                }

            //TODO("Disable specific hours or dates based on an array")
            TimePickerDialog(
                this,
                timeSetLister,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

        }
        if (hour != -1)
            chooseTableButton.isEnabled = true

        chooseTableButton.setOnClickListener {
            ShowPopup()
            if (tablesNumber.size > 2) {
                var text = tablesNumber.toString()
                text = text.substring(2, text.length - 1)
                chooseTableButton.text = text
                tableIsPicked = true
                confirmReservation.isEnabled = true
                confirmReservation.isEnabled = tableIsPicked
            } else {
                chooseTableButton.text = "Pick"
                confirmReservation.isEnabled = false
                tableIsPicked = false
            }

        }
        if (hour == -1)
            chooseTableButton.isEnabled = false
        else
            chooseTableButton.isEnabled = true
        resData.setOnClickListener {
            Toast.makeText(
                this,
                "$year $month $day $hour $minuteF",
                Toast.LENGTH_LONG
            ).show()
            Toast.makeText(this, "$tableIsPicked", Toast.LENGTH_SHORT).show()
        }
        confirmReservation.isEnabled = tableIsPicked
        confirmReservation.setOnClickListener {
            Toast.makeText(this, "Your reservation has been completed", Toast.LENGTH_SHORT).show()
            var name = theList.getTitle()
            var firstV = 1
            var statusV = 0
            var var3 = chooseTableButton.text
            var new_table = 3
            Database.runUpdate(
                """
            INSERT INTO tbl_orders (user_id, name, year, month, day, hour, minutes, tableselected, status,
            expired)
            VALUES ('$firstV', '$name', '$year', '$month', '$day', '$hour', '$minuteF' ,'$new_table', '$statusV','$statusV' );
        """.trimIndent()
            )

        }
    }

    private fun ShowPopup() {
        val myDialog: Dialog = Dialog(this)
        myDialog.setContentView(R.layout.tables_popup)
        val closeButton = myDialog.findViewById<Button>(R.id.close_button)
        val tablesGridList = myDialog.findViewById<GridView>(R.id.tables_list_1)
        var adapter = TableAdapter(this, TablesList)
        tablesGridList.adapter = adapter
        tablesGridList.setOnItemClickListener { parent, view, position, id ->
            if (TablesList[position].getStatus() == true)
                TablesList[position].setStatus(false)
            else
                TablesList[position].setStatus(true)
            adapter = TableAdapter(this, TablesList)
            tablesGridList.adapter = adapter
            tablesNumber = arrayListOf("")
            for (i in TablesList.indices)
                if (TablesList[i].getStatus() == true)
                    tablesNumber.add(TablesList[i].getId().toString())

        }

        closeButton.setOnClickListener {

            val chooseTableButton = findViewById<Button>(R.id.choosingTable)
            var text = tablesNumber.toString()
            if (text.length > 2)
                text = text.substring(2, text.length - 1)
            else
                text = "Pick"
            chooseTableButton.text = text
            val confirmReservation = findViewById<Button>(R.id.confirmedReservationButton)
            if (text != "Pick")
                confirmReservation.isEnabled = true
            else
                confirmReservation.isEnabled = false
            myDialog.dismiss()
        }

        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    fun getTables() {
        //TODO("aici se iau din baza de date")
        var table = TablesClass(2, 1, false)
        TablesList.add(table)
        table = TablesClass(2, 2, false)
        TablesList.add(table)
        table = TablesClass(2, 3, false)
        TablesList.add(table)
        table = TablesClass(2, 4, false)
        TablesList.add(table)
        table = TablesClass(2, 5, false)
        TablesList.add(table)
        table = TablesClass(2, 6, false)
        TablesList.add(table)
    }
}





