package com.example.nimble.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.mainmenu.RestaurantsClass
import com.example.nimble.R
import com.example.nimble.RestaurantPages.CasaPiratilorPage
import com.example.nimble.RestaurantPages.KlausenBurgerPage
import android.widget.TextView





class SearchActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var search=findViewById<SearchView>(R.id.searchView)
        val listView=findViewById<ListView>(R.id.listView)
        var names= arrayOf("Casa Piratilor","Marty ","La Papion","Klausen Burger","Cafe","Restaurant 1")
        var numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
        var index:Int=0
        while (index<names.size)
        {
            numbersMap.put(names[index],index)
            index++
        }
        val adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)
        listView.adapter=adapter
        listView.setOnItemClickListener{parent, view, position, id ->
            val restaurantClicked = (view as TextView).text.toString()
            var inpos = numbersMap[restaurantClicked]
            if(inpos==0)
            {
                val intent= Intent(this, CasaPiratilorPage::class.java)
                startActivity(intent)
            }
            else if(inpos==3)
            {
                val intent=Intent(this, KlausenBurgerPage::class.java)
                startActivity(intent)
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