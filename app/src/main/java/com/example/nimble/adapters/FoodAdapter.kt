import android.widget.TextView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.nimble.RestaurantPages.RestaurantMenuActivity
import com.example.nimble.entities.ProductClass
import org.w3c.dom.Text


open class FoodAdapter(
    var dishesList: ArrayList<ProductClass>,
    var allDishes: ArrayList<ProductClass>
) : androidx.recyclerview.widget.RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {

//        holder.image.setImageResource(dishesList[position].getIcon())
        holder.title.text = dishesList[position].getTitle()
        holder.price.text = dishesList[position].getPriceOfProduct().toString()
        holder.quantity.text = dishesList[position].getQuantity().toString()
        holder.howManyItems.text =
            "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
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
        init {
            itemView.setOnClickListener(this)
            add.setOnClickListener {
//                allDishes[dishesList[adapterPosition].getId()].riseHowManyAdded()
//                howManyItems.text =
//                    "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
                onAddClick(adapterPosition, howManyItems)
            }
            delete.setOnClickListener {
//                if (allDishes[dishesList[position].getId()].getHowManyAdded() > 0) {
//                    allDishes[dishesList[position].getId()].lowerHowManyAdded()
//
//                    howManyItems.text =
//                        "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
//                }
                onDeleteClick(position, howManyItems)
            }
        }

        override fun onClick(v: View?) {

        }
    }

    open fun onAddClick(position: Int, howManyItems: TextView) {
        allDishes[dishesList[position].getId()].riseHowManyAdded()
        howManyItems.text =
            "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
    }

    open fun onDeleteClick(position: Int, howManyItems: TextView) {
        if (allDishes[dishesList[position].getId()].getHowManyAdded() > 0) {
            allDishes[dishesList[position].getId()].lowerHowManyAdded()

            howManyItems.text =
                "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
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

class ShowedFoodAdapter(dishesList: ArrayList<ProductClass>, allDishes: ArrayList<ProductClass>) :
    FoodAdapter(dishesList, allDishes) {
    override fun onDeleteClick(position: Int, howManyItems: TextView) {
        if (allDishes[dishesList[position].getId()].getHowManyAdded() > 1) {
            allDishes[dishesList[position].getId()].lowerHowManyAdded()

            howManyItems.text =
                "${allDishes[dishesList[position].getId()].getHowManyAdded()} items"
        } else {
            allDishes[dishesList[position].getId()].lowerHowManyAdded()
            dishesList.removeAt(position)
            notifyDataSetChanged()

        }
    }

}

