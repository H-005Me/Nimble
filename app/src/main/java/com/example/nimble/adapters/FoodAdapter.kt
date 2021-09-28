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


class FoodAdapter(
    private var dishesList: ArrayList<ProductClass>,
    private val listener: RestaurantMenuActivity
) : androidx.recyclerview.widget.RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {

//        holder.image.setImageResource(dishesList[position].getIcon())
        holder.title.text = dishesList[position].getTitle()
        holder.price.text = dishesList[position].getPriceOfProduct().toString()
        holder.quantity.text = dishesList[position].getQuantity().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.nimble.R.layout.custom_food, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = dishesList.size

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title = itemView.findViewById<TextView>(com.example.nimble.R.id.foodType)
        var add = itemView.findViewById<Button>(com.example.nimble.R.id.buttonAddToCart)
        var delete = itemView.findViewById<Button>(com.example.nimble.R.id.buttonRemoveFromCart)
        var quantity = itemView.findViewById<TextView>(com.example.nimble.R.id.foodQuantity)
        var price = itemView.findViewById<TextView>(com.example.nimble.R.id.foodPrice)
        var howManyItems =
            itemView.findViewById<TextView>(com.example.nimble.R.id.actualQuantityBtn)
        var limit = 0;

        init {
            itemView.setOnClickListener(this)
            add.setOnClickListener {
                limit++
                howManyItems.text = "$limit items"
            }
            delete.setOnClickListener {
                if (limit > 0) {
                    --limit
                    howManyItems.text = "$limit items"
                }
            }
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
