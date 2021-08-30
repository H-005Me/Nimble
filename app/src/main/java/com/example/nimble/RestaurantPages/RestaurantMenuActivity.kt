package com.example.nimble.RestaurantPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nimble.R
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import android.view.View
import android.widget.ExpandableListView
import com.example.nimble.adapters.ExpandableListAdapter


class RestaurantMenuActivity : AppCompatActivity() {
    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
//        var expandableListView=findViewById<ExpandableListView>(R.id.expand_activities_button)
//        expandableListView.setAdapter(ExpandableListAdapter(this,expandableListView, header, body))
    }
}