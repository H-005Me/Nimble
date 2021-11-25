package com.example.nimble.profile

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nimble.R
import com.example.nimble.entities.OrdersClass
import java.util.*
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.*
import com.example.nimble.adapters.TableAdapter
import com.example.nimble.database.Database
import com.example.nimble.entities.TablesClass
import kotlin.collections.ArrayList

/// TODO Fix getTables(), use function from ReservationActivity (I think)

class EditOrdersActivity : AppCompatActivity() {
    var TablesList = ArrayList<TablesClass>()
    var tablesNumber = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_orders_edit_activity)
        var backButton = findViewById<Button>(R.id.backbutton)
        var cancelButton = findViewById<Button>(R.id.btCancel)
        var saveButton = findViewById<Button>(R.id.btSave)
        var theList = intent.getSerializableExtra("LIST") as OrdersClass
        var pickHourBtn = findViewById<Button>(R.id.new_hourbutton)
        var pickDateBtn = findViewById<Button>(R.id.new_datebutton)
        var pickTableBtn = findViewById<Button>(R.id.new_tablebutton)
        var editRemarkTxt = findViewById<EditText>(R.id.remarkstext)
        val deleteReservation = findViewById<Button>(R.id.btReservationDelete)
        //firstly declared are the elements of the layout
        var thehour = 0
        var theminute = 0
        var aux1 = theList
        var aux2 = theList
        var day = aux1.getDay()
        var month = aux1.getMonth() - 1
        var year = aux1.getYear()
        var hour = aux1.getHour()
        var minutes = aux1.getMinute()
        var tables = aux1.getTables()
        var remark = aux1.getRemarks()
        var restaurantname = aux1.getName()
        //no modifications
        setText(year, month + 1, day, hour, minutes, tables, remark)
        saveButton.text = "Save"
        saveButton.isEnabled = false
        cancelButton.isEnabled = false
        cancelButton.visibility = View.GONE
        //it modifies when changes are made
        //exit activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        backButton.setOnClickListener {
            finish()
//            SeeReservationsActity().recreate()
        }
        //sends data to the database
        //TODO("confirmation message")
        saveButton.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Confirm")
            builder.setMessage("Are you sure? This will rewrite the reservation in the database")
            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                    cancelButton.isEnabled = false
                    cancelButton.visibility = View.GONE
                    saveButton.isEnabled = false
                    theList.setMinutes(minutes)
                    theList.setHour(hour)
                    theList.setDay(day)
                    theList.setMonth(month)
                    theList.setRemarks(editRemarkTxt.text.toString())
                    month++
                    var id = theList.getId()
                    Database.runUpdate(
                        """
                        UPDATE tbl_orders SET minutes = '$minutes', hour = '$hour', day='$day', month='$month',tableselected='$tables' WHERE command_id = '$id';
                    """.trimIndent()
                    )
                    month--
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
        pickDateBtn.setOnClickListener {
            saveButton.isEnabled = true
            cancelButton.isEnabled = true
            cancelButton.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val dpd =
                    DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { view: DatePicker, yearPicked: Int, monthPicked: Int, dayOfMonth: Int ->
                            year = yearPicked
                            month = monthPicked
                            day = dayOfMonth
                            month += 1
                            pickDateBtn.text = "$day/$month/$year"
                            month -= 1
                        },
                        year,
                        month,
                        day

                    )
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000

                dpd.show()


            } else {

            }

        }
        pickHourBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetLister =
                TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    hour = hourOfDay
                    theminute = minute
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pickHourBtn.text = SimpleDateFormat("HH:mm").format(cal.time)
                    }
                }

            //TODO("Disable specific hours or dates based on an array")
            TimePickerDialog(
                this,
                timeSetLister,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
            //when you make a change the cancel button needs to work,just like the save button
            cancelButton.isEnabled = true
            cancelButton.visibility = View.VISIBLE
            saveButton.isEnabled = true
        }
        getTables()
        pickTableBtn.setOnClickListener {
            saveButton.isEnabled = true
            new_showPopUp()
            var new_string = pickTableBtn.text.toString()
            //tables = new_string.toUInt().toInt() (tables is string now)
            tables = new_string

        }
        //data resets to the original
        cancelButton.setOnClickListener {
            year = theList.getYear()
            month = theList.getMonth()
            day = theList.getDay()
            hour = theList.getHour()
            minutes = theList.getMinute()
            tables = theList.getTables()
            remark = theList.getRemarks()
            setText(year, month, day, hour, minutes, tables, remark)
            cancelButton.isEnabled = false
            cancelButton.visibility = View.GONE
            saveButton.isEnabled = false

        }
        deleteReservation.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Confirm")
            builder.setMessage("Are you sure? This will delete the reservation in the database,if the reservation is cancelled 15 minutes before the reservation a tax will be perceived")
            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog

                    var id = theList.getId()
                    Database.runUpdate(
                        """
                        DELETE FROM tbl_orders WHERE command_id='$id';
                    """.trimIndent()
                    )
                    finish()
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

    private fun new_showPopUp() {
        val myDialog: Dialog = Dialog(this)
        myDialog.setContentView(R.layout.pop_up_tables)
        val closeButton = myDialog.findViewById<Button>(R.id.close_button)
        val tablesGridList = myDialog.findViewById<GridView>(R.id.tables_list_1)
        var adapter = TableAdapter(this, TablesList, TablesList)
        tablesGridList.adapter = adapter
        tablesGridList.setOnItemClickListener { parent, view, position, id ->
            if (TablesList[position].getStatus() == 1)
                TablesList[position].setStatus(0)
            else if (TablesList[position].getStatus() == 0)
                TablesList[position].setStatus(1)
            adapter = TableAdapter(this, TablesList, TablesList)
            tablesGridList.adapter = adapter
            tablesNumber = arrayListOf("")
            for (i in TablesList.indices)
                if (TablesList[i].getStatus() == 1)
                    tablesNumber.add(TablesList[i].getId().toString())

        }

        closeButton.setOnClickListener {

            val chooseTableButton = findViewById<Button>(R.id.new_tablebutton)
            var text = tablesNumber.toString()
            if (text.length > 2)
                text = text.substring(2, text.length - 1)
            else
                text = "Pick"
            chooseTableButton.text = text
            myDialog.dismiss()
        }

        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    fun setText(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minutes: Int,
        tables: String,
        remarks: String
    ) {
        var pickHourBtn = findViewById<Button>(R.id.new_hourbutton)
        var pickDateBtn = findViewById<Button>(R.id.new_datebutton)
        var pickTableBtn = findViewById<Button>(R.id.new_tablebutton)
        var editRemarkTxt = findViewById<EditText>(R.id.remarkstext)
        pickDateBtn.text = "$day/$month/$year"
        if (hour <= 9)
            pickHourBtn.text = "0$hour:$minutes"
        else
            pickHourBtn.text = "$hour:$minutes"
        pickTableBtn.text = tables
        editRemarkTxt.setText(remarks)
    }

    fun getTables() {
        //TODO("aici se iau din baza de date")
        var table = TablesClass(2, 1, 0, "Center")
        TablesList.add(table)
        table = TablesClass(2, 2, 0, "Center")
        TablesList.add(table)
        table = TablesClass(2, 3, 0, "Window")
        TablesList.add(table)
        table = TablesClass(2, 4, 0, "Terrace")
        TablesList.add(table)
        table = TablesClass(2, 5, 0, "Window")
        TablesList.add(table)
        table = TablesClass(2, 6, 0, "Terrace")
        TablesList.add(table)
    }

}