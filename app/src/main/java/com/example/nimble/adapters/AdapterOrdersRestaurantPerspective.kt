package com.example.nimble.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.nimble.database.Database
import com.example.nimble.entities.OrdersClass
import com.example.nimble.entities.ProductClass
import com.example.nimble.restaurant_perspective.MainMenuRestaurantsPerspective
import org.w3c.dom.Text
import java.security.AccessController.getContext

open class AdapterOrdersRestaurantPerspective(
    var ordersList: ArrayList<OrdersClass>, var context: Context
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
        var theStatus = "Status"
        if (ordersList[position].getStatus() == 4) {
            theStatus = "Accepted"
        } else if (ordersList[position].getStatus() == 3) {
            theStatus = "Declined"
        } else {
            theStatus = "Waiting"
        }

        holder.tvStatus.text = theStatus
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
        var tvStatus = itemView.findViewById<TextView>(com.example.nimble.R.id.tvStatus)
        init {
            itemView.setOnClickListener(this)
            btAccept.setOnClickListener {
                onAcceptClick(adapterPosition)

            }
            btReject.setOnClickListener {
                onRejectClick(adapterPosition)
                notifyDataSetChanged()
            }
        }

        override fun onClick(v: View?) {

        }
    }

    open fun onAcceptClick(position: Int) {
        showPopUp(1, position, context)
    }

    open fun onRejectClick(position: Int) {
        showPopUp(2, position, context)

    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    interface MyClickListener {
        fun onEdit(p: Int)
        fun onDelete(p: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showPopUp(command: Int, position: Int, context: Context) {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Confirm")
        builder.setMessage("Are you sure?")
        builder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                var id = ordersList[position].getId()
                if (command == 1) {
                    Database.runUpdate(
                        """
                        UPDATE tbl_orders SET status = '4' WHERE command_id='$id';
                    """.trimIndent()
                    )
                    ordersList[position].setStatus(4);
                    notifyDataSetChanged()
                } else if (command == 2) {
                    Database.runUpdate(
                        """
                        UPDATE tbl_orders SET status = '3' WHERE command_id='$id';
                    """.trimIndent()
                    )
                    ordersList[position].setStatus(3)
                }

                notifyDataSetChanged()
                dialog.dismiss()
            })

        builder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing
                dialog.dismiss()
            })

        val alert: android.app.AlertDialog? = builder.create()
        alert!!.show()

    }
}







