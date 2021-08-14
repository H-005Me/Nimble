package com.example.nimble.mainmenu

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.R

class AdapterWithClass(private val context: Activity, private val theList: ArrayList<RestaurantsClass>
    /**private val description: Array<String>, private val imgid: Array<Int>**/)
    : BaseAdapter() {
    override fun getCount(): Int {
        return theList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        var rowView = inflater.inflate(R.layout.custom_grid, null, true)

        val titleText = rowView?.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView

        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        titleText.text = theList[position].getTitle()

        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}