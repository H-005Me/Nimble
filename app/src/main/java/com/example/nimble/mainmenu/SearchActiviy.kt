package com.example.nimble.mainmenu
import com.example.nimble.entities.RestaurantsClass
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nimble.R
import com.example.nimble.RestaurantPages.CasaPiratilorPage
import com.example.nimble.RestaurantPages.KlausenBurgerPage
import android.widget.TextView
import com.example.nimble.MainActivity
import com.example.nimble.RestaurantPages.GeneralRestaurant

class SearchActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var search=findViewById<SearchView>(R.id.searchView)
        val listView=findViewById<ListView>(R.id.listView)
        val restaurantsList = intent.getSerializableExtra("LIST") as ArrayList<RestaurantsClass>

       // var names= arrayOf("Casa Piratilor","Marty ","La Papion","Klausen Burger","Cafe","Restaurant 1")
//        var list= MainActivity.getList
        var names: MutableList<String> = ArrayList()
        for (item in restaurantsList)
           names.add(item.getTitle())

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

            val intent= Intent(this, GeneralRestaurant::class.java)
            intent.putExtra("TheList",restaurantsList[position])
            startActivity(intent)

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