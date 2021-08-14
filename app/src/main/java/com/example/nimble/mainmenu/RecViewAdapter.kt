//
//import android.R
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//
//import android.widget.TextView
//
//import android.view.ViewGroup
//
//import android.view.LayoutInflater
//import android.view.View
//
//
//class MyRecyclerViewAdapter internal constructor(context: Context?, data: List<String>) :
//    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder?>() {
//    private val mData: List<String>
//    private val mInflater: LayoutInflater
//    private var mClickListener: ItemClickListener? = null
//
//    // inflates the row layout from xml when needed
//    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        val view: View = mInflater.inflate(R.layout.activity_list_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    // binds the data to the TextView in each row
//    fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val animal = mData[position]
//        holder.myTextView.text = animal
//    }
//
//    // total number of rows
//    fun getItemCount(): Int {
//        return mData.field
//    }
//
//    // stores and recycles views as they are scrolled off screen
//    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        var myTextView: TextView
//        fun onClick(view: View?) {
//            if (mClickListener != null) mClickListener!!.onItemClick(view, getAdapterPosition())
//        }
//
//        init {
//            myTextView = itemView.findViewById(R.id.title)
//            itemView.setOnClickListener(this)
//        }
//    }
//
//    // convenience method for getting data at click position
//    fun getItem(id: Int): String {
//        return mData[id]
//    }
//
//    // allows clicks events to be caught
//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        mClickListener = itemClickListener
//    }
//
//    // parent activity will implement this method to respond to click events
//    interface ItemClickListener {
//        fun onItemClick(view: View?, position: Int)
//    }
//
//    // data is passed into the constructor
//    init {
//        mInflater = LayoutInflater.from(context)
//        mData = data
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//        TODO("Not yet implemented")
//    }
//}