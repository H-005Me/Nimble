package com.example.nimble.RestaurantPages

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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


}