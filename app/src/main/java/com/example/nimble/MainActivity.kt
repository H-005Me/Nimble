package com.example.nimble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nimble.mainmenu.SearchActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        var searchViewing=findViewById<Button>(R.id.searchView)

        searchViewing.setOnClickListener()
        {
            val intent= Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}