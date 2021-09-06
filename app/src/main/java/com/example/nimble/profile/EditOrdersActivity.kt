package com.example.nimble.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.nimble.R
import com.example.nimble.entities.OrdersClass
import com.example.nimble.makeToast
import java.util.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class EditOrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_orders_edit_activity)
        var backButton = findViewById<Button>(R.id.backbutton)
        var cancelButton = findViewById<Button>(R.id.cancelbutton)
        var saveButton = findViewById<Button>(R.id.savebutton)
        var theList = intent.getSerializableExtra("LIST") as OrdersClass
        var pickHourBtn = findViewById<Button>(R.id.new_hourbutton)
        var pickDateBtn = findViewById<Button>(R.id.new_datebutton)
        var pickTableBtn = findViewById<Button>(R.id.new_tablebutton)
        //firstly declared are the elements of the layout
        var thehour = 0
        var theminute = 0
        var aux1 = theList
        var aux2 = theList
        var day = aux1.getDay()
        var month = aux1.getMonth()
        var year = aux1.getYear()
        var hour = aux1.getHour()
        var minutes = aux1.getMinute()
        var tables = aux1.getTable()
        //no modifications
        setText(year, month, day, hour, minutes, tables)
        saveButton.text = "Save"
        saveButton.isEnabled = false
        cancelButton.isEnabled = false
        cancelButton.visibility = View.GONE
        //it modifies when changes are made
        //exit activity
        backButton.setOnClickListener {
            finish()
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
                            pickDateBtn.text = "$day $month $year"
                        },
                        year,
                        month,
                        day

                    )
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000

                dpd.show()
                pickDateBtn.text = "$day $month $year"


            } else {

            }
            pickDateBtn.text = "$day $month $year"

        }

//        Toast.makeText(this, theList.getHour(), Toast.LENGTH_SHORT).show()
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
        pickTableBtn.setOnClickListener {

        }


        //data resets to the original
        cancelButton.setOnClickListener {
            year = theList.getYear()
            month = theList.getMonth()
            day = theList.getDay()
            hour = theList.getHour()
            minutes = theList.getMinute()
            tables = theList.getTable()
            setText(year, month, day, hour, minutes, tables)
            cancelButton.isEnabled = false
            cancelButton.visibility = View.GONE
            saveButton.isEnabled = false
        }
    }

    fun setText(year: Int, month: Int, day: Int, hour: Int, minutes: Int, tables: Int) {
        var pickHourBtn = findViewById<Button>(R.id.new_hourbutton)
        var pickDateBtn = findViewById<Button>(R.id.new_datebutton)
        var pickTableBtn = findViewById<Button>(R.id.new_tablebutton)
        pickDateBtn.text = "$day/$month/$year"
        if (hour <= 9)
            pickHourBtn.text = "0$hour:$minutes"
        else
            pickHourBtn.text = "$hour:$minutes"
        pickTableBtn.text = "$tables"
    }

}