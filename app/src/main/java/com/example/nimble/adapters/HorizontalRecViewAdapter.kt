package com.example.nimble.adapters

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.entities.RestaurantsClass
import com.example.nimble.mainmenu.MainMenu

class ProductsAdapter(
    private val restaurants: List<RestaurantsClass>,
    private val listener: MainMenu
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {

        holder.image.setImageResource(restaurants[position].getIcon())
        var toShow = holder.image
        Log.i("the image:", "$toShow")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_list, parent, false)


        return ViewHolder(view)
    }


    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var image = itemView.findViewById<ImageView>(R.id.thePhoto)
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