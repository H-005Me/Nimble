package com.example.nimble.adapters

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.example.nimble.R
import com.example.nimble.entities.CategoriesClass
import com.example.nimble.entities.TablesClass


class TableAdapter(
    private val context: Activity,
    private val theCurrentList: ArrayList<TablesClass>,
    private val theInitialList: ArrayList<TablesClass>
) : BaseAdapter() {
    //when you filter you need to remember the initial array and when you click something
    //you need to know which table you've clicked so that the table remains blue even if you
    // get another filter
    override fun getCount(): Int {
        return theCurrentList.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItem(position: Int): Any {
        return theCurrentList[position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        var rowView = inflater.inflate(R.layout.custom_table, null, true)
        val tableButton = rowView.findViewById<TextView>(R.id.table_button)
        val tableLayout = rowView.findViewById<ConstraintLayout>(R.id.relativeLayout)
        val tableChairs = rowView.findViewById<TextView>(R.id.numar_persoane)
        tableButton.text =
            theInitialList[theCurrentList[position].getInitialPositionInArray()].getId().toString()
        if (theInitialList[theCurrentList[position].getInitialPositionInArray()].getStatus() == 1)
            tableLayout.setBackgroundResource(R.drawable.table_blue)
        if (theInitialList[theCurrentList[position].getInitialPositionInArray()].getStatus() == 2)
            tableLayout.setBackgroundResource(R.drawable.table_red)
        var aux1 =
            theInitialList[theCurrentList[position].getInitialPositionInArray()].getNumberOfPeople()
                .toString()
        tableChairs.text = "Chairs at the table $aux1"
        return rowView
    }
}