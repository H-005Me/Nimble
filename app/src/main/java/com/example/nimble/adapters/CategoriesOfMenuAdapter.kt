package com.example.nimble.adapters

import android.R
import android.content.Context
import android.util.Log

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.nimble.RestaurantPages.RestaurantMenuActivity
import com.example.nimble.adapters.ProductsAdapter
import com.example.nimble.entities.ProductClass
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.MainMenu
import org.w3c.dom.Text


class CategoriesOfMenuAdapter(
    private var categoryList: ArrayList<String>,
    private val listener: RestaurantMenuActivity
) : androidx.recyclerview.widget.RecyclerView.Adapter<CategoriesOfMenuAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: CategoriesOfMenuAdapter.ViewHolder, position: Int) {

//        holder.image.setImageResource(dishesList[position].getIcon())
        holder.new_title.text = categoryList[position]

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.nimble.R.layout.custom_text_for_categories_of_menu, parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount() = categoryList.size

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var new_title = itemView.findViewById<TextView>(com.example.nimble.R.id.theMessage)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

        }
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    interface MyClickListener {
        fun onEdit(p: Int)
        fun onDelete(p: Int)
    }
}

