package com.example.nimble.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.nimble.entities.OrdersClass
import com.example.nimble.entities.ProductClass

open class AdapterOrdersRestaurantPerspective(
    var ordersList: ArrayList<OrdersClass>
) : androidx.recyclerview.widget.RecyclerView.Adapter<AdapterOrdersRestaurantPerspective.ViewHolder>() {

    override fun onBindViewHolder(
        holder: AdapterOrdersRestaurantPerspective.ViewHolder,
        position: Int
    ) {

//        holder.image.setImageResource(dishesList[position].getIcon())
        holder.tvTitle.text = ordersList[position].getUserName()
        holder.tvDate.text =
            "${ordersList[position].getDay()}/${ordersList[position].getMonth()}/${ordersList[position].getYear()}"
        holder.tvTableRestaurantPerspective.text = ordersList[position].getTable().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                com.example.nimble.R.layout.custom_orders_restaurant_perspective,
                parent,
                false
            )

        return ViewHolder(view)
    }


    override fun getItemCount() = ordersList.size

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var btReject = itemView.findViewById<Button>(com.example.nimble.R.id.btReject)
        var btAccept = itemView.findViewById<Button>(com.example.nimble.R.id.btConfirm)
        var tvTitle =
            itemView.findViewById<TextView>(com.example.nimble.R.id.tvTitleRestaurantPerspective)
        var tvDate =
            itemView.findViewById<TextView>(com.example.nimble.R.id.tvDateRestaurantPerspective)
        var tvTableRestaurantPerspective =
            itemView.findViewById<TextView>(com.example.nimble.R.id.tvTableRestaurantPerspective)

        init {
            itemView.setOnClickListener(this)
            btAccept.setOnClickListener {
                onAcceptClick(adapterPosition)
            }
            btReject.setOnClickListener {
                onRejectClick(adapterPosition)
            }
        }

        override fun onClick(v: View?) {

        }
    }

    open fun onAcceptClick(position: Int) {

    }

    open fun onRejectClick(position: Int) {

    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    interface MyClickListener {
        fun onEdit(p: Int)
        fun onDelete(p: Int)
    }


}




