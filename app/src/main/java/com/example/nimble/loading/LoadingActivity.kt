package com.example.nimble.loading

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import com.example.nimble.R
import com.example.nimble.database.Database
import com.example.nimble.login.LoginActivity
import com.example.nimble.mainmenu.MainMenu

class LoadingActivity : AppCompatActivity() {
    private val LOGTAG = "LoadingActivity" /// for logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // this allows networking on main thread
        val threadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(threadPolicy)

        /// connect to db
        if (!setupDb()) { /// db connection failed
            Log.e(LOGTAG, "onCreate() - Database connection failed")

            /// tell the user to restart the app and disable any other inputs
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Fatal error")
            alert.setMessage("Cannot connect to database. Make sure that your device is connected to the internet and restart the application. If the issue persists, notify the developers and try again at a later time")
            alert.setCancelable(false)
            alert.show()

            return
        }

        Database.debugPrintTable("tbl_food")

        /// go to LoginActivity & destroy LoadingActivity
        //place to add things in db I think

        //
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    /**
     * returns true if db is connected
     */
    private fun setupDb () : Boolean {
        if (Database.connected == 1) {
            Log.i(LOGTAG, "setupDb() - Database already connected")
            return true
        }

        for (i: Int in 1..3) { /// try 3 times
            Log.d(LOGTAG, "setupDb() - Try $i")
            Database.connect()
            if (Database.connected == 1) /// if connection is successful
                return true
        }

        /// db failed to connect
        Log.e(LOGTAG, "setupDb() - Database failed to connect")
        return false
    }
}