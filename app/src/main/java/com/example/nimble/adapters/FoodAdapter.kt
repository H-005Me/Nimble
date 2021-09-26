import android.R
import android.content.Context

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button


class FoodAdapter(context: Context) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.nimble.R.layout.custom_food, parent, false)
        return ViewHolder(view, object : MyClickListener {
            override fun onAdd(p: Int) {
                // Implement your functionality for onEdit here
            }

            override fun onDelete(p: Int) {
                // Implement your functionality for onDelete here
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list: List<*> = lists.elementAt(position)
        holder.name.setText(list.getName())
    }

    override fun getItemCount(): Int {
        return lists.size()
    }

    class ViewHolder(itemView: View, listener: MyClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listener: MyClickListener
        var name: TextView
        var add: Button
        var delete: Button
        fun onClick(view: View) {
            when (view.getId()) {
                R.id.add -> listener.onAdd(this.layoutPosition)
                R.id.delete -> listener.onDelete(this.layoutPosition)
                else -> {
                }
            }
        }

        init {
            name = itemView.findViewById(R.id.foodType)
            delete = itemView.findViewById(R.id.buttonRemoveFromCart) as Button
            add = itemView.findViewById(R.id.buttonAddToCart) as Button
            this.listener = listener
            add.setOnClickListener(this)
            delete.setOnClickListener(this)
        }
    }

    interface MyClickListener {
        fun onAdd(p: Int)
        fun onDelete(p: Int)
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.lists = lists
    }
}