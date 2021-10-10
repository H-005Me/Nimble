package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.nimble.R
import com.example.nimble.entities.OrdersClass


class OrdersAdapterForListViews(
    private val context: Activity,
    private val theList: ArrayList<OrdersClass>
) : BaseAdapter() {
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
        var rowView = inflater.inflate(R.layout.profile_orders_custom_list, null, true)
        val titleText = rowView?.findViewById(R.id.ordersrestaurantname) as TextView
        val date = rowView.findViewById<TextView>(R.id.ordersDate)
        val hour = rowView.findViewById<TextView>(R.id.ordersHour)
        val status = rowView.findViewById<TextView>(R.id.ordersStatus)
//        val eachItem=rowView.findViewById<RelativeLayout>(R.id.eachorder)
        titleText.text = theList[position].getName()
        var month = theList[position].getMonth()
        var year = theList[position].getYear()
        var day = theList[position].getDay()
        var hourvar = theList[position].getHour()
        var minutes = theList[position].getMinute()
        var isAvailable = theList[position].getStatus()
        date.text = "$year/$month/$day"
        hour.text = "$hourvar:$minutes"
        if (isAvailable == 0) {
            status.text = "Available"
//            eachItem.setBackgroundResource(R.color.light_gray)
        } else if (isAvailable == 2) {
            status.text = "Expired"
//            eachItem.setBackgroundResource(R.color.red_crimson)
        } else if (isAvailable == 1) {
            status.text = "Completed"
//            eachItem.setBackgroundResource(R.color.lime)
        } else if (isAvailable == 3) {
            status.text = "Declined"

        } else if (isAvailable == 4) {
            status.text = "Accepted"

        }
        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}