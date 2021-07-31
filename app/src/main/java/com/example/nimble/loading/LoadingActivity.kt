package com.example.nimble.loading

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import com.example.nimble.R
import com.example.nimble.database.Database
import com.example.nimble.login.LoginActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // this allows networking on main thread
        val threadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(threadPolicy)

        /// connect to db
        if (!setupDb()) { /// db connection failed
            println("ERROR LoadingActivity.onCreate(): Database connection failed")

            /// tell the user to restart the app and disable any other inputs
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Fatal error")
            alert.setMessage("Cannot connect to database. Please restart the application, and if the issue persists, notify the developers and try again at a later time")
            alert.setCancelable(false)
            alert.show()

            return
        }

        /// change to LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
    }

    /**
     * returns true if db is connected
     */
    private fun setupDb () : Boolean {
        if (Database.connected == 1) {
            println("setupDb(): Database already connected")
            return true
        }

        for (i: Int in 1..3) { /// try 3 times
            println("Try $i")
            Database.connect()
            if (Database.connected == 1) /// if connection is successful
                return true
        }

        /// db failed to connect
        println("ERROR setupDb(): Database failed to connect")
        return false
    }
}