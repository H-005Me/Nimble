package com.example.nimble.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.nimble.R

class GridAdapterRestaurants(
    private val context: Activity,
    private val theList: Array<String>,
    private val theResourceList: ArrayList<Int>
) : BaseAdapter() {
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
        var rowView = inflater.inflate(R.layout.options_horizontal_list, null, true)

        val titleText = rowView?.findViewById(R.id.textView2) as TextView
        val imageView = rowView.findViewById(R.id.thePhoto2) as ImageView

        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        titleText.text = theList[position]
        imageView.setImageResource(theResourceList[position])
        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}