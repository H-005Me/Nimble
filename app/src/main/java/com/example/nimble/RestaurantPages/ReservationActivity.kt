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
import kotlinx.android.synthetic.main.activity_reservation.*
import org.w3c.dom.Text
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
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        //this is so that the keyboard adapts to the amount of text written
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
        dateText.setOnClickListener {
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
        hourRes.text = "nu a fost ales nimic"
        if (hour != -1)
            chooseTableButton.isEnabled = true

        hourRes.setOnClickListener {
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
            //TODO(you can make a  reservation without any tables Good or bad?)
            if (tablesNumber.size > 2) {
                var text = tablesNumber.toString()
                if (text[0] == '[')
                    text = text.substring(1, text.length - 1)
                chooseTableButton.text = text
                tableIsPicked = true
                confirmReservation.isEnabled = true
                confirmReservation.isEnabled = tableIsPicked
            } else {
                chooseTableButton.text = "Pick"
                confirmReservation.isEnabled = false
                tableIsPicked = false
                confirmReservation.isEnabled = tableIsPicked
            }

        }
        chooseTableButton.isEnabled = hour != -1



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
        //buttons to filter the tables
        //TODO Improvement-get the filters from the table information obtain in the database(with recyclerviews)
        val windowSeat = myDialog.findViewById<RadioButton>(R.id.btGeam)
        val centerSeat = myDialog.findViewById<RadioButton>(R.id.btCentru)
        val terraceSeat = myDialog.findViewById<RadioButton>(R.id.btTerasa)
        var allSeats = myDialog.findViewById<RadioButton>(R.id.btAll)
        var tvNumberOfSeats = myDialog.findViewById<TextView>(R.id.tvNumberOfPeople)
        var btFilter = myDialog.findViewById<Button>(R.id.btFilter)

        var isAdaptedSeat = false
        //they are filtered based on a parameter
        // currently by a string symbolising position of the table
        var currentArrayListFiltered = TablesList
        var currentArrayListOfSeats = currentArrayListFiltered
        var adapter = TableAdapter(this, currentArrayListOfSeats, TablesList)
        tablesGridList.adapter = adapter

        //gets the filtered array(the ones from the window)
        //makes the changes visible by redoing the adapter; this can be improved
        //the same can be said for the other ones; only the theString parameter changes
        //
        /**
         * there are three arrays
         * 1.TableList(initial array,don't modify it,be it life or death)
         * 2.currentArrayListFiltered(array filtered by position ex"geam", "fereastra")
         * 3.currentArrayListOfSeats(array that filters the currentArrayListFiltered by the number of seats)
         * you always filter based on currentArrayListOfSeats
         * i didn't take the case where you filter first by seats than by position
         * it can be done
         */

        //initially it is the unfiltered(all tables)
        // you need to store this array for the onClickListener
        //(you need it in the onClickListener because when you click on the table
        //you don't select the table currently shown(if you press the first table when you filter "2" for
        //seats,and then you go back without any filters or doesn't matter what filter,the first position is
        //always selected even if it has a different filter)
        // when you filter based on how many seats are at the tables
        //you don't filter the current array(because it can already be filtered so you lose tables that
        //otherwise would be good (ex: when you filter based on "Terasa" then you filter with "2" seats
        //then you filter with "6" you don't get any tables),so you need a second array that is the currentArrayListOfSeats that
        //memorises what should the seats thing filter for
        //
        windowSeat.setOnClickListener {

            var newTableList = ArrayList<TablesClass>()
            newTableList = getFilteredTables(TablesList, "la geam")
            adapter = TableAdapter(this, newTableList, TablesList)
            tablesGridList.adapter = adapter
            currentArrayListFiltered = newTableList
            currentArrayListOfSeats = newTableList
            //explanation up

        }
        centerSeat.setOnClickListener {
            var newTableList = ArrayList<TablesClass>()
            newTableList = getFilteredTables(TablesList, "centrale")
            adapter = TableAdapter(this, newTableList, TablesList)
            tablesGridList.adapter = adapter
            currentArrayListFiltered = newTableList
            currentArrayListOfSeats = newTableList
            //explanation up

        }
        terraceSeat.setOnClickListener {
            var newTableList = ArrayList<TablesClass>()
            newTableList = getFilteredTables(TablesList, "terasa")
            adapter = TableAdapter(this, newTableList, TablesList)
            tablesGridList.adapter = adapter
            //explanation up
            currentArrayListFiltered = newTableList
            currentArrayListOfSeats = newTableList

        }
        allSeats.setOnClickListener {
            adapter = TableAdapter(this, TablesList, TablesList)
            tablesGridList.adapter = adapter
            //it does the initial array, without any filters
            currentArrayListFiltered = TablesList
            currentArrayListOfSeats = currentArrayListFiltered
        }
        btFilter.setOnClickListener {
            var theTextOfSeats = tvNumberOfSeats.text.toString()
            if (theTextOfSeats != "All" && theTextOfSeats != "") {
                var possibleArray: ArrayList<TablesClass>
                possibleArray =
                    getFilteredForSeatsNumber(theTextOfSeats.toInt(), currentArrayListFiltered)
                //you check if there is a filter than check if there are tables with that filter
                //the filter is position+numberOfSeats(e.g. "geam" + "2")

                if (possibleArray.size != 0) {
                    currentArrayListOfSeats = possibleArray
                    adapter = TableAdapter(this, currentArrayListOfSeats, TablesList)
                    tablesGridList.adapter = adapter
                } else {
                    Toast.makeText(
                        this,
                        "There are no tables with " + theTextOfSeats + " seats",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (theTextOfSeats == "") {
                adapter = TableAdapter(this, currentArrayListFiltered, TablesList)
                tablesGridList.adapter = adapter
            }
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        tablesGridList.setOnItemClickListener { parent, view, position, id ->
            /// where the tables are changed
            if (TablesList[currentArrayListOfSeats[position].getInitialPositionInArray()].getStatus() == 1) {
                TablesList[currentArrayListOfSeats[position].getInitialPositionInArray()].setStatus(
                    0
                )
            } else if (TablesList[currentArrayListOfSeats[position].getInitialPositionInArray()].getStatus() == 0) {
                TablesList[currentArrayListOfSeats[position].getInitialPositionInArray()].setStatus(
                    1
                )
            }

            /*
            adapter = TableAdapter(this, currentArrayListOfSeats, TablesList)
            tablesGridList.adapter = adapter
            */
            adapter.notifyDataSetChanged()
            tablesNumber = ArrayList<String>()
            for (i in TablesList.indices)
                if (TablesList[i].getStatus() == 1)
                    tablesNumber.add(TablesList[i].getId().toString())
        }

        closeButton.setOnClickListener {
            /// TODO Bug - after deselecting all tables, no message is shown
            val chooseTableButton = findViewById<Button>(R.id.btChooseTable)
            var text = tablesNumber.toString()
            val confirmReservation = findViewById<Button>(R.id.btConfirmedResevation)

            if (text.length > 2) {
                if (text[0] == '[')
                    text = text.substring(1, text.length - 1)

            } else {
                text = "Pick"
                confirmReservation.isEnabled = false

            }
            chooseTableButton.text = text



            confirmReservation.isEnabled =
                text != "Pick" /// if text != pick then you can complete the reservation TODO rewrite this comment in a better way

            myDialog.dismiss()
        }

        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }
}

/**
 * gets all tables from the restaurant with the restaurantId id
 */
fun getTables (restaurantId: Int) : ArrayList<TablesClass> {
    /// TODO These are hardcoded, 9 tables at restaurantId = 0

    var tablesList = ArrayList<TablesClass>()

    var tablesFromDb = Database.runQuery(
        """
        SELECT table_id, is_reserved, position, nrOfPeople FROM tbl_tables WHERE restaurant_id = $restaurantId
    """.trimIndent()
    )
    var initPos = 0
    //initial position in array
    while (tablesFromDb!!.next()) {
        val id = tablesFromDb.getInt(1)
        val is_reserved = tablesFromDb.getInt(2)
        val position = tablesFromDb.getString(3)
        val nrOfPeople = tablesFromDb.getInt(4)
        //is reserved can be 1 or 0
        //position can be "la geam","centrale","terasa"


        var toBeAddedTable = TablesClass(nrOfPeople, id, isReservedToStatus(is_reserved), position)
        toBeAddedTable.setInitialPositionInArray(initPos)
        //each table needs an initial position
        // did this so that I can add something that isn't a parameter for the class
        ++initPos
        tablesList.add(toBeAddedTable)
    }

    return tablesList
}

/**
 * given an array of tables arr = ArrayList<String> where arr[0] = id of first table (string format)
 * create string to put in db (string format: "tableid0;tableid1;...;tableidn;")
 */
fun createTablesStr(tablesList: ArrayList<String>): String {
    var tablesStr = ""

    for (table in tablesList)
        tablesStr += "$table;" /// insert table in tablesStr

    return tablesStr
}

// first function is to filter the tables based on position
fun getFilteredTables(
    theInitialList: ArrayList<TablesClass>,
    theString: String
): ArrayList<TablesClass> {
    // it returns the tables that contain the position(theString)

    var newTableList = ArrayList<TablesClass>()
    for (index in theInitialList.indices)
        if (theInitialList[index].getTablePosition() == theString)
            newTableList.add(theInitialList[index])

    return newTableList

}

//filters the tables based on the number of people
fun getFilteredForSeatsNumber(
    number: Int,
    initialArray: ArrayList<TablesClass>
): ArrayList<TablesClass> {
    var newArrayList = ArrayList<TablesClass>()
    for (x in initialArray.indices)
        if (initialArray[x].getNumberOfPeople() == number)
            newArrayList.add(initialArray[x])
    return newArrayList

}