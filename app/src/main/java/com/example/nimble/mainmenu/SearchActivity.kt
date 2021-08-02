package com.example.nimble.mainmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import com.example.nimble.R

class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        val lv_listView=findViewById<ListView>(R.id.lv_ListView)
        val tv_emptyTextView=findViewById<TextView>(R.id.tv_emptyTextView)
//        var CasaPiratilor = restaurantsClass(arrayOf("Bere","Cocktail cu mere"), arrayOf("Normala","Bautura"),"Casa Piratilor",4.9)
//        val Marty = restaurantsClass(arrayOf("Bere","Cocktail cu mere"), arrayOf("Normala","Bautura"),"Casa Piratilor",4.9)
//        var  LaPapion= restaurantsClass(arrayOf("Bere","Cocktail cu mere"), arrayOf("Normala","Bautura"),"Casa Piratilor",4.9)
//        var KlausenBurger = restaurantsClass(arrayOf("Bere","Cocktail cu mere"), arrayOf("Normala","Bautura"),"Casa Piratilor",4.9)
//        var all= listOf<restaurantsClass>(
//            CasaPiratilor, Marty,LaPapion,KlausenBurger
//        )
        adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.restaurants_array))
        lv_listView.adapter = adapter
        lv_listView.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            Toast.makeText(applicationContext,parent?.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show()

        }
        lv_listView.emptyView = tv_emptyTextView


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        val search= menu?.findItem(R.id.nav_search)
        val searchView : SearchView? = search?.actionView as SearchView?
        if (searchView != null) {
            searchView.queryHint = "Search something!"
        }
        if(searchView==null)
            return super.onCreateOptionsMenu(menu)
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)

    }
}