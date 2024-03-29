package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.nimble.R
import com.example.nimble.entities.RestaurantsClass

class TagsAdapter(private val context: Activity, private val theList: ArrayList<String>,/**private val theIcons:ArrayList<Int>**/
    /**private val description: Array<String>, private val imgid: Array<Int>**/)
    : BaseAdapter() {
    override fun getCount(): Int {
        return theList.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItem(position: Int): Any {
        return theList[position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        var rowView = inflater.inflate(R.layout.custom_tag, null, true)

        val titleText = rowView?.findViewById(R.id.tag) as TextView

        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        titleText.text = theList[position]

        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}