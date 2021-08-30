package com.example.nimble.adapters

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import com.example.nimble.R
import com.example.nimble.entities.CategoriesClass
import com.example.nimble.entities.TablesClass


class TableAdapter(
    private val context: Activity, private val theList: ArrayList<TablesClass>
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
        var rowView = inflater.inflate(R.layout.custom_table, null, true)
        val tableButton = rowView.findViewById<TextView>(R.id.table_button)
        val tableLayout = rowView.findViewById<LinearLayout>(R.id.table_layout)
        val tableChairs = rowView.findViewById<TextView>(R.id.numar_persoane)
        tableButton.text = theList[position].getId().toString()

        if (theList[position].getStatus() == true)
            tableLayout.background = R.drawable.ic_launcher_foreground.toDrawable()
        var aux1 = theList[position].getNumberOfPeople().toString()
        tableChairs.text = "Chairs at the table $aux1"
        return rowView
    }
}