package com.example.nimble.RestaurantPages

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
import android.util.Log
import android.view.WindowManager

import android.widget.TextView
import com.example.nimble.adapters.TableAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.entities.TablesClass
import com.example.nimble.entities.isReservedToStatus
import com.example.nimble.user.user
import java.time.temporal.TemporalAdjusters.next


class ReservationActivity : AppCompatActivity() {
    var tables = 0
    var TablesList = ArrayList<TablesClass>()
    var tablesNumber = ArrayList<String>()

    val restaurantId = 0 /// TODO hardcoded restaurant id = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        TablesList = getTables(restaurantId) /// TODO hardcoded restaurant id 0
        var year = 0
        var month = 0
        var day = 0
        var hour = -1
        var minuteF = 0
        val c = Calendar.getInstance()
        val pickDateBtn = findViewById<Button>(R.id.btDate)
        val dateTv = findViewById<TextView>(R.id.dateText)
        val pickHourBtn = findViewById<Button>(R.id.btHour)
        val hourRes = findViewById<TextView>(R.id.hourText)
        val hourPicker = findViewById<TextView>(R.id.tablePickButton)
        var chooseTableButton = findViewById<Button>(R.id.btChooseTable)
        var confirmReservation = findViewById<Button>(R.id.btConfirmedResevation)
        var confirmationInformer = findViewById<TextView>(R.id.reservationInformer)
        var remarksEditor = findViewById<EditText>(R.id.remarkstext)
        var backButton = findViewById<Button>(R.id.btBackReservation)
        var dateIsPicked: Boolean = true
        var hourIsPicked: Boolean = false
        var tableIsPicked: Boolean = false
        var theList = intent.getSerializableExtra("LIST") as RestaurantsClass
        var the_remark = ""
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (remarksEditor.text.toString() == "Remark") {
            the_remark = "Nothing"
        } else {
            the_remark = remarksEditor.text.toString()
        }
        pickHourBtn.isEnabled = dateIsPicked
        chooseTableButton.isEnabled = hourIsPicked
        confirmReservation.isEnabled = tableIsPicked
        //button imageView in a gallery

        if (year == 0)
            year = c.get(Calendar.YEAR)
        if (month == 0)
            month = c.get(Calendar.MONTH)
        if (day == 0)
            day = c.get(Calendar.DAY_OF_MONTH)
        confirmationInformer.setOnClickListener {

            Toast.makeText(this, the_remark, Toast.LENGTH_SHORT).show()
        }
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
                            month += 1
                            dateTv.text = "$day $month $year"
                            month -= 1
                        },
                        year,
                        month,
                        day

                    )
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000

                dpd.show()
                month = month + 1
                dateTv.text = "$day $month $year"
                month = month - 1

            } else {

            }
            month++
            dateTv.text = "$day $month $year"
            month--
        }
        month++
        dateTv.text = "$day $month $year"
        month--
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

        confirmReservation.isEnabled = tableIsPicked

        confirmReservation.setOnClickListener {
            Toast.makeText(this, "Your reservation has been completed", Toast.LENGTH_SHORT).show()
            var name = theList.getTitle()
            var firstV = user.getId()
            var statusV = 0
            month++ /// ex: december - 12
            var var3 = chooseTableButton.text
            var the_remark = remarksEditor.text.toString()

            /// set all selected tables as occupied in the db
            for (tableNumberStr in tablesNumber) {
                /// table is occupied TODO table is occupied in an interval of time
                Database.runUpdate("""
                    UPDATE tbl_tables SET is_reserved = 1 WHERE restaurant_id = $restaurantId AND table_id = ${tableNumberStr.toInt()}
                """.trimIndent())
            }

            /// tablesStr - formatted string to be put in tbl_orders-tableselected column
            var tablesStr = createTablesStr(tablesNumber) /// tableid0;tableid1;...;tableidn;
            Database.runUpdate("""
                INSERT INTO tbl_orders (user_id, name, year, month, day, hour, minutes, tableselected, status, expired,remarks)
                VALUES ('$firstV', '$name', '$year', '$month', '$day', '$hour', '$minuteF' ,'$tablesStr', '$statusV','$statusV','$the_remark' );
            """.trimIndent()
            )
            month--

            finish()
        }

        backButton.setOnClickListener {
            finish()
        }

    }

    fun ShowPopup()
    {
        val myDialog: Dialog = Dialog(this)
        myDialog.setContentView(R.layout.pop_up_tables)
        val closeButton = myDialog.findViewById<Button>(R.id.close_button)
        val tablesGridList = myDialog.findViewById<GridView>(R.id.tables_list_1)
        var adapter = TableAdapter(this, TablesList)
        tablesGridList.adapter = adapter
        tablesGridList.setOnItemClickListener { parent, view, position, id ->
            /// where the tables are changed
            if (TablesList[position].getStatus() == 1) {
                TablesList[position].setStatus(0)
            } else if (TablesList[position].getStatus() == 0) {
                TablesList[position].setStatus(1)
            }

            adapter = TableAdapter(this, TablesList)
            tablesGridList.adapter = adapter

            tablesNumber = ArrayList<String>()
            for (i in TablesList.indices)
                if (TablesList[i].getStatus() == 1)
                    tablesNumber.add(TablesList[i].getId().toString())
        }

        closeButton.setOnClickListener {

            /// TODO Bug - the choose table button doesn't show the first chosen table
            val chooseTableButton = findViewById<Button>(R.id.btChooseTable)
            var text = tablesNumber.toString()
            if (text.length > 1)
                text = text.substring(1, text.length - 1)
            else
                text = "Pick"
            chooseTableButton.text = text
            val confirmReservation = findViewById<Button>(R.id.btConfirmedResevation)
            if (text != "Pick")
                confirmReservation.isEnabled = true
            else
                confirmReservation.isEnabled = false
            myDialog.dismiss()
        }

        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }


}

/**
 * gets all tables from the restaurant with the restaurantId id
 */
fun getTables (restaurantId: Int) : ArrayList<TablesClass>
{
    /// TODO These are hardcoded, 9 tables at restaurantId = 0

    var tablesList = ArrayList<TablesClass>()

    var tablesFromDb = Database.runQuery("""
        SELECT table_id, is_reserved, position, nrOfPeople FROM tbl_tables WHERE restaurant_id = $restaurantId
    """.trimIndent())

    while (tablesFromDb!!.next()) {
        val id = tablesFromDb.getInt(1)
        val is_reserved = tablesFromDb.getInt(2)
        val position = tablesFromDb.getString(3)
        val nrOfPeople = tablesFromDb.getInt(4)

        tablesList.add(TablesClass(nrOfPeople, id, isReservedToStatus(is_reserved)))
    }

    return tablesList
}

/**
 * given an array of tables arr = ArrayList<String> where arr[0] = id of first table (string format)
 * create string to put in db (string format: "tableid0;tableid1;...;tableidn;")
 */
fun createTablesStr (tablesList: ArrayList<String>): String
{
    var tablesStr = ""

    for (table in tablesList)
        tablesStr += "$table;" /// insert table in tablesStr

    return tablesStr
}