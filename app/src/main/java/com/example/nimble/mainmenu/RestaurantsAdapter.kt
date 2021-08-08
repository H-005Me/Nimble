//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.annotation.NonNull
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mainmenu.R
//import com.example.mainmenu.RestaurantsClass
//
//internal class MoviesAdapter(private var moviesList: List<RestaurantsClass>) :
//    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {
//    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var title: TextView = view.findViewById(R.id.title)
//        var distance: TextView = view.findViewById(R.id.distance)
//        var description: TextView = view.findViewById(R.id.description)
//    }
//    @NonNull
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout., parent, false)
//        return MyViewHolder(itemView)
//    }
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val movie = moviesList[position]
//        holder.title.text = movie.getTitle()
//        holder.genre.text = movie.getGenre()
//        holder.year.text = movie.getYear()
//    }
//    override fun getItemCount(): Int {
//        return moviesList.size
//    }
//}