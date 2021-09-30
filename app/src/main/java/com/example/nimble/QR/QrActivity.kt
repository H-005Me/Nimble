package com.example.qr_good_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TabHost
import android.widget.Toast
import com.example.nimble.R
import com.example.nimble.RestaurantPages.GeneralRestaurant
import com.example.nimble.RestaurantPages.RestaurantMenuActivity
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.RestaurantsList


import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class QrActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        var BtnScanner = findViewById<Button>(R.id.btnScanner)
        initScanner()
    }

    /// override fun onCreate(savedInstanceState: Bundle?) {
      ///  super.onCreate(savedInstanceState)
     ////   binding = ActivityMainBinding.inflate(layoutInflater)
    ///    setContentView(binding.root)
      ///  binding.btnScanner.setOnClickListener { initScanner() }

    ///}

    private fun initScanner() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
                finish()
            } else {
                val theString = result.contents.toString()
                Toast.makeText(this, "$theString", Toast.LENGTH_SHORT).show()
                var theList = intent.getSerializableExtra("LIST") as ArrayList<RestaurantsClass>
                var i = 0
                for (x in theList.indices)
                    if (theString == theList[x].getTitle())
                        i = x

                intent = Intent(this, RestaurantMenuActivity::class.java)
                intent.putExtra("LIST", theList[i])
                startActivity(intent)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}



