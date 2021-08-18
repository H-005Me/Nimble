package com.example.nimble.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.MainMenu

class ProductsAdapter(
    private val restaurants: List<RestaurantsClass>,
    private val listener: MainMenu
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {
        val product = restaurants[position]

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var image: View? = itemView.findViewById(R.id.thePhoto)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(position)
            }
        }
    }
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
}