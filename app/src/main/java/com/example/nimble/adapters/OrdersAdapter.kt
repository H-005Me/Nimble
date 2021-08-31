package com.example.nimble.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.entities.OrdersClass
import com.example.nimble.profile.SeeReservationsActity
import org.w3c.dom.Text

class OrdersAdapter(
    private val orders: ArrayList<OrdersClass>,
    private val listener: SeeReservationsActity
) : androidx.recyclerview.widget.RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        val product = orders[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_orders_custom_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = orders.size

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var restaurantName = itemView.findViewById<TextView>(R.id.ordersrestaurantname)
        var date = itemView.findViewById<TextView>(R.id.ordersDate)
        var hour = itemView.findViewById<TextView>(R.id.ordersHour)
        var status = itemView.findViewById<TextView>(R.id.ordersStatus)

        init {
            this.restaurantName.text = orders[adapterPosition].getName()
            this.date.text =
                (orders[adapterPosition].getYear() + orders[adapterPosition].getMonth() + orders[adapterPosition].getDay()).toString()
            this.hour.text =
                (orders[adapterPosition].getHour() + orders[adapterPosition].getMinute()).toString()
            if (orders[adapterPosition].getStatus() == false)
                this.status.text = "False"
            else
                this.status.text = "True"
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
}