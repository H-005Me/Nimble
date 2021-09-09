package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.nimble.entities.RestaurantsClass

import com.example.nimble.R

class OffertsAdapter(private val context: Activity,  private val theList:ArrayList<RestaurantsClass>)
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
        val rowView = inflater.inflate(R.layout.custom_offerts, null, true)

        val imageView = rowView.findViewById(R.id.offertsicon) as ImageView
        imageView.setImageResource(theList[position].getIcon())
        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        //imageView.setImageDrawable(theList[position].getIcon().toDrawable())

        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}