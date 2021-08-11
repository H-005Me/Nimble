package example.javatpoint.com.kotlincustomlistview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mainmenu.RestaurantsClass

import com.example.nimble.R
import kotlin.String as String

fun lambda( horizontal: Boolean): Int {
    if(horizontal==true)
        return R.layout.custom_list
    else
        return R.layout.horizontal_list
}
class MyListAdapter(private val context: Activity, private val title: Array<String>, private val distances: Array<Double>, private val horizontal:Boolean /**private val description: Array<String>, private val imgid: Array<Int>**/)
    : ArrayAdapter<String>(context, lambda(horizontal), title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        var rowView = inflater.inflate(R.layout.custom_list, null, true)
         if(!horizontal)
        {
            rowView = inflater.inflate(R.layout.horizontal_list, null, true)
        }
        val titleText = rowView?.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        val distance= rowView.findViewById(R.id.distance) as TextView
        //val distanceText = rowView.findViewById(R.id.distance) as TextView

        titleText.text = title[position]
        distance.text=distances[position].toString()
        //distanceText.text = distance[position].toString()
//        imageView.setImageResource(imgid[position])
//            subtitleText.text = description[position]

        return rowView
    }
}