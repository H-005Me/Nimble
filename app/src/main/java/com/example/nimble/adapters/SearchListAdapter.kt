//import android.R
//import android.content.Context
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.Filter
//import android.widget.Filterable
//import android.widget.Toast
//import com.example.nimble.adapters.CustomFilter
//import com.example.nimble.entities.RestaurantsClass
//import com.example.nimble.holders.MyViewHolder
//import java.util.ArrayList
//
//class MyAdapter(var c: Context, var movies: ArrayList<RestaurantsClass>) :
//    BaseAdapter(), Filterable {
//    var inflater: LayoutInflater? = null
//    var filterList: ArrayList<RestaurantsClass>
//    var filter: CustomFilter? = null
//
//    //TOTLA NUM OF MOVIES
//    override fun getCount(): Int {
//        return movies.size
//    }
//
//    //GET A SINGLE MOVIE
//    override fun getItem(position: Int): Any {
//        return movies[position]
//    }
//
//    //IDENTITDIER
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
//        var convertView = convertView
//        if (inflater == null) {
//            inflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        }
//
//        //PERFORM INFLATION
//        if (convertView == null) {
//            convertView = inflater!!.inflate(com.example.nimble.R.layout.search_custom_list, null, true)
//        }
//
//        //BIND DATA TO VIEWS
//        val holder = MyViewHolder(convertView)
//        holder.nameTxt.setText(movies[position].getTitle())
//
//        holder.setItemClickListener(object : ItemClickListener() {
//            fun onItemClick(v: View?) {
//                Toast.makeText(c, movies[position].getName(), Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        //RETURN A ROW
//        return convertView
//    }
//
//    override fun getFilter(): Filter {
//        if (filter == null) {
//            filter = CustomFilter(filterList, this)
//        }
//        return filter as CustomFilter
//    }
//
//    init {
//        filterList = movies
//    }
//}