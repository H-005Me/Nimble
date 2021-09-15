package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nimble.entities.RestaurantsClass

import com.example.nimble.R
import com.example.nimble.entities.OffertsClass

class OffertsAdapter(private val context: Activity, private val theList: ArrayList<OffertsClass>) :
    BaseAdapter() {
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

//        val imageView = rowView.findViewById(R.id.offertsicon) as ImageView
        val offertsName = rowView.findViewById<TextView>(R.id.offertsName)
        val offert = rowView.findViewById<ConstraintLayout>(R.id.eachOffert)
//        imageView.setImageResource(theList[position].getIcon())
        offertsName.setText(theList[position].getName())
        offert.setBackgroundResource(theList[position].getBackground())
        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        //imageView.setImageDrawable(theList[position].getIcon().toDrawable())

        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}