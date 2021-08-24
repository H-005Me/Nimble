package com.example.nimble.RestaurantPages

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.nimble.R
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.View

import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class ReservationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        var year = 0
        var month = 0
        var day = 0
        var hour = 0
        var minuteF = 0
        val c = Calendar.getInstance()
        if (year == 0)
            year = c.get(Calendar.YEAR)
        if (month == 0)
            month = c.get(Calendar.MONTH)
        if (day == 0)
            day = c.get(Calendar.DAY_OF_MONTH)
        val pickDateBtn = findViewById<Button>(R.id.dateButton)
        val dateTv = findViewById<TextView>(R.id.dateText)
        val pickHourBtn = findViewById<Button>(R.id.hourButton)
        val hourRes = findViewById<TextView>(R.id.hourText)
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
                            dateTv.setText("$day $month $year")
                        },
                        year,
                        month,
                        day

                    )
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000

                dpd.show()
                dateTv.setText("$day $month $year")


            } else {

            }
            dateTv.setText("$day $month $year")
        }
        dateTv.setText("$day $month $year")
        val chooseTableButton = findViewById<Button>(R.id.choosingTable)
        chooseTableButton.setOnClickListener {
            ShowPopup()
        }
        pickHourBtn.setOnClickListener {
            val cal = Calendar.getInstance()
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

        val resData = findViewById<Button>(R.id.resDetails)
        resData.setOnClickListener {
            Toast.makeText(
                this,
                "$year" + " $month" + " $day" + " $hour" + " $minuteF",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun ShowPopup() {
        val myDialog: Dialog = Dialog(this)
        myDialog.setContentView(R.layout.tables_popup)
        val closeButton = myDialog.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            myDialog.dismiss()
        }

        myDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

}

private fun Any.requestLocationUpdates(
    gpsProvider: String,
    i: Int,
    fl: Float,
    reservationActivity: ReservationActivity
) {
    TODO("IDK what it does but it works like this ")
}

