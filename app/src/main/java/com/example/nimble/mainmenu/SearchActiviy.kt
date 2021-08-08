package com.example.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class SearchActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var search=findViewById<SearchView>(R.id.searchView)
        val listView=findViewById<ListView>(R.id.listView)
        var names= arrayOf("Android","Java","Kotlin","C++","It doesn't work")
        val adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)
        listView.adapter=adapter
        listView.setOnItemClickListener{parent, view, position, id ->

            //Toast.makeText(this@MainActivity, "You have Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show()

            if (position==0){
                Toast.makeText(this, "Item One",   Toast.LENGTH_SHORT).show()
            }
            if (position==1){
                Toast.makeText(this, "Item Two",   Toast.LENGTH_SHORT).show()
            }
            if (position==2){
                Toast.makeText(this, "Item Three", Toast.LENGTH_SHORT).show()
            }
            if (position==3){
                Toast.makeText(this, "Item Four",  Toast.LENGTH_SHORT).show()
            }
            if (position==4){
                Toast.makeText(this, "Item Five",  Toast.LENGTH_SHORT).show()
            }
        }
        search.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                if(names.contains(query))
                {
                    adapter.filter.filter(query)
                }
                else{
                    Toast.makeText(applicationContext,"Item not found",Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }
        })
    }
}