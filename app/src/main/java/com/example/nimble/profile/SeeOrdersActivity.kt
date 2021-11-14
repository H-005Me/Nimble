package com.example.nimble.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nimble.R
import com.example.nimble.database.Database
import com.example.nimble.user.user

class SeeOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_orders)

        val USERNAME = user.getFullName()

        var orders = Database.runQuery("""
            SELECT commandId, minute, hour, day, month, year, stringOfIds, stringOfNumberOfEach FROM tbl_commands WHERE userName = '$USERNAME'
        """.trimIndent())

        while (orders!!.next()) {
            val commandId = orders.getInt(1)
            val minute = orders.getInt(2)
            val hour = orders.getInt(3)
            val day = orders.getInt(4)
            val month = orders.getInt(5)
            val year = orders.getInt(6)
            val stringOfIds = orders.getString(7)
            val stringOfNumbersEach = orders.getString(8)

            Log.d("ORDER", "$commandId, $minute, $hour, $day, $month, $year, $stringOfIds, $stringOfNumbersEach")
        }
    }
}