package com.example.nimble.profile

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import com.example.nimble.R
import com.example.nimble.entities.OrdersClass
import com.example.nimble.entities.RestaurantsClass
import com.google.android.material.button.MaterialButtonToggleGroup
import java.util.*

class EditOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_orders_edit_activity)
        var backButton = findViewById<Button>(R.id.backbutton)
        var cancelButton = findViewById<Button>(R.id.cancelbutton)
        var editButton = findViewById<Button>(R.id.editbutton)
        var theList = intent.getSerializableExtra("LIST") as OrdersClass
        var pickHourBtn = findViewById<Button>(R.id.new_hourbutton)
        var pickDateBtn = findViewById<Button>(R.id.new_datebutton)
        var pickTableBtn = findViewById<Button>(R.id.new_tablebutton)
        //firstly declared are the elements of the layout
        var theNew_List = theList
        var day = theNew_List.getDay()
        var month = theNew_List.getMonth()
        var year = theNew_List.getYear()
        var hour = theNew_List.getHour()
        var minutes = theNew_List.getMinute()
        var tables = theNew_List.getTable()
        //no modifications
        pickDateBtn.text = "$day/$month/$year"
        pickHourBtn.text = "$hour/$minutes"
        pickTableBtn.text = "$tables"
        editButton.isEnabled = false
        cancelButton.isEnabled = false
        cancelButton.visibility = View.GONE
        //it modifies when changes are made
        //exit activity
        backButton.setOnClickListener {
            finish()
        }
        //the edit button gets the text SAVE so it kinda becomes a save button
        editButton.setOnClickListener {
            cancelButton.isEnabled = true
            cancelButton.visibility = View.VISIBLE
            editButton.text = "SAVE"
        }

        pickHourBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetLister =
                TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    theNew_List.setHour(hourOfDay)
                    theNew_List.setMinutes(minute)
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

        }
    }
}