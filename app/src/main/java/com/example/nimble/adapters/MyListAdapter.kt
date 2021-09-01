package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import com.example.nimble.entities.RestaurantsClass

import com.example.nimble.R


class MyListAdapter(private val context: Activity,  private val theList:ArrayList<RestaurantsClass>)
    : BaseAdapter() {
    override fun getCount(): Int {
        return theList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        var rowView = inflater.inflate(R.layout.custom_list, null, true)
        val titleText = rowView?.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        val distance = rowView.findViewById(R.id.distance) as TextView
        val background = rowView.findViewById<LinearLayout>(R.id.eachItem)
        //val distanceText = rowView.findViewById(R.id.distance) as TextView
        background.setBackgroundResource(theList[position].getBackground())
        titleText.text = theList[position].getTitle()
        imageView.setBackgroundResource(theList[position].getIcon())
        distance.text= theList[position].getDistance().toString()
        //imageView.setImageDrawable(theList[position].getIcon().toDrawable())

        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}